import { MessageResponseDto } from "../types/MessageResponseDto";

interface SystemMessageItemProps {
  message: MessageResponseDto;
}

export default function SystemMessageItem({ message }: SystemMessageItemProps) {
  return (
    <div className="flex justify-center my-3">
      <div className="px-4 py-2 rounded-full bg-neutral-300 text-white text-sm text-center">
        {message.content}
      </div>
    </div>
  );
}
