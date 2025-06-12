import {useStore} from "../store/store.ts";
import {useNavigate} from "react-router";
import {useEffect, useState} from "react";
import api from "../api/api.ts";
import {ChatRoomsResponseDto} from "../api/dtos/response/ChatRoomsResponseDto.ts";
import {ChatRoomResponseDto} from "../api/dtos/response/ChatRoomResponseDto.ts";
import ChatRoomItem from "../components/ChatRoomItem.tsx";

export default function ChatRooms() {
    const [chatRooms, setChatRooms] = useState<ChatRoomResponseDto[]>();
    const {accessToken} = useStore()
    const navigate = useNavigate();

    useEffect(() => {
        if (!accessToken) return;
        fetchChatRooms();
    }, [accessToken]);

    const fetchChatRooms = async () => {
        try {
            const response = await api.get<ChatRoomsResponseDto>("/chat-rooms");
            setChatRooms(response.data.chatRooms);
        } catch (e) {
            console.error(e);
        }
    }

    return (
        <div className="flex justify-center w-screen h-screen">
            <div className="flex flex-col max-w-screen-sm w-full h-full bg-neutral-50">
                <div className="flex w-full justify-between p-10 items-center">
                    <h1 className="text-2xl font-bold">채팅방</h1>
                    <button
                        onClick={() => navigate("/chat-rooms/create")}
                        className="bg-blue-600 text-white p-3 px-4 rounded-xl shadow-xl">
                        만들기💬
                    </button>
                </div>
                <div className="flex flex-col gap-5 p-10">
                    {chatRooms && chatRooms.length > 0 ? (
                        chatRooms.map((room) => (
                            <ChatRoomItem
                                key={room.id}
                                id={room.id}
                                name={room.name}
                                memberCount={room.memberCount}
                            />
                        ))
                    ) : (
                        <div className="flex justify-center items-center h-full text-neutral-500">아직 채팅방이 없습니다...🦗</div>
                    )}
                </div>
            </div>
        </div>
    )
}