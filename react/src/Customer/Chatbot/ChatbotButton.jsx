import React from 'react';
import Fab from '@mui/material/Fab';
import ChatIcon from '@mui/icons-material/Chat'; // Hoặc một icon phù hợp khác

const ChatbotButton = ({ onClick }) => {
  return (
    <Fab
      color="primary"
      aria-label="chat"
      onClick={onClick}
      sx={{
        position: 'fixed',
        bottom: (theme) => theme.spacing(4), // Cách dưới 4 đơn vị spacing
        right: (theme) => theme.spacing(4),  // Cách phải 4 đơn vị spacing
        zIndex: 1000 // Đảm bảo nút nổi trên các thành phần khác
      }}
    >
      <ChatIcon />
    </Fab>
  );
};

export default ChatbotButton;