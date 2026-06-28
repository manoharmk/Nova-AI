import React, { useState } from 'react';
import { marked } from 'marked';

/**
 * MessageBubble renders individual chat history messages with Markdown support and Copy actions.
 *
 * @param {Object} props
 * @param {string} props.role - Sender identification: "user" or "assistant"
 * @param {string} props.content - Message body text
 * @param {boolean} props.isStreaming - True if this message is actively receiving chunks
 */
const MessageBubble = ({ role, content, isStreaming }) => {
  const isUser = role === 'user';
  const [copied, setCopied] = useState(false);

  const handleCopy = () => {
    navigator.clipboard.writeText(content);
    setCopied(true);
    setTimeout(() => setCopied(false), 2000);
  };

  // Convert markdown to HTML safely using marked
  const getHtmlContent = () => {
    try {
      return { __html: marked.parse(content || '') };
    } catch (e) {
      return { __html: content };
    }
  };

  return (
    <div className={`flex w-full ${isUser ? 'justify-end' : 'justify-start'} mb-4 group`}>
      <div className={`relative max-w-[80%] rounded-2xl px-4 py-3 shadow-md border ${
        isUser
          ? 'bg-gradient-to-tr from-violet-600 to-indigo-600 border-violet-500 text-white rounded-br-none font-medium'
          : 'bg-slate-800/80 border-slate-700/60 text-slate-100 rounded-bl-none'
      }`}>
        {/* Message content display */}
        {isUser ? (
          <p className="text-sm md:text-base whitespace-pre-wrap leading-relaxed">
            {content}
          </p>
        ) : (
          <div 
            className={`text-sm md:text-base markdown-body leading-relaxed ${isStreaming ? 'typing-cursor' : ''}`}
            dangerouslySetInnerHTML={getHtmlContent()}
          />
        )}

        {/* Copy button displayed on hover for assistant messages */}
        {!isUser && content && (
          <button
            onClick={handleCopy}
            title="Copy message to clipboard"
            className="absolute top-2.5 right-2.5 opacity-0 group-hover:opacity-100 transition-opacity duration-200 bg-slate-700 hover:bg-slate-600 text-slate-300 p-1.5 rounded-lg border border-slate-600 hover:border-slate-500 cursor-pointer shadow-sm"
          >
            {copied ? (
              <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" strokeWidth={2} stroke="currentColor" className="w-3.5 h-3.5 text-emerald-400">
                <path strokeLinecap="round" strokeLinejoin="round" d="m4.5 12.75 6 6 9-13.5" />
              </svg>
            ) : (
              <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" strokeWidth={1.8} stroke="currentColor" className="w-3.5 h-3.5">
                <path strokeLinecap="round" strokeLinejoin="round" d="M15.75 17.25v3.375c0 .621-.504 1.125-1.125 1.125h-9.75a1.125 1.125 0 0 1-1.125-1.125V7.875c0-.621.504-1.125 1.125-1.125H5.25m11.9-3.664A2.251 2.251 0 0 0 15 2.25h-1.5a2.251 2.251 0 0 0-2.15 1.586m5.8 0c.065.21.1.433.1.664v.75h-6V4.5c0-.231.035-.454.1-.664M6.75 7.5H4.875c-.621 0-1.125.504-1.125 1.125v12c0 .621.504 1.125 1.125 1.125h9.75c.621 0 1.125-.504 1.125-1.125V16.5M16.5 7.5h3.375c.621 0 1.125.504 1.125 1.125v11.25c0 .621-.504 1.125-1.125 1.125H16.5" />
              </svg>
            )}
          </button>
        )}
      </div>
    </div>
  );
};

export default MessageBubble;
