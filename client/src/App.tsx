import {useEffect} from "react";
import {Outlet, useNavigate} from "react-router";
import {useStore} from "./store/store.ts";

function App() {
  const navigate = useNavigate();
  const {accessToken, setAccessToken} = useStore();
  
  useEffect(() => {
    const localAccessToken = localStorage.getItem("accessToken");
    
    if (!localAccessToken) {
      navigate("/join");
      return;
    }

    if (!accessToken) {
      setAccessToken(localAccessToken);
    }

    navigate("/chat-rooms");

  }, []);
  
  return (
      <Outlet/>
  );
}

export default App;
