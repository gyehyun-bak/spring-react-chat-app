import {create} from "zustand/react";
import {Client} from "@stomp/stompjs";

interface StoreState {
    accessToken: string | null;
    setAccessToken: (accessToken: string) => void;
    stompClient: Client | null;
    setStompClient: (stompClient: Client) => void;
    memberId: number | null;
    setMemberId: (memberId: number) => void;
}

export const useStore = create<StoreState>((set) => ({
    accessToken: null,
    setAccessToken: (accessToken) => set({accessToken}),
    stompClient: null,
    setStompClient: (stompClient) => set({stompClient}),
    memberId: null,
    setMemberId: (memberId) => set({memberId}),
}))