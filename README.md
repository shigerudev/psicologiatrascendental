# PsychoGAT - Psicología Trascendental AI

PsychoGAT is an AI-powered conversational agent designed to provide a safe, introspective space for users to explore their inner world. It combines modern web technologies with advanced AI orchestration to ensure a supportive and secure experience.

## ✨ Features

- **🛡️ Safety First**: Integrated "Sentinel" system (`app.core.safety`) that assesses user input in real-time to detect crisis situations and provide immediate clinical referrals if necessary.
- **🧠 Advanced Orchestration**: Powered by **LangGraph**, enabling complex agentic flows including reflection, critique, and response integration.
- **🌌 Immersive Interface**: A "Glassmorphism" design style frontend creating a calming, transcendental user experience.
- **⚡ Modern Stack**: Built with FastAPI for high performance and vanilla JS/CSS for a lightweight, responsive frontend.

## 🛠️ Tech Stack

- **Backend**: Python 3.10+, FastAPI, Uvicorn
- **AI/LLM**: LangChain, LangGraph, OpenAI API
- **Database**: ChromaDB (Vector Store)
- **Frontend**: HTML5, CSS3, Vanilla JavaScript

## 🚀 Getting Started

### Prerequisites

- Python 3.10 or higher
- Git

### Installation

1.  **Clone the repository**
    ```bash
    git clone https://github.com/Shigerubt/psicologiatrascendental.git
    cd psicologiatrascendental
    ```

2.  **Create a Virtual Environment**
    ```bash
    python -m venv venv
    # Windows
    .\venv\Scripts\activate
    # macOS/Linux
    source venv/bin/activate
    ```

3.  **Install Dependencies**
    ```bash
    pip install -r requirements.txt
    ```

4.  **Environment Configuration**
    Create a `.env` file in the root directory and add your API keys:
    ```env
    OPENAI_API_KEY=your_openai_api_key_here
    ```

### Running the Application

1.  **Start the Backend Server**
    ```bash
    uvicorn app.main:app --reload
    ```
    The API will be available at `http://localhost:8000`.

2.  **Launch the Frontend**
    Simply open `frontend/index.html` in your modern web browser. 
    
    *Note: For a better experience, you can serve the frontend using a simple HTTP server (e.g., `python -m http.server --directory frontend 3000`), but opening the file directly works too.*

## 📂 Project Structure

```
├── app/
│   ├── agents/         # AI Agent definitions (Critic, Explorer, Integrator)
│   ├── core/           # Core logic (Graph orchestration, Safety Sentinel)
│   ├── main.py         # FastAPI entry point
│   └── models.py       # Pydantic data models
├── frontend/           # Web interface files
├── requirements.txt    # Python dependencies
└── .gitignore
```
