from typing import TypedDict, Annotated, List
from langgraph.graph import StateGraph, END
from app.agents.explorer import explorer
from app.agents.critic import critic
from app.agents.integrator import integrator
from app.core.memory import memory_manager

class GraphState(TypedDict):
    user_input: str
    user_id: str
    history: str
    context: str
    proposed_response: str
    critique_feedback: str
    final_response: str
    iterations: int

# --- Nodes ---

async def retrieve_context(state: GraphState):
    """Fetches relevant past memories."""
    context = await memory_manager.get_relevant_context(state["user_id"], state["user_input"])
    return {"context": context, "iterations": 0}

async def generate_response(state: GraphState):
    """Explorer generates a response."""
    # If there is feedback, append it to the context for the next attempt
    current_context = state["context"]
    if state.get("critique_feedback"):
        current_context += f"\n[CRITIC FEEDBACK]: {state['critique_feedback']}"
        
    response = await explorer.generate_response(state["user_input"], current_context)
    return {"proposed_response": response, "iterations": state["iterations"] + 1}

async def critique_response(state: GraphState):
    """Critic reviews the response."""
    result = await critic.critique(state["user_input"], state["proposed_response"])
    
    if result.approved:
        return {"final_response": state["proposed_response"], "critique_feedback": ""}
    else:
        # If the critic provided an improved version, we can use it or ask Explorer to try again.
        # For this design, we'll prefer the improved version if available, or loop back.
        if result.improved_response:
             return {"final_response": result.improved_response, "critique_feedback": ""}
        else:
             return {"critique_feedback": result.critique}

async def integrate_session(state: GraphState):
    """Integrator updates memory (fire and forget style usually, but here we wait)."""
    # We combine user input and final response for the memory
    interaction = f"User: {state['user_input']}\nPsychoGAT: {state['final_response']}"
    summary = await integrator.integrate(interaction)
    await memory_manager.add_memory(state["user_id"], summary)
    return {}

# --- Conditional Logic ---

def check_critique(state: GraphState):
    if state.get("final_response"):
        return "integrate"
    elif state["iterations"] > 3:
        # Fallback to avoid infinite loops: accept the last proposal or a safe fallback
        return "integrate" # In real prod, we might force a safe response here
    else:
        return "regenerate"

# --- Graph Definition ---

workflow = StateGraph(GraphState)

workflow.add_node("retrieve_context", retrieve_context)
workflow.add_node("generate", generate_response)
workflow.add_node("critique", critique_response)
workflow.add_node("integrate", integrate_session)

workflow.set_entry_point("retrieve_context")

workflow.add_edge("retrieve_context", "generate")
workflow.add_edge("generate", "critique")

workflow.add_conditional_edges(
    "critique",
    check_critique,
    {
        "integrate": "integrate",
        "regenerate": "generate"
    }
)

workflow.add_edge("integrate", END)

app_graph = workflow.compile()
