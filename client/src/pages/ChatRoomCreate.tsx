import {useStore} from "../store/store.ts";
import {useNavigate} from "react-router";
import {useState} from "react";
import api from "../api/api.ts";
import {ChatRoomResponseDto} from "../api/dtos/response/ChatRoomResponseDto.ts";

export default function ChatRoomCreate() {
    const {accessToken} = useStore();
    const navigate = useNavigate();

    const [name, setName] = useState("");
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState<string | null>(null);

    const handleCreateRoom = async () => {
        if (!name.trim()) {
            setError("채팅방 이름을 입력해주세요.");
            return;
        }

        setLoading(true);
        setError(null);

        try {
            const response = await api.post<ChatRoomResponseDto>(
                "http://localhost:8080/api/chat-rooms",
                {
                    name: name,
                    accessToken: accessToken,
                },
            );

            // 생성 성공 시 채팅방 목록 페이지로 이동
            navigate(`/chat-rooms/${response.data.id}`);
        } catch (e) {
            setError("채팅방 생성에 실패했습니다.");
            console.error(e);
        } finally {
            setLoading(false);
        }
    };

    return (
        <div className="flex justify-center w-screen h-screen">
            <div className="flex flex-col max-w-screen-sm w-full h-full p-20 gap-10 bg-neutral-50">
                <h1 className="text-2xl font-bold mb-6">새 채팅방 만들기</h1>
                <input
                    type="text"
                    placeholder="채팅방 이름 입력"
                    value={name}
                    onChange={(e) => setName(e.target.value)}
                    className="p-3 mb-4 border border-neutral-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-600"
                    disabled={loading}
                />

                {error && <p className="text-red-500 mb-4">{error}</p>}

                <button
                    onClick={handleCreateRoom}
                    disabled={loading}
                    className={`bg-blue-600 text-white p-3 rounded-xl shadow-xl hover:bg-blue-700 transition ${
                        loading ? "opacity-50 cursor-not-allowed" : ""
                    }`}
                >
                    {loading ? "생성 중..." : "만들기💬"}
                </button>
            </div>
        </div>
    )
}