import { Box, createTheme } from "@mui/material";
import MainBody from "../../mainBody/ui/MainBody";
import { Outlet } from "react-router-dom";
import { ThemeProvider } from "@emotion/react";
import Header from "../../../processes/header/ui/Header";
import LoadingUserData from "../../../processes/userData/ui/LoadingUserData";


const theme = createTheme();
function Layout() {



    return (
      <ThemeProvider theme={theme}>
        <LoadingUserData/>
        <Box sx={{height:"100vh", width:"100%",bgcolor:"#F0F8FF", boxSizing:"border-box", overflow:"hidden", overflowY:"scroll"}}>                         
                <Box
                  sx={{
                    display: "flex",
                    flexDirection: "column",
                    flex:1,
                    height:"100%",
                    padding:0,
                    marginn:0,
                    boxSizing:"border-box",
                   
                   
                  }}
                >
                  <Header/>
                    <MainBody>
                    <Outlet />
                  </MainBody>
                </Box>
        </Box>
      </ThemeProvider>
    );
  }
  

export default Layout;