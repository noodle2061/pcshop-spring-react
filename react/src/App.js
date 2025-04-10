import React, { useState } from 'react'; // Thêm useState
import { Route, Routes } from "react-router-dom";
import "./App.css";
import CustomerRoutes from "./Routers/CustomerRoutes";
import AdminPanel from "./Admin/AdminPanel";
import { useDispatch, useSelector } from "react-redux";
import { useEffect } from "react";
import { getUser } from "./Redux/Auth/Action";
import ChatbotButton from './Customer/Chatbot/ChatbotButton'; // Import nút chatbot
import ChatbotWindow from './Customer/Chatbot/ChatbotWindow'; // Import cửa sổ 
import 'katex/dist/katex.min.css';

function App() {
  const { auth } = useSelector(store => store);
  const dispatch = useDispatch();
  const jwt = localStorage.getItem("jwt");
  const [isChatbotOpen, setIsChatbotOpen] = useState(false); // State quản lý trạng thái mở/đóng chatbot

  useEffect(() => {
    if (jwt) {
      dispatch(getUser(jwt));
    }
  }, [jwt, dispatch]); // Thêm dispatch vào dependency array

  const handleToggleChatbot = () => {
    setIsChatbotOpen(!isChatbotOpen);
  };

  const handleCloseChatbot = () => {
    setIsChatbotOpen(false);
  }

  return (
    <div className="relative"> {/* Thêm relative để định vị FAB */}
      <Routes>
        <Route path="/*" element={<CustomerRoutes />} />
        {auth.user?.role === "ADMIN" && <Route path="/admin/*" element={<AdminPanel />} />}
      </Routes>

      {/* Luôn hiển thị nút Chatbot ở mọi trang Customer */}
      {/* Bạn có thể thêm điều kiện để chỉ hiển thị ở trang chủ nếu muốn */}
      {/* Ví dụ: {location.pathname === '/' && <ChatbotButton onClick={handleToggleChatbot} />} */}
      <ChatbotButton onClick={handleToggleChatbot} />
      <ChatbotWindow open={isChatbotOpen} handleClose={handleCloseChatbot} />
    </div>
  );
}

export default App;