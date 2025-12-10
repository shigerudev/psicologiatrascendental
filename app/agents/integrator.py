from langchain_core.prompts import ChatPromptTemplate
from langchain_openai import ChatOpenAI
from langchain_core.output_parsers import StrOutputParser

llm = ChatOpenAI(model="gpt-4o", temperature=0.3)

class IntegratorAgent:
    """
    The Integrator synthesizes the session.
    It takes the raw interaction and 'collapses' it into a coherent narrative entry.
    This supports the 'Narrative Self' theory of PT.
    """
    
    def __init__(self):
        self.system_prompt = """
        You are the INTEGRATOR.
        Your task is to summarize the recent interaction into a cohesive narrative thread.
        
        GOAL:
        - Capture the core emotional shift or realization.
        - Use the user's own metaphors.
        - Write in the third person (e.g., "The user explored the feeling of...") or 
          second person if better for the user's journal ("You felt..."). 
          Let's use Second Person ("You") for direct impact.
        
        INPUT:
        - Recent conversation history.
        """
        
        self.prompt = ChatPromptTemplate.from_messages([
            ("system", self.system_prompt),
            ("human", "Conversation History:\n{history}")
        ])
        
        self.chain = self.prompt | llm | StrOutputParser()
        
    async def integrate(self, history: str) -> str:
        return await self.chain.ainvoke({
            "history": history
        })

integrator = IntegratorAgent()
