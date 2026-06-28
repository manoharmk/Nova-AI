import React from 'react';

/**
 * MessageBubble renders individual chat history messages.
 *
 * @param {Object} props
 * @param {string} props.role - Sender identification: "user" or "assistant"
 * @param {string} props.content - Message body text
 */
const MessageBubble = ({ role, content }) => {
  const isUser = role === 'user';

  return (
    <div className={`flex w-full ${isUser ? 'justify-end' : 'justify-start'} mb-4 animate-fade-in`}>
      <div className={`max-w-[75%] rounded-2xl px-4 py-3 shadow-md transition-all duration-300 ${
        isUser
          ? 'bg-gradient-to-tr from-violet-600 to-indigo-600 text-white rounded-br-none font-medium'
          : 'bg-slate-800/80 border border-slate-700/60 text-slate-100 rounded-bl-none'
      }`}>
        {/* Render text with preserve newline spacing */}
        <p className="text-sm md:text-base whitespace-pre-wrap leading-relaxed">
          {content}
        </p>
      </div>
    </div>
  );
};

export default MessageBubble;
