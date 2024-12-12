import { Box, LinearProgress, Pagination, Stack, Typography } from "@mui/material";
import MenuWrapper from "../../../widgets/menu/ui/MenuWrapper";
import { useEffect, useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import {
  controlUserStatus,
  getActiveUser,
  getUser,
  getUserArticles,
  getUserStatus,
  isAuth,
} from "../model/userSlice";
import ArticlesList from "../../../widgets/articlesList/ui/ArticlesList";
import { fetchAuthorArticles, fetchUserArticles } from "./../api/requests";
import statusTypes from "../../../app/util/statusTypes";

function UserArticles({ edit }) {
  const articles = useSelector(getUserArticles)
  const dispatch = useDispatch();
  const status = useSelector(getUserStatus);
  const user = useSelector(getActiveUser);
  const currentUser = useSelector(getUser)
  const auth = useSelector(isAuth)

  const makeRequest = (page) => {

    if(edit){
      dispatch(
        fetchUserArticles({
          page: page,
          type: "articles",
        })
      );
    }else{

      const body = auth ? {page: page,
  type: "author",
        otherUserId:user.id,
        userId:auth ? currentUser.id : null
      } :
      {page: page,
        type: "author",
              otherUserId:user.id,
           
            }


      dispatch(
        fetchAuthorArticles(body)
      );
    }

   
  };

  const handlePageChange = (event, page) => {
    makeRequest(page);
  };

  useEffect(() => {
    if (edit && articles.articleDtoList.length == 0) {
      makeRequest(1);
    }else if(!edit){
      makeRequest(1);
    }
  }, []);

  useEffect(() => {
    if (status == statusTypes.succeeded) {
      dispatch(controlUserStatus(statusTypes.idle));
    }
  }, [status]);

  return (
    <>

    
      {articles.articleDtoList.length > 0 ? (
        <Stack direction={"column"}>
          <ArticlesList
            status={status}
            batch={articles}
            makeRequest={makeRequest}
            select={0}
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
      ) : status==statusTypes.loading ? (
        
          <LinearProgress/>
          
       
      ):

     <>
          <Typography>
            У Вас нет публикаий. Перейдите в раздел создания темы
          </Typography>
        </>
    
    }
    </>

  );
}

export default UserArticles;
