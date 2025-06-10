import {createRoot} from 'react-dom/client'
import './index.css'
import {BrowserRouter, Route, Routes} from "react-router";
import Join from "./pages/Join.tsx";
import ChatRooms from "./pages/ChatRooms.tsx";
import ChatRoomCreate from "./pages/ChatRoomCreate.tsx";
import ChatRoom from "./pages/ChatRoom.tsx";
import App from "./App.tsx";

createRoot(document.getElementById('root')!).render(
    <BrowserRouter>
        <Routes>
            <Route path={"/"} element={<App />}>
                <Route path={"chat-rooms"} element={<ChatRooms/>}/>
                <Route path={"chat-rooms/create"} element={<ChatRoomCreate/>}/>
                <Route path={"chat-rooms/:chatRoomId"} element={<ChatRoom/>}/>
            </Route>
            <Route path={"/join"} element={<Join/>}/>
        </Routes>
    </BrowserRouter>
)
