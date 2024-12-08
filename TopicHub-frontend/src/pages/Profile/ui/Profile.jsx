import { Box, Button, Pagination, Stack, Tab, Tabs, Typography } from "@mui/material";
import MenuWrapper from "../../../widgets/menu/ui/MenuWrapper";
import { useNavigate } from "react-router";
import { PathConstants } from "../../../app/pathConstants";
import { getUser } from "../model/userSlice";
import { useSelector } from "react-redux";
import { useState } from "react";
import UserArticles from "./UserArticles";
import UserProfile from "./UserProdile";
import UserMarks from "./UserMarks";
import UserSubscribers from "./UserSubscribers";
import UserFollowers from "./UserFollowers";
import CustomTabPanel from "../../../shared/Tabs/ui/CustomTabPanel";

function Profile() {
  const user = useSelector(getUser);
  const [value, setValue] = useState(0);

  const handleChange = (event, newValue) => {
    setValue(newValue);
  };

  function a11yProps(index) {
    return {
      id: `simple-tab-${index}`,
      "aria-controls": `simple-tabpanel-${index}`,
    };
  }



  const tabsList = [
    {
      name: "публикации",
      number: 0,
      component: <UserArticles edit={true}/>
    },
    {
      name: "Профиль",
      number: 1,
      component: <UserProfile edit={true}/>
    },
    {
      name: "закладки",
      number: 2,
      component: <UserMarks/>
    },
    {
      name: "подписчики",
      number: 3,
      component: <UserSubscribers edit={true}/>
    },
    {
      name: "подписки",
      number: 4,
      component: <UserFollowers edit={true}/>
    },
  ];

  return (
    <Box
      sx={{
        display: "flex",
        flexDirection: "column",
        maxWidth: "820px",
        margin: "0 auto",
      }}
    >
      <MenuWrapper>
        <Stack direction={"column"}>
          <Box
            sx={{
              display: "flex",
              justifyContent: "flex-start",
              alignItems: "center",
              width: "100%",
            }}
          >
            <Stack direction={"row"} spacing={2}>
              <img alt="logo" />
              <Typography variant="subtitle1">{user.login}</Typography>
            </Stack>
          </Box>
          <Box sx={{ borderBottom: 1, borderColor: "divider" }}>
            <Tabs
              value={value}
              onChange={handleChange}
              aria-label="basic tabs example"
            >
              {tabsList.map((item, index) => (
                <Tab
                  key={index}
                  label={item.name}
                  {...a11yProps(item.number)}
                />
              ))}
            </Tabs>
          </Box>
        </Stack>
      </MenuWrapper>
      {/* <CustomTabPanel value={value} index={0}>
            Item One
     </CustomTabPanel> */}
     {
        tabsList.map((item,index)=>(
            <CustomTabPanel key={index} value={value} index={0}>
                {item.component}
            </CustomTabPanel>
        ))

     }
   

      
    </Box>
  );
}

export default Profile;
