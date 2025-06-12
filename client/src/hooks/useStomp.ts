import {useCallback} from "react";
import {useStore} from "../store/store.ts";
import {SOCKET_URL} from "../config.ts";
import SockJS from "sockjs-client";
import {Client, IFrame} from "@stomp/stompjs";

interface ConnectOptions {
    accessToken: string;
    onConnect: () => void;
    onError: (e: IFrame) => void;
    onDisconnect: () => void;
}

export function useStomp() {
    const {setStompClient} = useStore();

    const connect = useCallback(
        ({
             accessToken,
             onConnect,
             onError,
             onDisconnect
         }: ConnectOptions) => {
            const socket = new SockJS(SOCKET_URL);

            const stompClient = new Client({
                webSocketFactory: () => socket,
                debug: (msg: string) => console.log("[STOMP]:", msg),
                connectHeaders: {
                    accessToken: accessToken,
                },
                onConnect: () => onConnect(),
                onStompError: (e) => {
                    console.error("[STOMP] 연결 실패: ", e);
                    onError(e);
                },
                onDisconnect: () => onDisconnect(),
                reconnectDelay: 5000,
                heartbeatIncoming: 4000,
                heartbeatOutgoing: 4000,
            });

            stompClient.activate();
            setStompClient(stompClient);
        }, [setStompClient]
    );

    return {
        connect
    };
}