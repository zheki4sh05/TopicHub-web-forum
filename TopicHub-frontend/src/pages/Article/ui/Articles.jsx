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
import { fetchFeed } from "../api/requests";
import { getFeed, getFeedStatus } from "../model/feedSlice";
import statusTypes from "./../../../app/util/statusTypes";
import Article from "../../../features/Article/ui/Article";
import ArticlesList from "../../../widgets/articlesList/ui/ArticlesList";

function Articles() {
  const hubs = useSelector(getHubsList);

  const dispatch = useDispatch();
  const feedStatus = useSelector(getFeedStatus);
  const feed = useSelector(getFeed);

  const [select, setSelect] = useState(0);

  const handleClick = (id) => {
    setSelect(id);
  };

  const makeRequest = (select, page) => {
    dispatch(
      fetchFeed({
        page: page,
        hub: select,
      })
    );
  };

  useEffect(() => {
    makeRequest(select, 1);
  }, []);

  const handlePageChange = (event, page) => {
    makeRequest(select, page);
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
          <Button variant="text" onClick={() => handleClick(0)}>
            <Typography
              variant="body1"
              style={{ textDecoration: select == 0 ? "none" : "underline" }}
            >
              Все
            </Typography>
          </Button>

          <Button variant="text" onClick={() => handleClick(0)}>
            <Typography
              variant="body1"
              style={{ textDecoration: select == 1 ? "none" : "underline" }}
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
