import {
  Box,
  Button,
  LinearProgress,
  Pagination,
  Typography,
} from "@mui/material";
import MenuWrapper from "../../../widgets/menu/ui/MenuWrapper";
import { useDispatch, useSelector } from "react-redux";
import { getHubsList } from "../../../entities/hubs/model/hubsSlice";

import { useEffect, useState } from "react";
import { fetchFeed, fetchHubs } from "../api/requests";
import { getFeed, getFeedStatus } from "../model/feedSlice";
import ArticlesList from "../../../widgets/articlesList/ui/ArticlesList";
import { getUser, isAuth } from "../../Profile/model/userSlice";


function Articles() {
  const hubs = useSelector(getHubsList);
  const user = useSelector(getUser)
  const auth = useSelector(isAuth)
  const dispatch = useDispatch();
  const feedStatus = useSelector(getFeedStatus);
  const feed = useSelector(getFeed);


  const [select, setSelect] = useState(0);

  const handleClick = (id) => {
    setSelect(id);
  };

  useEffect(()=>{
    makeRequest(select, 1);
  },[select])

  const getRequestBody=(select, page)=>{
    if(auth){
      return {
        type: "articles",
        page: page,
        hub: select,
        userId:user.id
      }
    }else{
      return {
        type: "articles",
        page: page,
        hub: select
      }  
    }
  }

  const makeRequest = (select, page) => {
    dispatch(
      fetchFeed(getRequestBody(select, page))
    );
  };

  useEffect(() => {
    makeRequest(select, 1);
  }, []);

  const handlePageChange = (event, page) => {
    
    makeRequest(select, page);
  };

    useEffect(()=>{
  
      if(hubs.length==0){
        dispatch(fetchHubs())
      }
       
    },[])

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
          <Button variant="text" onClick={() => handleClick(0)}>
            <Typography
              variant="body1"
              style={{ textDecoration: select == 0 ? "none" : "underline" }}
            >
              Все
            </Typography>
          </Button>

          <Button variant="text" onClick={() => handleClick(-1)}>
            <Typography
              variant="body1"
              style={{ textDecoration: select == -1 ? "none" : "underline" }}
            >
              Моя лента
            </Typography>
          </Button>

          {hubs.map((item, index) => (
            <Button
              variant="text"
              key={index}
              onClick={() => handleClick(item.id)}
            >
              <Typography
                variant="body2"
                style={{
                  textDecoration: select == item.id ? "none" : "underline",
                }}
              >
                {item.name}
              </Typography>
            </Button>
          ))}
        </Box>
      </MenuWrapper>

      <ArticlesList
      
      status={feedStatus}
      batch={feed}
      makeRequest={makeRequest}
      select={select}

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
            count={feed.pageCount + 1}
            variant="outlined"
            color="primary"
            onChange={handlePageChange}
          />
        </Box>
      </MenuWrapper>
    </Box>
  );
}

export default Articles;
