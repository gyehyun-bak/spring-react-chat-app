import { useEffect, useRef, useState } from "react";
import { Client } from "@stomp/stompjs";
import SockJS from "sockjs-client";

// 메시지 객체
interface MessageRequestDto {
  content: string;
}

interface MessageResonseDto {
  content: string;
}

// 서버 웹소켓 엔드포인트트
const SOCKET_URL = "http://localhost:8080/ws";

function App() {
  const [message, setMessage] = useState("");
  const [messages, setMessages] = useState<MessageResonseDto[]>([]);
  const stompClientRef = useRef<Client | null>(null);
  const inputRef = useRef<HTMLInputElement | null>(null);

  useEffect(() => {
    const socket = new SockJS(SOCKET_URL);
    const stompClient = new Client({
      webSocketFactory: () => socket as any,
      debug: (msg: string) => console.log("[STOMP]:", msg),
      onConnect: () => {
        console.log("[STOMP] 연결 성공: ", stompClient);
        // 채팅 토픽 구독
        const callback = (message: any) => {
          if (message.body) {
            console.log("[STOMP] 메시지 수신: ", message.body);
            const newMessage: MessageResonseDto = JSON.parse(message.body);
            setMessages((prevMessages) => [...prevMessages, newMessage]);
          }
        };

        stompClient.subscribe("/topic/chat", callback);
      },
      onStompError: (e) => {
        console.error("[STOMP] 연결 실패: ", e);
        stompClient.deactivate();
      },
      onDisconnect: () => console.log("STOMP 연결 해제"),
      reconnectDelay: 5000,
      heartbeatIncoming: 4000,
      heartbeatOutgoing: 4000,
    });

    stompClient.activate();
    stompClientRef.current = stompClient;

    return () => {
      stompClient.deactivate();
    };
  }, []);

  const sendMessage = () => {
    if (
      !message.trim() ||
      !stompClientRef.current ||
      !stompClientRef.current.connected
    )
      return;

    const messageDto: MessageRequestDto = {
      content: message,
    };

    stompClientRef.current.publish({
      destination: "/app/chat",
      body: JSON.stringify(messageDto),
    });

    setMessage("");
    inputRef.current?.focus();
  };

  const handleKeyPress = (e: React.KeyboardEvent<HTMLInputElement>) => {
    if (e.key === "Enter") {
      sendMessage();
    }
  };

  return (
    <div className="flex justify-center w-screen h-screen">
      <div className="flex flex-col max-w-screen-sm w-full h-full bg-neutral-100">
        {/* Header */}
        <div className="p-4 font-bold text-xl bg-neutral-200">
          Simple Chat Example
        </div>

        {/* Body */}
        <div className="flex-1 overflow-auto p-4">
          <div className="flex flex-col gap-1">
            {messages.map((message, index) => (
              <div
                key={index}
                className="p-2 my-1 rounded-lg w-fit bg-white shadow-md"
              >
                {message.content}
              </div>
            ))}
          </div>
        </div>

        {/* Input */}
        <div className="p-4 bg-neutral-200 flex items-center w-full">
          <input
            ref={inputRef}
            type="text"
            className="flex-1 p-2 border rounded mr-2"
            placeholder="메시지를 입력하세요..."
            value={message}
            onChange={(e) => setMessage(e.target.value)}
            onKeyDown={handleKeyPress}
          />
          <button
            className="p-2 bg-blue-500 text-white rounded"
            onClick={sendMessage}
          >
            전송
          </button>
        </div>
      </div>
    </div>
  );
}

export default App;
