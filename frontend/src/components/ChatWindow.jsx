import React, { useEffect, useRef } from 'react';
import MessageBubble from './MessageBubble';

/**
 * ChatWindow renders the message viewport, loading indicators, empty states, and auto-scrolls.
 *
 * @param {Object} props
 * @param {Array} props.messages - Array of message objects { role: "user" | "assistant", content: "..." }
 * @param {boolean} props.isLoading - Backend pending state
 * @param {string|null} props.error - Error details if communication fails
 */
const ChatWindow = ({ messages, isLoading, error }) => {
  const bottomRef = useRef(null);

  // Auto scroll logic
  useEffect(() => {
    bottomRef.current?.scrollIntoView({ behavior: 'smooth' });
  }, [messages, isLoading]);

  return (
    <div className="flex-1 overflow-y-auto px-4 py-6 md:px-8 space-y-4">
      {messages.length === 0 ? (
        // Premium Empty Welcome Screen
        <div className="h-full flex flex-col items-center justify-center text-center max-w-xl mx-auto py-12 animate-fade-in">
          <div className="w-16 h-16 rounded-2xl bg-gradient-to-tr from-violet-600 to-indigo-600 flex items-center justify-center shadow-lg shadow-violet-500/20 mb-6">
            <svg
              xmlns="http://www.w3.org/2000/svg"
              fill="none"
              viewBox="0 0 24 24"
              strokeWidth={1.8}
              stroke="currentColor"
              className="w-8 h-8 text-white animate-pulse"
            >
              <path
                strokeLinecap="round"
                strokeLinejoin="round"
                d="M9.813 15.904 9 21l8.982-11.795h-5.282L14.75 3 5.768 14.795h5.282Z"
              />
            </svg>
          </div>
          <h2 className="text-2xl md:text-3xl font-bold text-slate-100 tracking-tight mb-3">
            Say Hello to Nova
          </h2>
          <p className="text-sm md:text-base text-slate-400 leading-relaxed mb-8">
            Your premium desktop AI assistant. Powered by Clean Architecture and Spring AI. Ask me about math, system info, time, or general questions!
          </p>
          
          {/* Example suggestion tags */}
          <div className="grid grid-cols-2 gap-3 w-full max-w-md">
            <div className="glass-card rounded-xl p-3 text-left hover:border-slate-600 transition-colors">
              <p className="text-xs text-violet-400 font-semibold mb-1">LOCAL TOOL</p>
              <p className="text-xs md:text-sm text-slate-300">"What time is it?"</p>
            </div>
            <div className="glass-card rounded-xl p-3 text-left hover:border-slate-600 transition-colors">
              <p className="text-xs text-violet-400 font-semibold mb-1">DESKTOP LAUNCH</p>
              <p className="text-xs md:text-sm text-slate-300">"open vscode"</p>
            </div>
            <div className="glass-card rounded-xl p-3 text-left hover:border-slate-600 transition-colors">
              <p className="text-xs text-violet-400 font-semibold mb-1">LOCAL CALCULATOR</p>
              <p className="text-xs md:text-sm text-slate-300">"calculate 25 * 40"</p>
            </div>
            <div className="glass-card rounded-xl p-3 text-left hover:border-slate-600 transition-colors">
              <p className="text-xs text-violet-400 font-semibold mb-1">GENERAL INQUIRY</p>
              <p className="text-xs md:text-sm text-slate-300">"What is Spring Boot?"</p>
            </div>
          </div>
        </div>
      ) : (
        <div className="max-w-3xl mx-auto w-full">
          {messages.map((msg, index) => (
            <MessageBubble key={index} role={msg.role} content={msg.content} />
          ))}
        </div>
      )}

      {/* Loading Spinner */}
      {isLoading && (
        <div className="flex justify-start max-w-3xl mx-auto w-full mb-4 pl-4">
          <div className="flex items-center gap-3 bg-slate-800/50 border border-slate-700/50 rounded-2xl px-4 py-3 text-slate-300 shadow-md">
            <div className="flex gap-1.5 items-center">
              <div className="w-2.5 h-2.5 rounded-full bg-violet-500 animate-bounce" style={{ animationDelay: '0ms' }} />
              <div className="w-2.5 h-2.5 rounded-full bg-violet-500 animate-bounce" style={{ animationDelay: '150ms' }} />
              <div className="w-2.5 h-2.5 rounded-full bg-violet-500 animate-bounce" style={{ animationDelay: '300ms' }} />
            </div>
            <span className="text-xs md:text-sm text-slate-400 font-medium select-none">Thinking...</span>
          </div>
        </div>
      )}

      {/* Error Alert Display */}
      {error && (
        <div className="max-w-3xl mx-auto w-full mb-4">
          <div className="bg-red-500/10 border border-red-500/30 text-red-200 rounded-xl px-4 py-3 flex gap-3 items-center text-sm md:text-base shadow-sm">
            <svg
              xmlns="http://www.w3.org/2000/svg"
              fill="none"
              viewBox="0 0 24 24"
              strokeWidth={1.8}
              stroke="currentColor"
              className="w-5 h-5 text-red-400 shrink-0"
            >
              <path
                strokeLinecap="round"
                strokeLinejoin="round"
                d="M12 9v3.75m9-.75a9 9 0 1 1-18 0 9 9 0 0 1 18 0Zm-9 7.5h.008v.008H12v-.008Z"
              />
            </svg>
            <span className="leading-tight">{error}</span>
          </div>
        </div>
      )}

      {/* Anchor reference to scroll down to */}
      <div ref={bottomRef} />
    </div>
  );
};

export default ChatWindow;
