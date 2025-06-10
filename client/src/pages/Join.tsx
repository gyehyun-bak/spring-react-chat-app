import {MAX_NICKNAME_LENGTH} from "../config.ts";
import {useState} from "react";

export default function Home() {
    const [nickname, setNickname] = useState(""); // 웹사이트 접속시 입력받을 세션 닉네임

    const enterNickname = () => {
        if (!nickname.trim()) {
            setNickname("익명");
        }
    };

    return (
        <div className="flex justify-center w-screen h-screen">
            <div className="flex flex-col max-w-screen-sm w-full h-full bg-neutral-50 justify-center px-20 gap-5">
                <h1 className="text-2xl font-bold">닉네임을 입력해주세요😀</h1>
                <input
                    type="text"
                    placeholder="익명"
                    value={nickname}
                    maxLength={MAX_NICKNAME_LENGTH}
                    onChange={(e) => {
                        setNickname(e.target.value);
                    }}
                    className="p-4 rounded-xl shadow-xl"
                />
                <div className="text-sm text-gray-400 flex justify-end">
                    {nickname.length} / {MAX_NICKNAME_LENGTH}
                </div>
                <button
                    onClick={enterNickname}
                    className="bg-blue-600 text-white p-2 rounded-xl shadow-xl"
                >
                    입장
                </button>
            </div>
        </div>
    );
}