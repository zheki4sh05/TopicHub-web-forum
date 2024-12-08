import { Box, Pagination, Stack } from "@mui/material";
import MenuWrapper from "../../../widgets/menu/ui/MenuWrapper";
import { useEffect, useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { getUser, getUserArticles, getUserStatus } from "../model/userSlice";
import ArticlesList from "../../../widgets/articlesList/ui/ArticlesList";
import { fetchUserArticles } from './../api/requests';


function UserArticles({edit}) {
    const articles = edit ?  useSelector(getUserArticles) : []
    const user = useSelector(getUser)
   
    const dispatch = useDispatch()
    const status = useSelector(getUserStatus)
   

    const makeRequest = (select, page) =>{
    dispatch(fetchUserArticles({
        source:"article",
        page:page,
        type:"",
        
    }))
    }

    const handlePageChange = (event, page) => {
        makeRequest(0,page)
      };
   

      useEffect(()=>{
        if(Object.keys(articles).length==0){
            makeRequest(0, 1)
        }
       
      },[])


    return ( <Stack direction={"column"}>


            <ArticlesList
            
            status={status}
            batch={articles}
            makeRequest={makeRequest}
            select={0}
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
            count={Object.keys(articles).length!=0 ? articles.pageCount : 0}
            variant="outlined"
            color="primary"
            onChange={handlePageChange}
          />
        </Box>
      </MenuWrapper>

    </Stack> );
}

export default UserArticles;