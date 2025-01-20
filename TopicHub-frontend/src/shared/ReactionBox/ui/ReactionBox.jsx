import { Box, IconButton, Typography } from "@mui/material";
import ThumbUpIcon from "@mui/icons-material/ThumbUp";
import ThumbDownIcon from "@mui/icons-material/ThumbDown";
import InsertCommentIcon from "@mui/icons-material/InsertComment";
import DangerousIcon from "@mui/icons-material/Dangerous";
import { useDispatch, useSelector } from "react-redux";
import {isAuth } from "./../../../pages/Profile/model/userSlice";
import { makeReaction, removeReaction } from "../api/request";
import { useState } from "react";
import ThumbUpOffAltIcon from "@mui/icons-material/ThumbUpOffAlt";
import ThumbDownOffAltIcon from "@mui/icons-material/ThumbDownOffAlt";
import { useTranslation } from "react-i18next";
function ReactionBox({
  item,
  handleDanger,
  showDanger = false,
  handleComment,
  showLikes = true,
}) {
  const dispatch = useDispatch();
  const { t } = useTranslation();
  const auth = useSelector(isAuth);
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
        setDisLikeCount(dislikeCount - 1);
      }
      setLikeCount(likeCount + 1);
      setLike(true);
      handleMakeReaction(1);
    } else {
      setLike(false);
      setLikeCount(likeCount - 1);
      handleRemoveReaction();
    }
  };
  const handleDislike = () => {
    if (!dislike) {
      if (like) {
        setLike(false);
        setLikeCount(likeCount - 1);
      }
      setDisLikeCount(dislikeCount + 1);
      setDislike(true);
      handleMakeReaction(-1);
    } else {
      setDisLikeCount(dislikeCount - 1);
      setDislike(false);
      handleRemoveReaction();
    }
  };

  return (
    <Box sx={{ display: "flex", justifyContent: "space-between" }}>
      <Box sx={{ display: "flex", justifyContent: "start", marginTop: "10px" }}>
        {showLikes ? (
          <>
            <IconButton onClick={handleLike} disabled={!auth}>
              <Typography variant="subtitle2" sx={{ marginRight: "5px" }}>
                {likeCount}
              </Typography>

              {like && !dislike ? <ThumbUpIcon /> : <ThumbUpOffAltIcon />}
            </IconButton>
            <IconButton onClick={handleDislike} disabled={!auth}>
              <Typography variant="subtitle2" sx={{ marginRight: "5px" }}>
                {dislikeCount}
              </Typography>
              {dislike && !like ? <ThumbDownIcon /> : <ThumbDownOffAltIcon />}
            </IconButton>
          </>
        ) : null}

        <IconButton onClick={handleComment} disabled={!auth}>
          <Typography variant="subtitle2" sx={{ marginRight: "5px" }}>
            {item.commentsCount}
          </Typography>
          <InsertCommentIcon />
        </IconButton>
      </Box>

      {showDanger ? (
        <Box>
          <IconButton onClick={handleDanger} disabled={!auth}>
            <Typography variant="caption" sx={{ marginRight: "5px" }}>
              {t("btn_complaint")}
            </Typography>
            <DangerousIcon />
          </IconButton>
          {!auth ? (
            <Typography variant="caption">{t("txt_warn_auth3")}</Typography>
          ) : null}
        </Box>
      ) : null}
    </Box>
  );
}

export default ReactionBox;
