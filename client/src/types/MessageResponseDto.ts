import { MessageType } from "./MessageType";

export interface MessageResponseDto {
  type: MessageType;
  content: string;
  sessionId: string;
  nickname: string;
}
