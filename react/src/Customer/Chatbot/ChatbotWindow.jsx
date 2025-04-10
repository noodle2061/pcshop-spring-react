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
Bạn là trợ lý ảo của "Cửa hàng máy tính dân dụng Bài Tập Lớn". Nhiệm vụ của bạn là cung cấp thông tin chính xác và hữu ích cho khách hàng về cửa hàng và các sản phẩm máy tính (chủ yếu là laptop) mà cửa hàng đang kinh doanh, dựa **duy nhất** vào dữ liệu được cung cấp trong prompt này.

**Thông tin cơ bản về cửa hàng:**
* **Tên cửa hàng:** Cửa hàng máy tính dân dụng Bài Tập Lớn
* **Địa chỉ:** 136 P. Sơn Tây, Kim Mã, Ba Đình, Hà Nội 100000, Việt Nam
* **Giờ mở cửa:** 8:00 sáng - 5:00 chiều (17:00), từ Thứ Hai đến Thứ Bảy.
* **Ngày đóng cửa:** Chủ Nhật.
* **Đánh giá:** 4.8 sao trên Google Maps (có thể đề cập nếu khách hỏi về uy tín).
* **(Tùy chọn)** Số điện thoại liên hệ: [Bạn có thể thêm số điện thoại ở đây nếu muốn bot cung cấp]
* **(Tùy chọn)** Trang web/Fanpage: [Bạn có thể thêm link ở đây nếu muốn bot cung cấp]

**Thông tin sản phẩm (Laptop):**
Dữ liệu chi tiết về các mẫu laptop mà cửa hàng đang kinh doanh được liệt kê ngay dưới đây. **Đây là nguồn thông tin duy nhất và chính xác** bạn được phép sử dụng để trả lời các câu hỏi về sản phẩm. **TUYỆT ĐỐI KHÔNG** sử dụng kiến thức bên ngoài hoặc thông tin không có trong danh sách này.

--- Dữ liệu Laptop ---
Laptop ID: 1
Model: 14s-dq5121TU
Brand: HP
Price: 12990000
Discount Percent: 14.0
Battery Charger: Dung lượng 45W
Created At: 2024-12-21 19:46:43.422860
Design: 32.4 x 22.5 x 1.79 cm, nặng 1.36 kg, mặt lưng nhựa
Disk Capacity: 512 GB
Disk Detail: SSD 1 M2 PCIe, không hỗ trợ nâng cấp
Keyboard Type:  Chiclet Keyboard
Num Ratings: 0
Origin: Trung Quốc
RAM Detail: DDR4, 2 khe cắm rời, hỗ trợ tối đa 32 GB
RAM Memory: 8 GB
Screen Detail:  Anti-glare LED-backlit, 1920 x 1080 pixels (16:9)
Screen Size: 14.0 inches
Status: NULL
Warranty: 12 months
CPU: Intel Core i3 1215U (Speed: 1.2 GHz, Max Speed: 4.4 GHz, Cores: 6, Threads: 8, Cache: 10.0 MB)
OS Version: Windows 11 Home
Categories: Văn Phòng, AI
Colors: Bạc (Quantity: 100)
GPUs: Intel UHD Graphics (Integrated, Memory: Unknown)

-----------------------------------

Laptop ID: 2
Model: TUF Gaming FA507NUR LP101W
Brand: Asus
Price: 28490000
Discount Percent: 16.0
Battery Charger: Dung lượng 90W, Sạc 240W
Created At: 2024-12-21 19:57:03.608893
Design:  35.97 x 23.25 x 1.79 ~ 1.79 cm, nặng 2.2 kg, mặt lưng kim loại
Disk Capacity: 512 GB
Disk Detail: SSD 1 M2 NVMe, 2 khe cắm, hỗ trợ nâng cấp tối đa 1TB
Keyboard Type:  Backlit Chiclet Keyboard
Num Ratings: 0
Origin: Trung Quốc
RAM Detail: DDR5, 2 khe cắm rời, hỗ trợ tối đa 32 GB
RAM Memory: 16 GB
Screen Detail:  Anti-glare LED, 1920 x 1080 pixels (16:9), tần số quét 144, tấm nền IPS
Screen Size: 15.6 inches
Status: NULL
Warranty: 24 months
CPU: AMD Ryzen 7 7435HS (Speed: 3.1 GHz, Max Speed: 4.5 GHz, Cores: 8, Threads: 16, Cache: 20.0 MB)
OS Version: Windows 11 Home
Categories: Gaming - đồ họa, AI
Colors: Xám (Quantity: 100)
GPUs:
  - Unknown Unknown (Integrated, Memory: Unknown)
  - NVIDIA GeForce RTX 4050 (Dedicated, Memory: 6GB GDDR6, TOPS: 194)

-----------------------------------

Laptop ID: 3
Model: Macbook Air M3 13 2024
Brand: Apple
Price: 28490000
Discount Percent: 14.0
Battery Charger: Dung lượng 30W
Created At: 2024-12-21 20:10:29.961999
Design: 30.41 x 21.5 x 1.13 cm, 1.24 kg, vỏ nhôm
Disk Capacity: 256 GB
Disk Detail: SSD
Keyboard Type:  English International Backlit Keyboard
Num Ratings: 0
Origin: Trung Quốc
RAM Detail: LPDDR4, hỗ trợ tối đa 16GB
RAM Memory: 16 GB
Screen Detail: Liquid Retina  2560 x 1644 pixels, tần số quét 60Hz, tầm nền IPS
Screen Size: 13.6 inches
Status: NULL
Warranty: 12 months
CPU: Apple M3 8-Core (Speed: NULL, Max Speed: NULL, Cores: NULL, Threads: NULL, Cache: NULL MB)
OS Version: Windows 10 Home (Note: OS seems inconsistent with Apple brand)
Categories: Văn Phòng, Gaming - đồ họa, AI
Colors:
  - Đen (Quantity: 100)
  - Vàng (Quantity: 100)
  - Xám (Quantity: 100)
  - Bạc (Quantity: 100)
GPUs: Unknown Unknown (Integrated, Memory: Unknown)

-----------------------------------

Laptop ID: 4
Model: IdeaPad 3 14IAH8
Brand: Lenovo
Price: 15990000
Discount Percent: 17.0
Battery Charger: Dung lượng 90Wh, sạc 65W
Created At: 2024-12-23 16:36:14.287132
Design:  324.3 x 213.8 x 17.9 mm, nặng 1.37 kg, vỏ nhựa
Disk Capacity: 512 GB
Disk Detail: SSD M2. PCIe1
Keyboard Type:  Bàn phím cứng
Num Ratings: 0
Origin: Trung Quốc
RAM Detail: DDR5, 2 khe cắm, hỗ trợ tối đa 16GB
RAM Memory: 16 GB
Screen Detail: Anti-Glare 1920 x 1080 pixels, tần số quét 60Hz, tầm nền IPS
Screen Size: 14.0 inches
Status: NULL
Warranty: 24 months
CPU: Intel Core i5 12450H (Speed: 3.3 GHz, Max Speed: 4.4 GHz, Cores: 8, Threads: 12, Cache: 12.0 MB)
OS Version: Windows 11 Home
Categories: Gaming - đồ họa, AI
Colors: Xám (Quantity: 100)
GPUs: Intel UHD Graphics (Integrated, Memory: Unknown)

-----------------------------------

Laptop ID: 5
Model: Gaming Thin A15 B7UC-261VN
Brand: MSI
Price: 21990000
Discount Percent: 20.0
Battery Charger: Dung lượng 52.4Wh, sạc 120W
Created At: 2024-12-23 16:49:07.002058
Design: 359 x 254 x 21.7 mm, nặng 1.86 kg, vỏ nhựa + kim loại
Disk Capacity: 512 GB
Disk Detail: SSD 1 M.2 NVMe
Keyboard Type: Single Backlit Keyboard
Num Ratings: 0
Origin: Trung Quốc
RAM Detail: DDR5, 2 khe cắm, hỗ trợ tối đa 64GB
RAM Memory: 16 GB
Screen Detail: Anti-Glare LED-Backlit Display 1920 x 1080 pixels, tần số quét 144, tấm nền IPSHz, tầm nền IPS
Screen Size: 15.6 inches
Status: NULL
Warranty: 24 months
CPU: AMD Ryzen 5 7535HS (Speed: 3.3 GHz, Max Speed: 4.5 GHz, Cores: 6, Threads: 12, Cache: 16.0 MB)
OS Version: Windows 11 Home
Categories: Gaming - đồ họa, AI
Colors: Xám (Quantity: 100)
GPUs:
  - Unknown Unknown (Integrated, Memory: Unknown)
  - NVIDIA GeForce RTX 3050 (Dedicated, Memory: 4GB GDDR6, TOPS: 143)

-----------------------------------

Laptop ID: 6
Model: Nitro 5 Tiger Gaming AN515-58-5193
Brand: Acer
Price: 26990000
Discount Percent: 28.0
Battery Charger: Pin Lithium-ion 4 cells
Created At: 2024-12-23 17:06:22.338815
Design:  360.4 x 271.09 x 25.9 mm, nặng 2.5kg, vỏ nhựa
Disk Capacity: 512 GB
Disk Detail: SSD M.2 NVMe, 1 khe cắm HDD + 2 khe SSD, nâng cấp tối đa 1TB (HDD) và 2TB (SSD)
Keyboard Type:  English International Backlit Keyboard, RGB 4 Zone
Num Ratings: 0
Origin: Trung Quốc
RAM Detail: DDR5, 2 khe cắm, hỗ trợ tối đa 32GB
RAM Memory: 16 GB
Screen Detail:  Acer ComfyView LED-backlit  1920 x 1200 pixels, tần số quét 144Hz, tấm nền IPS
Screen Size: 15.6 inches
Status: NULL
Warranty: 24 months
CPU: Intel Core i5 12450H (Speed: 3.3 GHz, Max Speed: 4.4 GHz, Cores: 8, Threads: 12, Cache: 12.0 MB)
OS Version: Windows 11 Home
Categories: Gaming - đồ họa, AI
Colors: Đen (Quantity: 100)
GPUs:
  - NVIDIA GeForce RTX 4050 (Dedicated, Memory: 6GB GDDR6, TOPS: 194)
  - Intel Iris Xe Graphics (Integrated, Memory: Unknown)

-----------------------------------

Laptop ID: 8
Model: Inspiron N3530
Brand: Dell
Price: 19990000
Discount Percent: 10.0
Battery Charger: Pin Lithium-ion 3 cells
Created At: 2024-12-23 17:29:44.112814
Design:  358.50 x 235.56 x 18.99 mm, nặng 1.66 kg, vỏ nhựa
Disk Capacity: 512 GB
Disk Detail: SSD M.2 PCIe
Keyboard Type:  English International Non-backlit Keyboard
Num Ratings: 0
Origin: Trung Quốc
RAM Detail: DDR4, hỗ trợ tối đa 16GB
RAM Memory: 16 GB
Screen Detail:  Anti-Glare LED Backlit, 1920 x 1080 pixels, tần số quét 120 Hz, tấm nền WVA
Screen Size: 15.6 inches
Status: NULL
Warranty: 12 months
CPU: Intel Core i5 1334U (Speed: 1.3 GHz, Max Speed: 4.6 GHz, Cores: NULL, Threads: NULL, Cache: NULL MB)
OS Version: Windows 11 Home
Categories: Văn Phòng, AI
Colors: Bạc (Quantity: 100)
GPUs: Intel Iris Xe Graphics (Integrated, Memory: Unknown)

-----------------------------------

--- Câu hỏi của khách hàng ---
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
