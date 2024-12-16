import { Box, IconButton, Typography } from "@mui/material";
import ThumbUpIcon from "@mui/icons-material/ThumbUp";
import ThumbDownIcon from "@mui/icons-material/ThumbDown";
import InsertCommentIcon from "@mui/icons-material/InsertComment";
import DangerousIcon from "@mui/icons-material/Dangerous";
import { useDispatch, useSelector } from "react-redux";
import { getUser, isAuth } from "./../../../pages/Profile/model/userSlice";
import { makeReaction, removeReaction } from "../api/request";
import { useState } from "react";
import ThumbUpOffAltIcon from "@mui/icons-material/ThumbUpOffAlt";
import ThumbDownOffAltIcon from "@mui/icons-material/ThumbDownOffAlt";
function ReactionBox({ item, handleDanger, showDanger = false,handleComment ,showLikes=true}) {

  const dispatch = useDispatch();

  const auth = useSelector(isAuth)
  const [like, setLike] = useState(item.likeState == 1);
  const [dislike, setDislike] = useState(item.likeState == -1);
  const [likeCount, setLikeCount] = useState(item.likes);
  const [dislikeCount, setDisLikeCount] = useState(item.dislikes);

  const handleMakeReaction = (value) => {
    dispatch(
      makeReaction({
        type: "article",
        value: value,
        targetId: item.id,
      })
    );

  };

  const handleRemoveReaction = () => {
    dispatch(
      removeReaction({
        type: "article",
        targetId: item.id,
      })
    );
  };

  const handleLike = () => {
    if (!like) {
      if (dislike) {
        setDislike(false);
        setDisLikeCount(dislikeCount-1)
      }
      setLikeCount(likeCount+1)
      setLike(true);
      handleMakeReaction(1);
    } else {
      setLike(false);
      setLikeCount(likeCount-1)
      handleRemoveReaction();
    }
  };
  const handleDislike = () => {
    if (!dislike) {
      if (like) {
        setLike(false);
        setLikeCount(likeCount-1)
      }
      setDisLikeCount(dislikeCount+1)
      setDislike(true);
      handleMakeReaction(-1);
    } else {
      setDisLikeCount(dislikeCount-1)
      setDislike(false);
      handleRemoveReaction();
    }
  };

 

  return (
    <Box sx={{ display: "flex", justifyContent: "space-between" }}>
      <Box sx={{ display: "flex", justifyContent: "start", marginTop: "10px" }}>

        {
            showLikes ? 
            <>
            <IconButton onClick={handleLike}>
          <Typography variant="subtitle2" sx={{ marginRight: "5px" }}>
            {likeCount}
          </Typography>

          {like && !dislike ? <ThumbUpIcon /> : <ThumbUpOffAltIcon />}
        </IconButton>
        <IconButton onClick={handleDislike}>
          <Typography variant="subtitle2" sx={{ marginRight: "5px" }}>
            {dislikeCount}
          </Typography>
          {dislike && !like ? <ThumbDownIcon /> : <ThumbDownOffAltIcon />}
        </IconButton>
            
            </>
            :
            null

        }
        
        <IconButton onClick={handleComment}>
          <Typography variant="subtitle2" sx={{ marginRight: "5px" }}>
            0
          </Typography>
          <InsertCommentIcon />
        </IconButton>
      </Box>


      {showDanger ? 
      
      auth ?
      
      (
        <Box>
          <IconButton onClick={handleDanger}>
            <Typography variant="caption" sx={{ marginRight: "5px" }}>
              Пожаловаться
            </Typography>
            <DangerousIcon />
          </IconButton>
        </Box>
      ) : 
      <Box sx={{display:"flex",flexDirection:"column"}}>
          <IconButton disabled={true}>
            <Typography variant="caption" sx={{ marginRight: "5px" }}>
              Пожаловаться
            </Typography>
            <DangerousIcon />
          </IconButton>
          <Typography variant="caption" >Требуется авторизация</Typography>
        </Box>
     

      :
      null
    
    
    }
    </Box>
  );
}

export default ReactionBox;
