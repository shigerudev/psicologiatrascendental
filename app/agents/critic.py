from langchain_core.prompts import ChatPromptTemplate
from langchain_openai import ChatOpenAI
from langchain_core.output_parsers import PydanticOutputParser
from pydantic import BaseModel, Field

llm = ChatOpenAI(model="gpt-4o", temperature=0)

class CritiqueResult(BaseModel):
    approved: bool = Field(description="Whether the response is safe and appropriate")
    critique: str = Field(description="Explanation of what is wrong, if anything")
    improved_response: str = Field(description="A suggested rewrite if not approved, otherwise empty")

class CriticAgent:
    """
    The Critic ensures the Explorer maintains the 'Psychology Transcendental' stance:
    - No clinical diagnosis.
    - No advice-giving.
    - No judgment.
    """
    
    def __init__(self):
        self.parser = PydanticOutputParser(pydantic_object=CritiqueResult)
        
        self.system_prompt = """
        You are the CRITIC for PsychoGAT.
        Your job is to review the Explorer's proposed response to the user.
        
        CRITERIA FOR APPROVAL:
        1. NEUTRALITY: Does it avoid judging the user's experience as 'good' or 'bad'?
        2. NON-CLINICAL: Does it avoid sounding like a doctor or therapist diagnosing a condition?
        3. OPENNESS: Does it invite reflection rather than closing it down with answers?
        
        If the response violates these, REJECT it and provide a rewrite that keeps the intent but fixes the tone.
        
        {format_instructions}
        """
        
        self.prompt = ChatPromptTemplate.from_messages([
            ("system", self.system_prompt),
            ("human", "User Input: {user_input}\nExplorer Proposed Response: {proposed_response}")
        ])
        
        self.chain = self.prompt | llm | self.parser
        
    async def critique(self, user_input: str, proposed_response: str) -> CritiqueResult:
        return await self.chain.ainvoke({
            "user_input": user_input,
            "proposed_response": proposed_response,
            "format_instructions": self.parser.get_format_instructions()
        })

critic = CriticAgent()
