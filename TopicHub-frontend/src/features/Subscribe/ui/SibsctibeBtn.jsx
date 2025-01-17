import { useDispatch } from "react-redux";
import { subscribe, unsubscribe } from "../../../pages/ArticleView/api/requests";
import { Button } from "@mui/material";
import { useTranslation } from 'react-i18next';

function SubscribeBtn({ state,authorId, text="Отписаться" }) {
const {t} = useTranslation()  
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
          {t('btn_subscribe')}
        </Button>
      ) : (
        <Button
          variant="outlined"
          color="success"
          onClick={() => handleSubscribe(-1)}
        >
           {t('btn_unsubscribe')}
        </Button>
      )}
    </>
  );
}

export default SubscribeBtn;
