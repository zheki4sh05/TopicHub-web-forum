import { useDispatch, useSelector } from "react-redux";
import {
  createArticle,
  getSandboxList,
  getSandboxStatus,
  getSandboxWords,
  getTheme,
  isEmpty,
  resetSandBox,
} from "../../model/sandboxSlice";
import KeyWords from "./../../../../entities/article/ui/KeyWords";
import { Backdrop, Button, CircularProgress } from "@mui/material";
import { useEffect, useState } from "react";
import statusTypes from "../../../../app/util/statusTypes";
import { useNavigate } from "react-router";
import { PathConstants } from "../../../../app/pathConstants";

function SaveArticle() {
    const navigate = useNavigate();
  const dispatch = useDispatch();
  const empty = useSelector(isEmpty);
  const theme = useSelector(getTheme);
  const keyWords = useSelector(getSandboxWords);
  const list = useSelector(getSandboxList);
  const sandboxStatus = useSelector(getSandboxStatus);

  const [backDrop, setBackdrop] = useState(false);
  const handleClose = () => {
    setBackdrop(false);
  };
  const handleOpen = () => {
    setBackdrop(true);
  };

  const handleSave = () => {
   
    dispatch(
      createArticle({
        theme,
        keyWords: keyWords.map((word) => word.name),
        list,
      })
    );
  };

  useEffect(() => {
    if (sandboxStatus == statusTypes.loading) {
        handleOpen()
    } else if (sandboxStatus == statusTypes.succeeded) {
        handleClose()
        dispatch(resetSandBox())
        navigate(PathConstants.PROFILE)
    }else if (sandboxStatus == statusTypes.failed){
        handleClose()

    }
  }, [sandboxStatus]);

  return (
    <>
      <Button variant="contained" disabled={!empty} onClick={handleSave}>
        Опубликовать
      </Button>

      <Backdrop
        sx={(theme) => ({ color: "#fff", zIndex: theme.zIndex.drawer + 1 })}
        open={backDrop}
        onClick={handleClose}
      >
        <CircularProgress color="inherit" />
      </Backdrop>
    </>
  );
}

export default SaveArticle;
