import { MessageResponseDto } from "../types/MessageResponseDto";

interface ChatMessageItemProps {
  message: MessageResponseDto;
  isMine: boolean;
}

export default function ChatMessageItem({
  message,
  isMine,
}: ChatMessageItemProps) {
  return (
    <div
      className={`flex flex-col gap-1 ${isMine ? "items-end" : "items-start"}`}
    >
      <span className="text-sm text-neutral-400">{message.nickname}</span>
      <div
        className={`px-4 py-3 my-1 rounded-xl w-fit shadow-md ${
          isMine ? "bg-blue-600 text-white self-end" : "bg-white self-start"
        }`}
      >
        {message.content}
      </div>
    </div>
  );
}
