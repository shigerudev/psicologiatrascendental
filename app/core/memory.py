from langchain_chroma import Chroma
from langchain_openai import OpenAIEmbeddings
from langchain_core.documents import Document
from typing import List
import os

# In a real app, persistence_directory would be set
VECTOR_STORE_PATH = "./chroma_db"

class MemoryManager:
    """
    Manages the 'Narrative Self'.
    Stores user interactions and retrieves relevant past context to maintain
    narrative coherence, which is crucial for the 'collapsed state of the self'.
    """
    
    def __init__(self):
        self.embeddings = OpenAIEmbeddings(model="text-embedding-3-small")
        self.vector_store = Chroma(
            collection_name="psychogat_memory",
            embedding_function=self.embeddings,
            persist_directory=VECTOR_STORE_PATH
        )
        
    async def add_memory(self, user_id: str, content: str, metadata: dict = None):
        """Adds a new interaction to the narrative memory."""
        if metadata is None:
            metadata = {}
        metadata["user_id"] = user_id
        metadata["timestamp"] = str(os.path.getmtime(__file__)) # Placeholder timestamp
        
        doc = Document(page_content=content, metadata=metadata)
        await self.vector_store.aadd_documents([doc])
        
    async def get_relevant_context(self, user_id: str, query: str, k: int = 3) -> str:
        """Retrieves semantically relevant past interactions."""
        results = await self.vector_store.asimilarity_search(
            query, 
            k=k,
            filter={"user_id": user_id}
        )
        
        if not results:
            return ""
            
        context_str = "\n".join([f"- {doc.page_content}" for doc in results])
        return context_str

memory_manager = MemoryManager()
