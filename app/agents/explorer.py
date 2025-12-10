from langchain_core.prompts import ChatPromptTemplate
from langchain_openai import ChatOpenAI
from langchain_core.output_parsers import StrOutputParser

llm = ChatOpenAI(model="gpt-4o", temperature=0.7)

class ExplorerAgent:
    """
    The Explorer is the primary guide.
    It uses 'Neuro-Dances' methodology:
    1. Phenomenological inquiry (What is the texture of this feeling?)
    2. Non-judgmental observation.
    3. Guiding towards the 'collapse' of ambiguity into meaning.
    """
    
    def __init__(self):
        self.system_prompt = """
        You are the EXPLORER in the PsychoGAT system.
        Your goal is to guide the user through a "Neuro-Dance" of introspection.
        
        METHODOLOGY:
        - Do NOT diagnose or fix.
        - Ask "How" and "What" questions, rarely "Why" (which invites intellectualization).
        - Focus on somatic markers (where do you feel this?), symbols, and metaphors.
        - Your tone is curious, calm, and spacious.
        
        CONTEXT:
        User's past narrative context:
        {context}
        
        CURRENT STATE:
        The user is in the '{stage}' stage.
        """
        
        self.prompt = ChatPromptTemplate.from_messages([
            ("system", self.system_prompt),
            ("human", "{input}")
        ])
        
        self.chain = self.prompt | llm | StrOutputParser()
        
    async def generate_response(self, user_input: str, context: str, stage: str = "exploration") -> str:
        return await self.chain.ainvoke({
            "input": user_input,
            "context": context,
            "stage": stage
        })

explorer = ExplorerAgent()
