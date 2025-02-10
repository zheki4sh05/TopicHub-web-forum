import {
  Box,
  LinearProgress,
  Pagination,
  Stack,
  Typography,
} from "@mui/material";
import { useDispatch, useSelector } from "react-redux";
import { getUserBookmarks, getUserStatus } from "../model/userSlice";
import ArticlesList from "./../../../widgets/articlesList/ui/ArticlesList";
import MenuWrapper from "../../../widgets/menu/ui/MenuWrapper";
import statusTypes from "../../../app/util/statusTypes";
import { fetchUserBookmarks } from "../api/requests";
import { useEffect } from "react";
import { getFeed, getFeedStatus } from "../../Article/model/feedSlice";

function UserMarks() {
  const articles = useSelector(getFeed);
  const status = useSelector(getFeedStatus);
  const dispatch = useDispatch()


  const makeRequest = (page) => {
    dispatch(fetchUserBookmarks(
            {
                page:page
            }
    ))
  };

  useEffect(() => {
    makeRequest(1);
  }, []);
  const handlePageChange = (event, page) => {
    makeRequest(page);
  };
 

  return (
    <>
      {articles.items.length > 0 ? (
        <Stack direction={"column"}>
          <ArticlesList
            status={status}
            batch={articles}
            makeRequest={makeRequest}
            edit={false}
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
                  articles.items.length != 0 ? articles.maxPage : 0
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
          <Typography>Список закладок пуст.</Typography>
        </>
      )}
    </>
  );
}

export default UserMarks;
