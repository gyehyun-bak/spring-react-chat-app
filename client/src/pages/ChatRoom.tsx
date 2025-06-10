import SystemMessageItem from "../components/SystemMessageItem.tsx";
import ChatMessageItem from "../components/ChatMessageItem.tsx";
import {useStore} from "../store/store.ts";
import {useEffect, useRef, useState} from "react";
import {MessageResponseDto} from "../types/MessageResponseDto.ts";
import {useParams} from "react-router";
import {MessageRequestDto} from "../types/MessageRequestDto.ts";
import api from "../api/api.ts";

export default function ChatRoom() {
    const {stompClient, accessToken, memberId} = useStore();
    const {chatRoomId} = useParams();

    const [joined, setJoined] = useState(false);
    const [message, setMessage] = useState("");
    const [messages, setMessages] = useState<MessageResponseDto[]>([]);
    const inputRef = useRef<HTMLInputElement | null>(null);

    useEffect(() => {
        const joinChatRoom = async () => {
            try {
                const response = await api.post(`/chat-rooms/${chatRoomId}/join`, {
                    accessToken: accessToken,
                });

                if (response.status === 200) {
                    setJoined(true);
                }
            } catch (e) {
                console.error(e);
            }
        }
        
        joinChatRoom();
    }, [stompClient, chatRoomId, accessToken]);

    useEffect(() => {
        if (!joined || !stompClient) return;

        const callback = (message: { body: string; }) => {
            if (message.body) {
                console.log("[STOMP] 메시지 수신: ", message.body);
                const newMessage: MessageResponseDto = JSON.parse(message.body);
                setMessages((prevMessages) => [...prevMessages, newMessage]);
            }
        };

        const subscription = stompClient.subscribe(`/topic/chat-rooms/${chatRoomId}`, callback);

        return () => {
            subscription.unsubscribe();
        };

    }, [chatRoomId, joined, stompClient]);

    const sendMessage = () => {
        if (
            !message.trim() ||
            !stompClient ||
            !stompClient.connected
        ) return;

        const messageDto: MessageRequestDto = {
            content: message,
        };

        stompClient.publish({
            destination: `/app/chat-rooms/${chatRoomId}`,
            body: JSON.stringify(messageDto),
        });

        setMessage("");
        inputRef.current?.focus();
    }

    const handleKeyPress = (e: React.KeyboardEvent<HTMLInputElement>) => {
        if (e.key === "Enter") {
            sendMessage();
        }
    };

    return (
        <div className="flex justify-center w-screen h-screen">
            <div className="flex flex-col max-w-screen-sm w-full h-full bg-neutral-50">
                {/* Body */}
                <div className="flex-1 overflow-auto p-4">
                    <div className="flex flex-col gap-1">
                        {messages.map((message, index) => {
                            if (message.type === "SYSTEM") {
                                return <SystemMessageItem key={index} message={message}/>;
                            }

                            return (
                                <ChatMessageItem
                                    key={index}
                                    message={message}
                                    isMine={message.memberId === memberId}
                                />
                            );
                        })}
                    </div>
                </div>

                {/* Input */}
                <div className="px-10 pb-10 pt-5 flex items-center w-full">
                    <input
                        ref={inputRef}
                        type="text"
                        className="flex-1 p-4 rounded-xl mr-2 shadow-lg"
                        placeholder="메시지를 입력하세요..."
                        value={message}
                        onChange={(e) => setMessage(e.target.value)}
                        onKeyDown={handleKeyPress}
                    />
                    <button
                        className="p-4 bg-blue-600 text-white rounded-xl shadow-lg"
                        onClick={sendMessage}
                    >
                        전송
                    </button>
                </div>
            </div>
        </div>
    )
}