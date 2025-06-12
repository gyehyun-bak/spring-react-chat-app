import {useEffect} from "react";
import {Outlet, useNavigate} from "react-router";
import {useStore} from "./store/store.ts";
import {useStomp} from "./hooks/useStomp.ts";

function App() {
  const navigate = useNavigate();
  const {accessToken, stompClient} = useStore();
  const {connect} = useStomp();
  
  useEffect(() => {
    if (!accessToken) {
      navigate("/join");
      return;
    }
    
    if (stompClient && stompClient.connected) {
      return;
    }

    connect({
      accessToken: accessToken,
      onConnect: () => {},
      onError: () => {
        navigate("/join");
      },
      onDisconnect: () => {
        navigate("/join");
      }
    })

  }, [accessToken, navigate, stompClient?.connected]);
  
  return (
      <Outlet/>
  );
}

export default App;
