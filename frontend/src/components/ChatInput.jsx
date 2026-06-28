import React, { useState } from 'react';

/**
 * ChatInput renders the bottom input panel containing message box and submit buttons.
 *
 * @param {Object} props
 * @param {Function} props.onSendMessage - Triggered when a complete message is submitted
 * @param {boolean} props.isLoading - Current loading status from active API request
 */
const ChatInput = ({ onSendMessage, isLoading }) => {
  const [message, setMessage] = useState('');

  const handleSubmit = (e) => {
    e.preventDefault();
    if (!message.trim() || isLoading) return;
    onSendMessage(message.trim());
    setMessage('');
  };

  const handleKeyDown = (e) => {
    if (e.key === 'Enter' && !e.shiftKey) {
      handleSubmit(e);
    }
  };

  return (
    <form
      onSubmit={handleSubmit}
      className="p-4 bg-slate-900/90 backdrop-blur-md border-t border-slate-800 flex items-center gap-3 sticky bottom-0 z-10"
    >
      <input
        type="text"
        value={message}
        onChange={(e) => setMessage(e.target.value)}
        onKeyDown={handleKeyDown}
        placeholder={isLoading ? "Nova is thinking..." : "Type your message here..."}
        disabled={isLoading}
        className="flex-1 bg-slate-800/80 text-slate-100 placeholder-slate-400 border border-slate-700/80 rounded-xl px-4 py-3 focus:outline-none focus:ring-2 focus:ring-violet-500 focus:border-transparent disabled:opacity-50 transition-all text-sm md:text-base"
        autoFocus
      />
      <button
        type="submit"
        disabled={isLoading || !message.trim()}
        className="bg-violet-600 hover:bg-violet-500 active:bg-violet-700 disabled:opacity-50 text-white rounded-xl p-3 flex items-center justify-center transition-all shadow-lg hover:shadow-violet-600/30 cursor-pointer"
      >
        <svg
          xmlns="http://www.w3.org/2000/svg"
          viewBox="0 0 24 24"
          fill="currentColor"
          className="w-5 h-5"
        >
          <path d="M3.478 2.404a.75.75 0 0 0-.926.941l2.432 7.905H13.5a.75.75 0 0 1 0 1.5H4.984l-2.432 7.905a.75.75 0 0 0 .926.94 60.519 60.519 0 0 0 18.445-8.986.75.75 0 0 0 0-1.218A60.517 60.517 0 0 0 3.478 2.404Z" />
        </svg>
      </button>
    </form>
  );
};

export default ChatInput;
