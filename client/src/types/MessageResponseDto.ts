import { MessageType } from "./MessageType";

export interface MessageResponseDto {
  type: MessageType;
  content: string;
  memberId: number;
  nickname: string;
}
