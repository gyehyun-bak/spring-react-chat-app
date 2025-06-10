import {useNavigate} from "react-router";

interface ChatRoomItemProps {
    id: number;
    name: string;
    memberCount: number;
}

export default function ChatRoomItem({ id, name, memberCount }: ChatRoomItemProps) {
    const navigate = useNavigate();

    return (
        <div
            onClick={() => navigate(`/chat-rooms/${id}`)}
            className={"flex w-full p-5 border justify-between border-neutral-200 justify-start items-start shadow-xl rounded-xl hover:cursor-pointer hover:bg-neutral-200"}>
            <span>{name}</span>
            <span className={"text-neutral-500"}>👤{memberCount}</span>
        </div>
    )
}