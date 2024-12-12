import { useDispatch } from "react-redux";
import { subscribe, unsubscribe } from "../../../pages/ArticleView/api/requests";
import { Button } from "@mui/material";

function SubscribeBtn({ state,authorId, text="Отписаться" }) {
const dispatch = useDispatch()

 const handleSubscribe = (action) => {
    if (action == 1) {
      dispatch(
        subscribe({
          author: authorId,
        })
      );
    } else {
      dispatch(
        unsubscribe({
          author: authorId,
        })
      );
    }
  };

  return (
    <>
      {state ? (
        <Button
          variant="outlined"
          color="success"
          onClick={() => handleSubscribe(1)}
        >
          Подписаться
        </Button>
      ) : (
        <Button
          variant="outlined"
          color="success"
          onClick={() => handleSubscribe(-1)}
        >
          {text}
        </Button>
      )}
    </>
  );
}

export default SubscribeBtn;
