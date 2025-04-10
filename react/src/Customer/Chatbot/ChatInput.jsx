import React, { useState } from 'react';
import SendIcon from '@mui/icons-material/Send'; // Icon cho nút gửi

const ChatInput = ({ onSendMessage, loading }) => {
  const [inputValue, setInputValue] = useState('');

  const handleInputChange = (e) => {
    setInputValue(e.target.value);
  };

  const handleSend = () => {
    if (inputValue.trim() && !loading) {
      onSendMessage(inputValue.trim());
      setInputValue('');
    }
  };

  const handleKeyPress = (e) => {
    if (e.key === 'Enter' && !e.shiftKey) { // Gửi khi nhấn Enter (không phải Shift+Enter)
      e.preventDefault(); // Ngăn không xuống dòng trong input
      handleSend();
    }
  };

  return (
    <div className="p-4 border-t border-gray-200 bg-white rounded-b-2xl">
      <div className="flex items-center space-x-3">
        <input
          type="text"
          id="userInput"
          placeholder="Nhập tin nhắn của bạn..."
          value={inputValue}
          onChange={handleInputChange}
          onKeyPress={handleKeyPress}
          disabled={loading}
          className="flex-1 border border-gray-300 rounded-xl p-3 focus:outline-none focus:ring-2 focus:ring-blue-500 transition duration-200"
        />
        <button
          id="sendButton"
          onClick={handleSend}
          disabled={loading || !inputValue.trim()}
          className={`bg-blue-600 hover:bg-blue-700 text-white font-semibold py-3 px-5 rounded-xl transition duration-200 focus:outline-none focus:ring-2 focus:ring-blue-500 focus:ring-offset-2 shadow active:bg-blue-800 ${loading || !inputValue.trim() ? 'opacity-50 cursor-not-allowed' : ''}`}
        >
          <SendIcon />
        </button>
      </div>
      {loading && (
         <div id="loading-indicator" class="p-2 text-gray-500 text-center italic">
           Bot đang soạn tin...
         </div>
      )}
    </div>
  );
};

export default ChatInput;