import { Box, Grid2, Typography } from "@mui/material";
import SearchBox from "../../../features/Search/ui/SearchBox";
import { Link, useLocation } from "react-router";
import UserSettings from "./../../../widgets/userSettings/ui/UserSettings";
import { PathConstants } from "./../../../app/pathConstants";
import { useSelector } from "react-redux";
import { getUser, isAuth } from "../../../pages/Profile/model/userSlice";
import statusTypes from "../../../app/util/statusTypes";
import {memo} from 'react'
import LanguageSelect from "../../../features/Language/ui/LanguageSelect";
import { useTranslation } from "react-i18next";
import LogoutBtn from "../../../features/Logout/ui/LogoutBtn";
const Header  = memo(function Header() {
  const location = useLocation();
  const {t} = useTranslation()

  const user = useSelector(getUser);

  const auth = useSelector(isAuth)

  const showSearchBox=(path)=>{

    const names=[PathConstants.ARTICLE, PathConstants.PROFILE, PathConstants.SEARCH, PathConstants.HOME]

    if(names.includes(path)){
      return  <SearchBox />
    }else{
      return <></>
    }
  }

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
            <Link
              style={{ textDecoration: "none" }}
              to={{ pathname: PathConstants.ARTICLE }}
            >
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

            {showSearchBox(location.pathname)}
            
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
              {auth && user.roles.includes(statusTypes.admin) ? (
                <Box sx={{ marginRight: "15px" }}>
                
                    <a href={PathConstants.ADMIN_PANEL} 
                                
                    >
                      <Typography style={{ color: "white", textDecoration: "underline" }} >
                      {t('link_admin')}
                      </Typography>
                    
                    </a>
               

                </Box>
              ) : null}

              <Box sx={{display:"flex",flexDirection:"row",gap:"15px", marginRight: "20px" }}>
      
              <Link
            style={{ textDecoration: "none" }}
            to={{ pathname: PathConstants.CREATE_ARTICLE }}
          >
            <Typography
              variant="subtitle1"
              style={{ color: "white", textDecoration: "underline" }}
            >
              {t('link_create_theme')}
            </Typography>
          </Link>
                
                <Link
            style={{ textDecoration: "none" }}
            to={{ pathname: PathConstants.ARTICLE }}
          >
            <Typography
              variant="subtitle1"
              style={{ color: "white", textDecoration: "underline" }}
            >
               {t('link_feed')}
            </Typography>
          </Link>
              </Box>
              <UserSettings />
              <LanguageSelect/>
              <Box sx={{marginLeft:"15px"}}>
              <LogoutBtn/>
              </Box> 
            </Box>
          </Box>
        </Grid2>
      </Grid2>
    </Box>
  );
});

export default Header;
