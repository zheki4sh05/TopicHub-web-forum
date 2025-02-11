import {
  Box,
  Button,
  IconButton,
  LinearProgress,
  Stack,
  TextField,
  Typography,
} from "@mui/material";
import Comment from "../../../features/Comment/ui/Comment";
import { useEffect, useState } from "react";
import MultilineInput from "../../../shared/Input/ui/MultilineInput";
import { createComment, fetchComments } from "../api/requests";
import { useDispatch, useSelector } from "react-redux";
import { getComments, getStatusComments } from "../model/commentSlice";
import statusTypes from "../../../app/util/statusTypes";
import CachedIcon from '@mui/icons-material/Cached';
import { isAuth } from "../../../pages/Profile/model/userSlice";
import { PathConstants } from "../../../app/pathConstants";
import { Link } from "react-router";
function CommentsList({ article }) {
  const [comment, setComment] = useState({ value: "" });
  const [create, setCreate] = useState(false);
  const [parentId,setParentId] = useState(null)
  const auth = useSelector(isAuth)
  const makeRequest=()=>{
    dispatch(fetchComments(

      {
        articleId:article.id
      }

    ))
  }

  useEffect(()=>{
    makeRequest()
  },[])

  const comments = useSelector(getComments);
  const commentsStatus = useSelector(getStatusComments);

  const dispatch = useDispatch();

  const addComment = () => {
    setCreate(true);
  };
  const resetComment = () => {
    
    setCreate(false);
  };

  const handleClickComment = (id) => {
    setParentId(id)
    setCreate(true);
  };

  const handleCreateComment = (value) => {
    dispatch(
      createComment({
        value: value,
        articleId: article.id,
        parentId: parentId,
      })
    );
    setCreate(false)
    setParentId(null)
  };

  const handleReload=()=>{
    makeRequest()
  }

  return (
    <>
      {commentsStatus == statusTypes.succeeded || comments.length>0? (
        <Box sx={{ display: "flex", flexDirection: "column", width: "100%" }}>
          <Stack direction={"row"} sx={{ alignItems: "center" }}>
            <Box
              sx={{
                display: "flex",
                flexDirection: "row",
                padding: "0 0 12px 0",
                alignItems: "center",
                flex: 1,
              }}
            >
              <Typography variant="h6">Комментарии</Typography>
              <Typography variant="body2" sx={{ marginLeft: "20px" }}>
                Всего: {comments.length}
              </Typography>
            </Box>

            {create ? (
              <Button
                variant="outlined"
                size="small"
                color="secondary"
                sx={{ margin: "0 0 0 auto" }}
                onClick={resetComment}
              >
                Отменить
              </Button>
            ) : auth ? (
              <Button
                variant="outlined"
                size="small"
                color="secondary"
                sx={{ margin: "0 0 0 auto" }}
                onClick={addComment}
              >
                Написать
              </Button>
            )
            :(

              <Link to={{ pathname: PathConstants.LOGIN }} >
              <Typography>Авторизация</Typography>
              </Link>
            )
          

          
          }
          </Stack>

          {create ? (
            <MultilineInput handleSubmit={handleCreateComment} />
          ) : null}

          <Box
            sx={{ display: "flex", flexDirection: "column", marginTop: "20px" }}
          >
            {comments.map((item, index) => (
              <Comment
                key={index}
                item={item}
                handleComment={handleClickComment}
                level={0}
              />
            ))}
          </Box>
          <Box sx={{width:"100%"}} >
      <IconButton sx={{margin:"5px auto 0 auto"}} onClick={handleReload}>
        <CachedIcon  />
      </IconButton>
    </Box>
        </Box>
      ) : commentsStatus == statusTypes.loading ? (
        <LinearProgress />
      ) : 
      <>
      <Box
      sx={{
        display: "flex",
        flexDirection: "row",
        padding: "0 0 12px 0",
        alignItems: "center",
        flex: 1,
      }}
    >
      <Typography variant="h6">Комментарии</Typography>
      <Typography variant="body2" sx={{ marginLeft: "20px" }}>
        Не удалось загрузить
      </Typography>
    </Box>
  
       </>
      }
    </>
  );
}

export default CommentsList;
