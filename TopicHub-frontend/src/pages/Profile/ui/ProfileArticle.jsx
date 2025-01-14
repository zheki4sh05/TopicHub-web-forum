import { Box, Button, Typography } from "@mui/material";
import MenuWrapper from "../../../widgets/menu/ui/MenuWrapper";
import { Link, useNavigate } from "react-router";
import { PathConstants } from "../../../app/pathConstants";

import { getArticle } from "../../../features/Article/model/articleSlice";
import { useDispatch, useSelector } from "react-redux";
import CommentsList from "../../../widgets/comments/ui/CommentsList";
import Article from "./../../../features/Article/ui/Article";
import ConfirmModal from "../../../shared/ConfirmModal/ui/ConfirmModal";
import { useEffect, useState } from "react";
import { delUserArticle } from "../api/requests";
import { controlUserStatus, getUserStatus } from "../model/userSlice";
import statusTypes from "../../../app/util/statusTypes";

function ProfileArticle() {
  const navigate = useNavigate();
  const article = useSelector(getArticle);
  const [open, setOpen] = useState(false);
  const dispatch = useDispatch();

  const handleDelete = () => {
    setOpen(true);
  };

  const handlerAgreehandle = (id) => {
    dispatch(
      delUserArticle({
        id: article.id,
        type: "article",
      })
    );
    setOpen(false);
    navigate(PathConstants.PROFILE);
  };

  const handlerDisagree = () => {
    setOpen(false);
  };

  return (
    <>
      <Box
        sx={{
          display: "flex",
          flexDirection: "column",
          maxWidth: "1000px",
          margin: "0 auto",
        }}
      >
        <MenuWrapper>
          <Box
            sx={{
              display: "flex",
              justifyContent: "flex-start",
              alignItems: "center",
              width: "100%",
            }}
          >
            <Link to={PathConstants.PROFILE}>
              <Typography
                variant="body1"
                style={{ textDecoration: "underline" }}
              >
                Назад
              </Typography>
            </Link>
          </Box>
        </MenuWrapper>

        <Box sx={{ margin: "20px 0 20px 0" }}>
          <Box
            sx={{
              maxWidth: "820px",
              display: "flex",
              flexDirection: "column",
              margin: "0 auto",
              gap: "10px",
            }}
          >
            <Article
              item={article}
              mode={"long"}
              edit={true}
              
              handleDelete={handleDelete}
            />

            <MenuWrapper>
              <CommentsList article={article} />
            </MenuWrapper>
          </Box>
        </Box>
      </Box>

      <ConfirmModal
        show={open}
        title={"Удаление статьи"}
        body={"Вы действительно хотите удалить статью?"}
        handlerAgree={handlerAgreehandle}
        handlerDisagree={handlerDisagree}
        data={article}
      />
    </>
  );
}

export default ProfileArticle;
