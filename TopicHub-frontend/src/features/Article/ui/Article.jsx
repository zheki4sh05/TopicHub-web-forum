import {
  Box,
  Button,
  Chip,
  IconButton,
  Paper,
  Typography,
} from "@mui/material";
import statusTypes from "../../../app/util/statusTypes";
import { formatDateFromTimestamp } from "../../../app/util/date";
import { useDispatch, useSelector } from "react-redux";
import { getHubs } from "../../../pages/Article/model/feedSlice";
import { Link, useLocation } from "react-router";
import { PathConstants } from "../../../app/pathConstants";
import { setArticle } from "../model/articleSlice";
import ReactionBox from "../../../shared/ReactionBox/ui/ReactionBox";
import MenuWrapper from "../../../widgets/menu/ui/MenuWrapper";
import EditIcon from "@mui/icons-material/Edit";
import DeleteIcon from "@mui/icons-material/Delete";
function Article({ item = {}, mode, edit = false, handleEdit, handleDelete }) {
  const hubs = useSelector(getHubs);
  const dispatch = useDispatch();
  const getArticlePart = (item, index) => {
    switch (item.type) {
      case "list": {
        return <></>;
      }
      case "img": {
        return (
          <Box key={index}>
            <img src={item.value} alt="Не удалось загрузить изображение!" />
            <Typography variant="body2" gutterBottom sx={{ width: "100%" }}>
              {item.value}
            </Typography>
          </Box>
        );
      }
      case "paragraph": {
        return (
          <Typography
            variant="body2"
            gutterBottom
            sx={{ width: "100%" }}
            key={index}
          >
            {item.value}
          </Typography>
        );
      }
      case "chapter": {
        return (
          <Typography
            variant="h6"
            gutterBottom
            sx={{ width: "100%" }}
            key={index}
          >
            {item.value}
          </Typography>
        );
      }
    }
  };
  const getContent = (item) => {
    return item.list.map((item, index) => <> {getArticlePart(item, index)}</>);
  };

  const getViewByMode = (type, item) => {
    switch (type) {
      case statusTypes.short: {
        return (
          <Box sx={{ maxHeight: "170px", overflow: "hidden" }}>
            {getContent(item)}
          </Box>
        );
      }
      case statusTypes.long: {
        return (
          <>
            <Box sx={{ maxHeight: "auto" }}>{getContent(item)}</Box>
          </>
        );
      }
    }
  };

  const handleActive = () => {
    dispatch(setArticle(item));
  };

  return (
    <Paper elevation={1}>
      <Box
        sx={{
          width: "100%",
          display: "flex",
          flexDirection: "column",
          padding: "15px 15px 5px 15px",
          boxSizing: "border-box",
        }}
      >
        {mode == "long" && edit ? (
          
            <Box sx={{ display: "flex", justifyContent: "space-between",marginBottom:"15px" }}>
              <IconButton onClick={handleEdit} >
                <EditIcon />
              </IconButton>

              <IconButton>
                <DeleteIcon onClick={handleDelete}/>
              </IconButton>
            </Box>
         
        ) : null}

        <Box sx={{ display: "flex", justifyContent: "space-between" }}>
          <Box
            sx={{
              display: "flex",
              justifyContent: "flex-start",
              alignItems: "center",
            }}
          >
            <img src="" alt="logo" />
            <Typography sx={{ marginLeft: "15px" }} variant="subtitle1">
              {item.userDto.login}
            </Typography>
            <Typography sx={{ marginLeft: "15px" }} variant="caption">
              {formatDateFromTimestamp(item.created)}
            </Typography>
          </Box>
          <Box>
            <Typography
              variant="body1"
              sx={{ color: "#6495ED", textDecoration: "underline" }}
            >
              {hubs.find((hub) => hub.id == item.hub).name}
            </Typography>
          </Box>
        </Box>

        <Box sx={{ display: "flex", flexDirection: "column" }}>
          <Box>
            <Typography variant="h6">{item.theme}</Typography>
          </Box>
          {getViewByMode(mode, item)}

          <Box
            sx={{
              display: "flex",
              flexDirection: "row",
              borderBottom: "1px solid #C4C4C4",
              marginTop: "15px",
            }}
          >
            {mode == "short" ? (
              <Box sx={{ height: "30px", width: "140px" }}>
                <Link to={ edit ? PathConstants.PROFILE_ARTICLE : PathConstants.VIEW}>
                  <Button
                    variant="contained"
                    size="small"
                    onClick={handleActive}
                  >
                    Читать далее
                  </Button>
                </Link>
              </Box>
            ) : null}

            <Box sx={{ paddingBottom: "10px" }}>
              {item.keyWords.map((item, index) => (
                <Chip label={item} key={index} sx={{ margin: "0 5px" }} />
              ))}
            </Box>
          </Box>
          <ReactionBox
            item={item}

            showDanger={true}
            // handleDanger={() => {}}
          
          />
        </Box>
      </Box>
    </Paper>
  );
}

export default Article;
