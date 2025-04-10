import React, { useEffect, useRef } from 'react';
import ReactMarkdown from 'react-markdown'; // Import react-markdown
import remarkMath from 'remark-math';       // Import plugin cho cú pháp math
import rehypeKatex from 'rehype-katex';     // Import plugin để render math bằng KaTeX
// CSS KaTeX đã được import ở index.js hoặc App.js
// import 'katex/dist/katex.min.css'; // Không cần import lại ở đây nếu đã import ở file chính

import './chatbot.css'; // Import CSS để tạo hiệu ứng fadeIn (nếu có)

const ChatMessages = ({ messages }) => {
  const chatboxRef = useRef(null);

  // Tự động cuộn xuống tin nhắn mới nhất
  useEffect(() => {
    if (chatboxRef.current) {
      chatboxRef.current.scrollTop = chatboxRef.current.scrollHeight;
    }
  }, [messages]);

  return (
    <div
      ref={chatboxRef}
      id="chatbox"
      className="flex-1 p-4 overflow-y-auto space-y-4 bg-gray-50 prose prose-sm max-w-none" // Thêm class 'prose' để Tailwind định dạng Markdown cơ bản
      style={{ scrollbarWidth: 'thin' }} // Cho Firefox
    >
      {messages.map((msg, index) => (
        <div
          key={index}
          className={`flex ${msg.sender === 'user' ? 'justify-end' : 'justify-start'}`}
        >
          <div
            className={`p-3 rounded-xl shadow ${
              msg.sender === 'user'
                ? 'bg-blue-500 text-white prose-invert' // Thêm prose-invert cho text màu sáng trên nền tối
                : 'bg-gray-200 text-gray-800'
            }`}
          >
            {/* Sử dụng ReactMarkdown với các plugin */}
            <ReactMarkdown
              remarkPlugins={[remarkMath]} // Cho phép nhận diện cú pháp LaTeX ($...$, $$...$$)
              rehypePlugins={[rehypeKatex]} // Render cú pháp đó bằng KaTeX
              // Xóa bỏ linkTarget="_blank"
              // Thay vào đó, sử dụng 'components' để tùy chỉnh thẻ 'a'
              components={{
                // Ghi đè cách render thẻ <a>
                a: ({node, ...props}) => (
                  <a
                    {...props}
                    target="_blank" // Mở link trong tab mới
                    rel="noopener noreferrer" // Thêm rel để bảo mật
                  />
                )
              }}
            >
              {msg.text}
            </ReactMarkdown>
          </div>
        </div>
      ))}
    </div>
  );
};

export default ChatMessages;
