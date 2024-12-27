import {
  Box,
  Button,
  CircularProgress,
  IconButton,
  Stack,
  Typography,
} from "@mui/material";
import MenuWrapper from "../../../widgets/menu/ui/MenuWrapper";
import { PathConstants } from "../../../app/pathConstants";
import { Link, useNavigate } from "react-router";
import { useDispatch, useSelector } from "react-redux";
import {
  getArticle,
  getArticleStatus,
  getBookmarksStatus,
  getReactions,
  getSubscriptionStatus,
  manageBookmarkStatus,
  manageSubscriptionStatus,
} from "../../../features/Article/model/articleSlice";
import Article from "../../../features/Article/ui/Article";
import CommentsList from "../../../widgets/comments/ui/CommentsList";
import { useEffect } from "react";
import {
  addBookmark,
  checkReactions,
  removeBookmark,
  subscribe,
  unsubscribe,
} from "../api/requests";
import {
  controlUserStatus,
  deleteBookmark,
  getUser,
  getUserStatus,
  isAuth,
  pushBookmark,
} from "../../Profile/model/userSlice";
import statusTypes from "../../../app/util/statusTypes";
import BookmarkIcon from "@mui/icons-material/Bookmark";
import BookmarkAddedIcon from "@mui/icons-material/BookmarkAdded";
import Author from "../../../widgets/author/ui/Author";

function ArticleView() {
  const article = useSelector(getArticle);
  const user = useSelector(getUser);
  const status = useSelector(getArticleStatus);
  const reaction = useSelector(getReactions);
  const auth = useSelector(isAuth)
  const subscribeStatus = useSelector(getSubscriptionStatus);
  const bookmarStatus = useSelector(getBookmarksStatus);

  const dispatch = useDispatch();

  useEffect(() => {
    if(auth){
      dispatch(
        checkReactions({
          article: article.id,
          author: article.userDto.id,
          user: user.id,
        })
      );
    }
    
  }, []);


  useEffect(() => {
    if (subscribeStatus == statusTypes.succeeded) {
      dispatch(manageSubscriptionStatus(statusTypes.idle));
    }
  }, [subscribeStatus]);

  useEffect(() => {
    if (bookmarStatus == statusTypes.succeeded) {
      dispatch(manageBookmarkStatus(statusTypes.idle));
    }
  }, [bookmarStatus]);

  const handleSubscribe = (action) => {
    if (action == 1) {
      dispatch(
        subscribe({
          author: article.userDto.id,
        })
      );
    } else {
      dispatch(
        unsubscribe({
          author: article.userDto.id,
        })
      );
    }
  };

  const handleRemoveBookMark = () => {
    dispatch(
      removeBookmark({
        article: article.id,
      })
    );
    dispatch(deleteBookmark(article.id));
  };

  const handleAddBookMark = () => {
    dispatch(
      addBookmark({
        article: article.id,
      })
    );
    dispatch(pushBookmark(article));
  };

  return (
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
          <Link to={PathConstants.ARTICLE}>
            <Typography variant="body1" style={{ textDecoration: "underline" }}>
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
          <Article item={article} mode={"long"} />

          <MenuWrapper>
            <Box
              sx={{
                display: "flex",
                justifyContent: "space-between",
                alignItems: "center",
                width: "100%",
              }}
            >
              <Stack direction="row" sx={{ alignItems: "center", gap: "20px" }}>
                <Box
                  sx={{
                    display: "flex",
                    flexDirection: "row",
                    gap: "10px",
                    textDecoration: "none",
                  }}
                >
                  <Author user={article.userDto} size={50} edit={false} />
                </Box>

                {user.id != article.userDto.id ? (
                  status == statusTypes.failed ? (
                    <Button variant="outlined" color="success" disabled={true}>
                       Авторизация
                    </Button>
                  ) : status == statusTypes.loading ? (
                    <CircularProgress />
                  ) : !reaction.isSubscribe ? (
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
                      Отписаться
                    </Button>
                  )
                ) : null}
              </Stack>
              <Box
                sx={{
                  display: "flex",
                  flexDirection: "row",
                  alignItems: "center",
                }}
              >
                <Typography>Закладки</Typography>

                {reaction.isMarked ? (
                  <IconButton onClick={handleRemoveBookMark} disabled={!auth}>
                    <BookmarkAddedIcon />
                  </IconButton>
                ) : (
                  <IconButton onClick={handleAddBookMark} disabled={!auth}>
                    <BookmarkIcon />
                  </IconButton>
                )}
              </Box>
            </Box>
          </MenuWrapper>

          <MenuWrapper>
            <CommentsList article={article} />
          </MenuWrapper>
        </Box>
      </Box>
    </Box>
  );
}

export default ArticleView;
