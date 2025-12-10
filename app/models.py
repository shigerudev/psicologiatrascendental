from pydantic import BaseModel, Field
from typing import List, Optional, Dict, Any
from enum import Enum
from datetime import datetime

class SessionState(str, Enum):
    INIT = "init"
    ACTIVE = "active"
    PAUSED = "paused"
    TERMINATED_SAFE = "terminated_safe" # Normal end
    TERMINATED_RISK = "terminated_risk" # Safety intervention

class MessageRole(str, Enum):
    USER = "user"
    SYSTEM = "system"
    ASSISTANT = "assistant"
    INTERNAL_MONOLOGUE = "internal" # For agent thoughts

class Message(BaseModel):
    role: MessageRole
    content: str
    timestamp: datetime = Field(default_factory=datetime.now)
    metadata: Dict[str, Any] = Field(default_factory=dict)

class UserSession(BaseModel):
    session_id: str
    user_id: str
    state: SessionState = SessionState.INIT
    context_summary: str = ""
    current_stage: str = "grounding" # e.g., grounding, exploration, integration
    risk_level: float = 0.0
    
class SafetyAssessment(BaseModel):
    is_safe: bool
    risk_category: Optional[str] = None
    reasoning: str
    suggested_action: str
