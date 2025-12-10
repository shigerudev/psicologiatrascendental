const chatMessages = document.getElementById('chatMessages');
const userInput = document.getElementById('userInput');
const sendBtn = document.getElementById('sendBtn');

// Generate a random session ID for this demo
const sessionId = 'session_' + Math.random().toString(36).substr(2, 9);

// Auto-resize textarea
userInput.addEventListener('input', function () {
    this.style.height = 'auto';
    this.style.height = (this.scrollHeight) + 'px';
    if (this.value === '') {
        this.style.height = 'auto';
    }
});

// Send message on Enter (but allow Shift+Enter for new lines)
userInput.addEventListener('keydown', function (e) {
    if (e.key === 'Enter' && !e.shiftKey) {
        e.preventDefault();
        sendMessage();
    }
});

sendBtn.addEventListener('click', sendMessage);

async function sendMessage() {
    const text = userInput.value.trim();
    if (!text) return;

    // Add User Message
    addMessage(text, 'user');
    userInput.value = '';
    userInput.style.height = 'auto';

    // Show loading state (optional, could be a typing indicator)
    const loadingId = addLoadingIndicator();

    try {
        // Call the Backend API
        const response = await fetch('http://localhost:8000/chat', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
                session_id: sessionId,
                user_input: text
            })
        });

        const data = await response.json();

        // Remove loading indicator
        removeMessage(loadingId);

        // Add System Response
        addMessage(data.response, 'system');

        // Handle Safety Trigger if needed
        if (!data.safety_status.is_safe) {
            document.querySelector('.status-indicator .dot').style.backgroundColor = '#f44336';
            document.querySelector('.status-indicator .status-text').textContent = 'Intervención Requerida';
        }

    } catch (error) {
        console.error('Error:', error);
        removeMessage(loadingId);
        addMessage("Lo siento, hubo un error de conexión con tu espacio interior (Error del servidor).", 'system');
    }
}

function addMessage(text, sender) {
    const div = document.createElement('div');
    div.classList.add('message');
    div.classList.add(sender === 'user' ? 'user-message' : 'system-message');

    // Simple markdown parsing for bold/italic if needed, or just text
    div.textContent = text;

    chatMessages.appendChild(div);
    scrollToBottom();
    return div;
}

function addLoadingIndicator() {
    const id = 'loading-' + Date.now();
    const div = document.createElement('div');
    div.id = id;
    div.classList.add('message', 'system-message');
    div.innerHTML = '<span style="opacity:0.6">Reflexionando...</span>';
    chatMessages.appendChild(div);
    scrollToBottom();
    return id;
}

function removeMessage(id) {
    const el = document.getElementById(id);
    if (el) el.remove();
}

function scrollToBottom() {
    chatMessages.scrollTop = chatMessages.scrollHeight;
}
