import { Box, Grid2, Typography } from "@mui/material";
import SearchBox from "../../../features/Search/ui/SearchBox";
import { Link, useLocation } from "react-router";
import UserSettings from "./../../../widgets/userSettings/ui/UserSettings";
import { PathConstants } from "./../../../app/pathConstants";

function Header() {
  const location = useLocation();

  const getLinkByPath = (path) => {
    switch (path) {
      case PathConstants.ARTICLE: {
        return (
          <Link style={{ textDecoration: "none" }} to={{pathname: PathConstants.CREATE_ARTICLE}}>
            <Typography
              variant="subtitle1"
              style={{ color: "white", textDecoration: "underline" }}
            >
              Создать статью
            </Typography>
          </Link>
        );
      }
      case PathConstants.CREATE_ARTICLE: {
        return (
          <Link style={{ textDecoration: "none" }} to={{pathname: PathConstants.ARTICLE}}>
            <Typography
              variant="subtitle1"
              style={{ color: "white", textDecoration: "underline" }}
            >
              Лента
            </Typography>
          </Link>
        );
      }
    }

    return "";
  };

  return (
    <Box
      sx={{
        height: "70px",
        paddingLeft: "40px",
        paddingRight: "40px",
        backgroundColor: "#2196f3",
        display: "flex",
        flexDirection: "row",
        alignItems: "center",
      }}
    >
      <Grid2 container spacing={2} sx={{ width: "100%" }}>
        <Grid2 size={4}>
          <Box
            sx={{
              display: "flex",
              flexDirection: "row",
              height: "100%",
              alignItems: "center",
            }}
          >
            <Link style={{ textDecoration: "none" }} to={{pathname: PathConstants.ARTICLE}}>
              <Typography variant="h6" style={{ color: "white" }}>
                TopicHub
              </Typography>
            </Link>
          </Box>
        </Grid2>
        <Grid2 size={4}>
          <Box
            sx={{
              display: "flex",
              justifyContent: "center",
              width: "100%",
              height: "100%",
              alignItems: "center",
            }}
          >
            <SearchBox />
          </Box>
        </Grid2>
        <Grid2 size={4}>
          <Box
            sx={{
              display: "flex",
              justifyContent: "flex-end",
              alignItems: "center",
              height: "100%",
            }}
          >
            <Box
              sx={{
                display: "flex",
                flexDirection: "row",
                alignItems: "center",
              }}
            >
              <Box sx={{ marginRight: "20px" }}>
                {getLinkByPath(location.pathname)}
              </Box>
              <UserSettings />
            </Box>
          </Box>
        </Grid2>
      </Grid2>
    </Box>
  );
}

export default Header;
