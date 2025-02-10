import {
  Box,
  LinearProgress,
  Pagination,
  Stack,
  Typography,
} from "@mui/material";
import MenuWrapper from "../../../widgets/menu/ui/MenuWrapper";
import { useEffect } from "react";
import { useDispatch, useSelector } from "react-redux";
import {
  getActiveUser,
  getUser,
  isAuth,
} from "../model/userSlice";
import ArticlesList from "../../../widgets/articlesList/ui/ArticlesList";
import { fetchAuthorArticles, fetchUserArticles } from "./../api/requests";
import statusTypes from "../../../app/util/statusTypes";
import { getFeed, getFeedStatus } from "../../Article/model/feedSlice";
import { useTranslation } from "react-i18next";

function UserArticles({ edit }) {
  const articles = useSelector(getFeed);
  const dispatch = useDispatch();
  const status = useSelector(getFeedStatus);
  const user = useSelector(getActiveUser);
  const currentUser = useSelector(getUser);
  const auth = useSelector(isAuth);
  const {t} = useTranslation()
  const makeRequest = (page) => {
    if (edit) {
      dispatch(
        fetchUserArticles({
          page: page,
          type: "articles",
        })
      );
    } else {
      const body = auth
        ? {
            page: page,
            type: "author",
            otherUserId: user.id,
            userId: auth ? currentUser.id : null,
          }
        : { page: page, type: "author", otherUserId: user.id };

      dispatch(fetchAuthorArticles(body));
    }
  };

  const handlePageChange = (event, page) => {
    makeRequest(page);
  };

  useEffect(() => {
    makeRequest(1);
  }, []);

  return (
    <>
      {articles.items.length > 0 ? (
        <Stack direction={"column"}>
          <ArticlesList
            status={status}
            batch={articles}
            makeRequest={makeRequest}
            edit={true}
          />

          <MenuWrapper>
            <Box
              sx={{
                width: "100%",
                display: "flex",
                justifyContent: "center",
              }}
            >
              <Pagination
                count={
                  Object.keys(articles).length != 0 ? articles.pageCount : 0
                }
                variant="outlined"
                color="primary"
                onChange={handlePageChange}
              />
            </Box>
          </MenuWrapper>
        </Stack>
      ) : status == statusTypes.loading ? (
        <LinearProgress />
      ) : (
        <>
          <Typography>
           {t('txt_warning_articles')}
          </Typography>
        </>
      )}
    </>
  );
}

export default UserArticles;
