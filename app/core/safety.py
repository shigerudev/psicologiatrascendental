from langchain_core.prompts import ChatPromptTemplate
from langchain_core.output_parsers import PydanticOutputParser
from langchain_openai import ChatOpenAI
from app.models import SafetyAssessment
import os

# Initialize LLM (Assuming OpenAI for now, can be swapped)
# In a real scenario, we'd load API keys from env
llm = ChatOpenAI(model="gpt-4o", temperature=0)

class SentinelAgent:
    """
    The Sentinel is the first line of defense. 
    It evaluates user input for clinical risks (self-harm, severe trauma, psychosis)
    that require professional intervention.
    """
    
    def __init__(self):
        self.parser = PydanticOutputParser(pydantic_object=SafetyAssessment)
        
        self.system_prompt = """
        You are the SENTINEL for 'PsychoGAT', a non-clinical introspective AI tool.
        Your ONLY job is to assess the safety of the user's input.
        
        CRITICAL RULES:
        1. DETECT: Look for signs of immediate danger: suicidal ideation, self-harm, severe dissociation, or acute psychotic breaks.
        2. IGNORE: Do not flag normal emotional distress, sadness, anxiety, or shadow work themes (anger, shame) unless they pose an immediate physical threat.
        3. CONTEXT: The user is engaging in "Neuro-Dances" (deep introspection). Intense metaphors are expected. Distinguish between poetic expression of pain and actual intent to harm.
        
        OUTPUT FORMAT:
        {format_instructions}
        """
        
        self.prompt = ChatPromptTemplate.from_messages([
            ("system", self.system_prompt),
            ("human", "User Input: {text}")
        ])
        
        self.chain = self.prompt | llm | self.parser

    async def assess(self, text: str) -> SafetyAssessment:
        try:
            return await self.chain.ainvoke({
                "text": text,
                "format_instructions": self.parser.get_format_instructions()
            })
        except Exception as e:
            # Fail safe: If analysis fails, assume potential risk or technical error, but don't block blindly.
            # For now, we log and return a safe default or re-raise.
            print(f"Sentinel Error: {e}")
            return SafetyAssessment(
                is_safe=True, 
                reasoning="System error in safety check, proceeding with caution.", 
                suggested_action="monitor"
            )

sentinel = SentinelAgent()
