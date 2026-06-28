import React, { useState, useEffect, useRef } from 'react';
import axios from 'axios';
import ChatWindow from './components/ChatWindow';
import ChatInput from './components/ChatInput';

function App() {
  const [messages, setMessages] = useState([]);
  const [isLoading, setIsLoading] = useState(false);
  const [isStreaming, setIsStreaming] = useState(false);
  const [error, setError] = useState(null);

  const abortControllerRef = useRef(null);

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
    setIsStreaming(false);

    // Set up AbortController for cancel capability
    const abortController = new AbortController();
    abortControllerRef.current = abortController;

    try {
      const response = await fetch('/api/chat/stream', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({ message: content }),
        signal: abortController.signal,
      });

      if (!response.ok) {
        throw new Error('Connection failed. Please ensure the backend server is running.');
      }

      const reader = response.body.getReader();
      const decoder = new TextDecoder('utf-8');
      
      let done = false;
      let buffer = '';
      let isFirstToken = true;

      while (!done) {
        const { value, done: readerDone } = await reader.read();
        done = readerDone;
        
        if (value) {
          buffer += decoder.decode(value, { stream: !done });
          
          let boundary = buffer.indexOf('\n\n');
          while (boundary !== -1) {
            const block = buffer.substring(0, boundary);
            buffer = buffer.substring(boundary + 2);
            
            const lines = block.split('\n');
            for (const line of lines) {
              if (line.startsWith('data:')) {
                const token = line.substring(5);
                
                if (isFirstToken) {
                  isFirstToken = false;
                  setIsLoading(false);
                  setIsStreaming(true);
                  // Insert the initial empty assistant bubble
                  setMessages((prev) => [...prev, { role: 'assistant', content: token }]);
                } else {
                  // Append subsequent tokens to the active assistant bubble
                  setMessages((prev) => {
                    const updated = [...prev];
                    const lastIndex = updated.length - 1;
                    if (lastIndex >= 0 && updated[lastIndex].role === 'assistant') {
                      updated[lastIndex] = {
                        ...updated[lastIndex],
                        content: updated[lastIndex].content + token
                      };
                    }
                    return updated;
                  });
                }
              }
            }
            boundary = buffer.indexOf('\n\n');
          }
        }
      }
      
      // If we finished loading but never received any data block (e.g. empty response)
      if (isFirstToken) {
        setIsLoading(false);
        setMessages((prev) => [...prev, { role: 'assistant', content: 'Nova is currently unavailable. Please try again.' }]);
      }

    } catch (err) {
      if (err.name === 'AbortError') {
        console.log('Stream generation aborted by user.');
      } else {
        setError(err.message || 'An unexpected error occurred.');
      }
    } finally {
      setIsLoading(false);
      setIsStreaming(false);
      abortControllerRef.current = null;
    }
  };

  const handleCancelGeneration = () => {
    if (abortControllerRef.current) {
      abortControllerRef.current.abort();
    }
  };

  const handleRetry = () => {
    const userQueries = messages.filter((msg) => msg.role === 'user');
    if (userQueries.length > 0) {
      const lastQuery = userQueries[userQueries.length - 1].content;
      handleSendMessage(lastQuery);
    }
  };

  const handleRegenerate = () => {
    const userQueries = messages.filter((msg) => msg.role === 'user');
    if (userQueries.length > 0) {
      const lastQuery = userQueries[userQueries.length - 1].content;
      
      setMessages((prev) => {
        const updated = [...prev];
        const lastIndex = updated.length - 1;
        if (lastIndex >= 0 && updated[lastIndex].role === 'assistant') {
          updated.pop();
        }
        return updated;
      });
      
      handleSendMessage(lastQuery);
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
        
        {messages.length > 0 && (
          <button
            onClick={handleClearMemory}
            className="text-xs text-slate-400 hover:text-red-400 border border-slate-700/80 hover:border-red-500/30 bg-slate-800/20 px-3 py-1.5 rounded-lg transition-colors cursor-pointer"
          >
            Clear History
          </button>
        )}
      </header>

      <ChatWindow 
        messages={messages} 
        isLoading={isLoading} 
        isStreaming={isStreaming} 
        error={error}
        onRetry={handleRetry}
        onRegenerate={handleRegenerate}
      />

      <ChatInput 
        onSendMessage={handleSendMessage} 
        isLoading={isLoading} 
        isStreaming={isStreaming}
        onCancel={handleCancelGeneration}
      />
    </div>
  );
}

export default App;
