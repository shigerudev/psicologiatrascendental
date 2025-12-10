from dotenv import load_dotenv
load_dotenv()

from fastapi import FastAPI, HTTPException
from pydantic import BaseModel
from app.core.safety import sentinel
from app.models import SafetyAssessment

from fastapi.middleware.cors import CORSMiddleware

app = FastAPI(title="PsychoGAT API", version="0.1.0")

app.add_middleware(
    CORSMiddleware,
    allow_origins=["*"],
    allow_credentials=True,
    allow_methods=["*"],
    allow_headers=["*"],
)

class ChatRequest(BaseModel):
    session_id: str
    user_input: str

class ChatResponse(BaseModel):
    response: str
    safety_status: SafetyAssessment

@app.get("/")
async def root():
    return {"message": "PsychoGAT System Online", "status": "active"}

@app.post("/chat", response_model=ChatResponse)
async def chat_endpoint(request: ChatRequest):
    # 1. Safety Check (The Sentinel)
    safety_assessment = await sentinel.assess(request.user_input)
    
    if not safety_assessment.is_safe:
        # Clinical Referral Protocol
        return ChatResponse(
            response="[SISTEMA] Hemos detectado que estás pasando por un momento muy difícil que requiere apoyo profesional humano. Por tu seguridad, vamos a pausar esta sesión. Por favor, contacta a los servicios de emergencia o a tu terapeuta.",
            safety_status=safety_assessment
        )
    
    # 2. Orchestration (LangGraph)
    from app.core.graph import app_graph
    
    # Initialize state
    initial_state = {
        "user_input": request.user_input,
        "user_id": request.session_id, # Using session_id as user_id for simplicity in this demo
        "history": "", # In real app, we'd fetch recent chat history
        "context": "",
        "proposed_response": "",
        "critique_feedback": "",
        "final_response": "",
        "iterations": 0
    }
    
    # Run the graph
    final_state = await app_graph.ainvoke(initial_state)
    
    return ChatResponse(
        response=final_state.get("final_response", "Error generating response."),
        safety_status=safety_assessment
    )

if __name__ == "__main__":
    import uvicorn
    uvicorn.run(app, host="0.0.0.0", port=8000)
