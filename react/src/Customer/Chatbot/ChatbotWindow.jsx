import React, { useState, useEffect } from 'react'; // Bỏ import useEffect không dùng
import Modal from '@mui/material/Modal';
import Box from '@mui/material/Box';
import IconButton from '@mui/material/IconButton';
import CloseIcon from '@mui/icons-material/Close';
import ChatMessages from './ChatMessages';
import ChatInput from './ChatInput';

// --- Cấu hình API ---
// eslint-disable-next-line no-unused-vars
const API_KEY = 'AIzaSyA-gcGDxL7vpkLdGcItB6q40y1dPdVQLBk'; // Giữ nguyên khóa API của bạn
const MODEL_NAME = 'gemini-1.5-flash-latest'; // Sử dụng model mới nhất hoặc model bạn muốn
const GENERATE_API_URL = `https://generativelanguage.googleapis.com/v1beta/models/${MODEL_NAME}:generateContent?key=${API_KEY}`;
// --------------------

// --- Dữ liệu hướng dẫn và sản phẩm ---
const BOT_INSTRUCTIONS = `
Hướng dẫn trả lời: Bạn là trợ lý ảo của "Cửa hàng máy tính dân dụng Bài Tập Lớn". Nhiệm vụ của bạn là cung cấp thông tin chính xác và hữu ích cho khách hàng về cửa hàng và các sản phẩm máy tính để bàn (PC) mà cửa hàng đang kinh doanh, dựa **duy nhất** vào dữ liệu được cung cấp trong prompt này.

**Thông tin cơ bản về cửa hàng:**
* **Tên cửa hàng:** Cửa hàng máy tính dân dụng Bài Tập Lớn
* **Địa chỉ:** 136 P. Sơn Tây, Kim Mã, Ba Đình, Hà Nội 100000, Việt Nam
* **Giờ mở cửa:** 8:00 sáng - 5:00 chiều (17:00), từ Thứ Hai đến Thứ Bảy.
* **Ngày đóng cửa:** Chủ Nhật.
* **Đánh giá:** 4.8 sao trên Google Maps (có thể đề cập nếu khách hỏi về uy tín).
* **(Tùy chọn)** Số điện thoại liên hệ: [Bạn có thể thêm số điện thoại ở đây nếu muốn bot cung cấp]
* **(Tùy chọn)** Trang web/Fanpage: [Bạn có thể thêm link ở đây nếu muốn bot cung cấp]

**Thông tin sản phẩm (PC / Máy tính để bàn):**
Dữ liệu chi tiết về các mẫu máy tính để bàn (PC) mà cửa hàng đang kinh doanh được liệt kê ngay dưới đây. **Đây là nguồn thông tin duy nhất và chính xác** bạn được phép sử dụng để trả lời các câu hỏi về sản phẩm. **TUYỆT ĐỐI KHÔNG** sử dụng kiến thức bên ngoài hoặc thông tin không có trong danh sách này.


Thông tin chi tiết các PC trong cơ sở dữ liệu PC_Shop:

---

**1. PC Đồ Họa AI - Ryzen 9 9950X/ 64GB/ RTX 5090 (ID: 1)**

Mã sản phẩm (ID): 1
Model: PC Đồ Họa AI - Ryzen 9 9950X/ 64GB/ RTX 5090
Thương hiệu PC: Unknown (Không rõ hãng lắp ráp/bán)
Loại: Gaming, Đồ họa
Giá gốc: 100,000,000 VND
Giảm giá: 10%
Giá sau giảm giá: 90,000,000 VND
CPU: AMD Ryzen 9 9950X (16 nhân, 32 luồng, 3.5 GHz - 5.7 GHz, 64MB Cache)
GPU: NVIDIA RTX 5090 (24GB VRAM)
RAM: 64GB DDR4
Ổ cứng: 2000GB SSD (2TB)
Nguồn (PSU): 1300W
Hệ điều hành: Windows 10
Màu sắc: Đen (Số lượng: 10)
Bảo hành: 12 tháng
Thông tin thêm: Thiết kế Phổ thông, Xuất xứ Trung Quốc, Không kèm màn hình, Không kèm bàn phím.
Ngày tạo: 2025-04-07 20:34:07

---

**2. PC GVN Intel i3-12100F/ VGA RX 6500XT (Powered by ASUS) (ID: 2)**

Mã sản phẩm (ID): 2
Model: PC GVN Intel i3-12100F/ VGA RX 6500XT (Powered by ASUS)
Thương hiệu PC: ASUS
Loại: Văn Phòng
Giá gốc: 10,000,000 VND
Giảm giá: 10%
Giá sau giảm giá: 9,000,000 VND
CPU: Intel Core i3-12100F (4 nhân, 8 luồng, 3.3 GHz - 4.3 GHz, 12MB Cache)
GPU: ASUS RX 6500XT (4GB VRAM)
RAM: 16GB DDR4
Ổ cứng: 500GB SSD
Nguồn (PSU): 650W
Hệ điều hành: Windows 10
Màu sắc: Đen (Số lượng: 10)
Bảo hành: 12 tháng
Thông tin thêm: Thiết kế Phổ thông, Xuất xứ Trung Quốc, Không kèm màn hình (ghi 1 inch có thể là lỗi nhập liệu), Không kèm bàn phím.
Ngày tạo: 2025-04-07 20:43:27

---

**3. PC GVN Homework i3 - GT (ID: 3)**

Mã sản phẩm (ID): 3
Model: PC GVN Homework i3 - GT
Thương hiệu PC: Unknown (Không rõ hãng lắp ráp/bán)
Loại: Văn Phòng
Giá gốc: 10,000,000 VND
Giảm giá: 10%
Giá sau giảm giá: 9,000,000 VND
CPU: Intel Core i3-10105F (4 nhân, 4 luồng, 3.0 GHz - 4.3 GHz, 12MB Cache)
GPU: NVIDIA GT1030 (2GB VRAM)
RAM: 8GB DDR4
Ổ cứng: 256GB SSD
Nguồn (PSU): 400W
Hệ điều hành: Windows 10
Màu sắc: Đen (Số lượng: 10)
Bảo hành: 12 tháng
Thông tin thêm: Thiết kế Phổ thông, Xuất xứ Không rõ, Không kèm màn hình (ghi 0.1 inch có thể là lỗi nhập liệu), Không kèm bàn phím.
Ngày tạo: 2025-04-07 21:18:45

---

**4. PC GVN Intel i5-12400F/ VGA RTX 4060 (ID: 4)**

Mã sản phẩm (ID): 4
Model: PC GVN Intel i5-12400F/ VGA RTX 4060
Thương hiệu PC: Unknown (Không rõ hãng lắp ráp/bán)
Loại: Gaming
Giá gốc: 20,000,000 VND
Giảm giá: 15%
Giá sau giảm giá: 17,000,000 VND
CPU: Intel Core i5-12400F (6 nhân, 12 luồng, 2.5 GHz - 4.4 GHz, 18MB Cache)
GPU: NVIDIA RTX 4060 (8GB VRAM)
RAM: 32GB DDR4
Ổ cứng: 1000GB SSD (1TB)
Nguồn (PSU): 650W
Hệ điều hành: Windows 10
Màu sắc: Đen (Số lượng: 10)
Bảo hành: 12 tháng
Thông tin thêm: Thiết kế Phổ thông, Xuất xứ Trung Quốc, Không kèm màn hình, Không kèm bàn phím.
Ngày tạo: 2025-04-07 21:37:31

---
`;
// ---------------------------------


const style = {
  position: 'absolute',
  bottom: '90px', // Khoảng cách từ dưới lên (cao hơn FAB)
  right: '30px',  // Khoảng cách từ phải sang
  width: '90vw', // Chiều rộng tối đa 90% viewport width
  maxWidth: '450px', // Chiều rộng tối đa cụ thể
  height: '70vh', // Chiều cao tối đa 70% viewport height
  maxHeight: '600px', // Chiều cao tối đa cụ thể
  bgcolor: 'background.paper',
  borderRadius: '1rem', // bo góc 2xl
  boxShadow: 24,
  display: 'flex',
  flexDirection: 'column',
  overflow: 'hidden', // Đảm bảo nội dung không tràn ra ngoài modal
  // Thêm transition cho hiệu ứng xuất hiện/ẩn
  transition: 'transform 0.3s ease-out, opacity 0.3s ease-out',
  transform: 'translateY(20px)', // Bắt đầu ẩn dưới một chút
  opacity: 0,
};

const openStyle = {
  ...style,
  transform: 'translateY(0)', // Di chuyển về vị trí cuối cùng
  opacity: 1, // Hiện rõ
};


const ChatbotWindow = ({ open, handleClose }) => {
  const [messages, setMessages] = useState([
    { sender: 'bot', text: `Xin chào! Bạn muốn hỏi gì về cửa hàng hoặc các sản phẩm laptop của chúng tôi?` } // Cập nhật lời chào
  ]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState('');
  const [history, setHistory] = useState([]); // State để lưu lịch sử chat cho API

  // Hàm thêm tin nhắn vào state hiển thị
  const addMessage = (sender, text) => {
    setMessages(prevMessages => [...prevMessages, { sender, text }]);
  };

  // Hàm gửi tin nhắn đến API
  const sendMessageToAI = async (userMessage) => {
    setLoading(true);
    setError('');
    addMessage('user', userMessage); // Thêm tin nhắn người dùng vào UI

    // Tạo nội dung đầy đủ gửi đến API
    const fullPrompt = `${BOT_INSTRUCTIONS}\n${userMessage}`;

    // Cập nhật lịch sử chat cho API
    const currentHistory = [
        ...history,
        { role: "user", parts: [{ text: fullPrompt }] } // Gửi prompt đầy đủ bao gồm cả hướng dẫn
    ];

    try {
      const response = await fetch(GENERATE_API_URL, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        // Gửi lịch sử chat và câu hỏi mới nhất (đã bao gồm hướng dẫn)
        body: JSON.stringify({ contents: currentHistory }),
      });

      if (!response.ok) {
        const errorData = await response.json();
        console.error("API Error (generateContent):", errorData);
        let specificError = errorData?.error?.message || '(Không có thông báo lỗi cụ thể)';
         if (errorData?.error?.status === 'PERMISSION_DENIED') {
             specificError += ' Có thể khóa API không hợp lệ, chưa được kích hoạt hoặc không có quyền truy cập Generative Language API.';
         } else if (response.status === 404) {
             specificError += ` Model "${MODEL_NAME}" không được tìm thấy hoặc không hỗ trợ cho API key này.`;
         } else if (response.status === 400) {
              specificError += ' Yêu cầu không hợp lệ. Kiểm tra cấu trúc body gửi đi.';
          } else if (response.status === 429) {
              specificError += ' Đã đạt giới hạn quota. Vui lòng thử lại sau.';
          }
        throw new Error(`Lỗi API: ${response.status} ${response.statusText}. ${specificError}`);
      }

      const data = await response.json();
      console.log("API Response (generateContent):", data);

      let botResponseText = "Xin lỗi, tôi không thể xử lý yêu cầu này.";
        if (data.candidates && data.candidates.length > 0 &&
            data.candidates[0].content && data.candidates[0].content.parts &&
            data.candidates[0].content.parts.length > 0 &&
            data.candidates[0].content.parts[0].text) {
            botResponseText = data.candidates[0].content.parts[0].text;
        } else if (data.promptFeedback && data.promptFeedback.blockReason) {
            console.warn("Content blocked:", data.promptFeedback.blockReason);
            const blockDetails = data.promptFeedback.safetyRatings ? ` (${data.promptFeedback.safetyRatings.map(r => r.category + ': ' + r.probability).join(', ')})` : '';
            botResponseText = `Nội dung bị chặn: ${data.promptFeedback.blockReason}${blockDetails}. Vui lòng thử lại với nội dung khác.`;
        } else if (data.candidates && data.candidates.length > 0 && data.candidates[0].finishReason && data.candidates[0].finishReason !== 'STOP') {
             console.warn("Generation finished unexpectedly:", data.candidates[0].finishReason);
             botResponseText = `Phản hồi bị dừng sớm (${data.candidates[0].finishReason}). Có thể do độ dài tối đa hoặc bộ lọc an toàn.`;
         }
         else {
             console.error("Unexpected API response structure:", data);
         }

      addMessage('bot', botResponseText); // Thêm câu trả lời của bot vào UI

      // Cập nhật lịch sử chat với câu trả lời của bot
      setHistory([
          ...currentHistory,
          { role: "model", parts: [{ text: botResponseText }] }
      ]);


    } catch (error) {
      console.error("Lỗi khi gọi API generateContent:", error);
      setError(`Đã xảy ra lỗi: ${error.message}. Vui lòng kiểm tra console.`);
      addMessage('bot', `Lỗi: ${error.message}`); // Hiển thị lỗi trong chatbox
        // Không cập nhật history nếu có lỗi
    } finally {
      setLoading(false);
    }
  };

  return (
    <Modal
      open={open}
      onClose={handleClose}
      aria-labelledby="chatbot-modal-title"
      aria-describedby="chatbot-modal-description"
      closeAfterTransition // Cho phép hiệu ứng transition
    >
      <Box sx={open ? openStyle : style}>
        {/* Header */}
        <div className="bg-blue-600 text-white p-3 rounded-t-xl shadow-md flex justify-between items-center">
          <h1 id="chatbot-modal-title" className="text-lg font-semibold text-center flex-1">
            Chatbot Hỗ Trợ
          </h1>
          <IconButton onClick={handleClose} size="small" sx={{ color: 'white' }}>
            <CloseIcon />
          </IconButton>
        </div>

        {/* Message Area */}
        <ChatMessages messages={messages} />

         {/* Error Message Display */}
        {error && (
          <div className="p-2 text-red-600 text-center text-sm bg-red-50 border-t border-red-200">
              {error}
          </div>
        )}


        {/* Input Area */}
        <ChatInput onSendMessage={sendMessageToAI} loading={loading} />
      </Box>
    </Modal>
  );
};

export default ChatbotWindow;
