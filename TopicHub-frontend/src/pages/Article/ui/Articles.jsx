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
import { Link } from "react-router";
import { useEffect, useState } from "react";
import { fetchFeed } from "../api/requests";
import { getFeed, getFeedStatus } from "../model/feedSlice";
import statusTypes from "./../../../app/util/statusTypes";
import Article from "../../../features/Article/ui/Article";

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
        type: select,
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

      <Box sx={{ margin: "20px 0 20px 0" }}>
        {feedStatus == statusTypes.loading ? (
          <Box sx={{ width: "100%" }}>
            <LinearProgress />
          </Box>
        ) : feedStatus == statusTypes.succeeded ? (
          <>
            <Box sx={{ maxWidth: "820px", margin: "0 auto" }}>
              {feed.articleDtoList.map((item, index) => (
                <Box key={index} sx={{marginBottom:"10px"}}>
                  <Article  item={item} mode={statusTypes.short} />
                </Box>
              ))}
            </Box>
          </>
        ) : (
          <Box sx={{ display: "flex", flexDirection: "row" }}>
            <Typography variant="subtitle1" gutterBottom sx={{ color: "red" }}>
              Ошибка загрузки!
            </Typography>
            <Button
              sx={{ marginLeft: "15px" }}
              variant="outlined"
              onClick={() => makeRequest(select, 1)}
            >
              Попробовать еще раз
            </Button>
          </Box>
        )}
      </Box>
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
