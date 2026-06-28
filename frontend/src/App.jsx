import React, { useState, useEffect } from 'react';
import axios from 'axios';
import ChatWindow from './components/ChatWindow';
import ChatInput from './components/ChatInput';
import { sendChatMessage } from './services/chatApi';

function App() {
  const [messages, setMessages] = useState([]);
  const [isLoading, setIsLoading] = useState(false);
  const [error, setError] = useState(null);

  // Load existing memory history on mount
  useEffect(() => {
    const fetchHistory = async () => {
      try {
        const response = await axios.get('/api/memory');
        setMessages(response.data);
      } catch (err) {
        console.error('Failed to load history:', err);
      }
    };
    fetchHistory();
  }, []);

  const handleSendMessage = async (content) => {
    // Add user message to UI immediately
    const userMsg = { role: 'user', content };
    setMessages((prev) => [...prev, userMsg]);
    
    setIsLoading(true);
    setError(null);

    try {
      const reply = await sendChatMessage(content);
      const assistantMsg = { role: 'assistant', content: reply };
      setMessages((prev) => [...prev, assistantMsg]);
    } catch (err) {
      setError(err.message || 'An unexpected error occurred.');
    } finally {
      setIsLoading(false);
    }
  };

  const handleClearMemory = async () => {
    if (window.confirm('Are you sure you want to clear your conversation history?')) {
      try {
        await axios.post('/api/memory/clear');
        setMessages([]);
      } catch (err) {
        setError('Failed to clear conversation memory.');
      }
    }
  };

  return (
    <div className="flex flex-col h-screen bg-[#070913] text-slate-100 font-sans">
      {/* Header bar */}
      <header className="glass-panel px-6 py-4 flex items-center justify-between border-b border-slate-800 shadow-md">
        <div className="flex items-center gap-3">
          <div className="w-9 h-9 rounded-xl bg-gradient-to-tr from-violet-600 to-indigo-600 flex items-center justify-center shadow-lg shadow-violet-500/20">
            <svg
              xmlns="http://www.w3.org/2000/svg"
              viewBox="0 0 24 24"
              fill="currentColor"
              className="w-5 h-5 text-white"
            >
              <path d="M12 2a10 10 0 1 0 10 10A10 10 0 0 0 12 2Zm.75 6.75a.75.75 0 0 1 1.5 0v3.5a.75.75 0 0 1-1.5 0v-3.5Zm-.75 9a1 1 0 1 1 1-1 1 1 0 0 1-1 1Z" />
            </svg>
          </div>
          <div>
            <h1 className="text-base md:text-lg font-bold tracking-wide bg-gradient-to-r from-slate-100 to-slate-300 bg-clip-text text-transparent">
              Nova AI
            </h1>
            <p className="text-[10px] md:text-xs text-emerald-400 font-medium flex items-center gap-1.5">
              <span className="w-1.5 h-1.5 rounded-full bg-emerald-400 animate-pulse" />
              Connected
            </p>
          </div>
        </div>
        
        {/* Clear memory button */}
        {messages.length > 0 && (
          <button
            onClick={handleClearMemory}
            className="text-xs text-slate-400 hover:text-red-400 border border-slate-700/80 hover:border-red-500/30 bg-slate-800/20 px-3 py-1.5 rounded-lg transition-colors cursor-pointer"
          >
            Clear History
          </button>
        )}
      </header>

      {/* Main chat window */}
      <ChatWindow messages={messages} isLoading={isLoading} error={error} />

      {/* Input controls */}
      <ChatInput onSendMessage={handleSendMessage} isLoading={isLoading} />
    </div>
  );
}

export default App;
