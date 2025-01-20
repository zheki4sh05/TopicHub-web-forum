import {
  Box,
  Button,
  Pagination,
  Stack,
  Tab,
  Tabs,
  Typography,
} from "@mui/material";
import MenuWrapper from "../../../widgets/menu/ui/MenuWrapper";
import { useNavigate } from "react-router";
import { PathConstants } from "../../../app/pathConstants";
import { getActiveUser, getUser } from "../model/userSlice";
import { useSelector } from "react-redux";
import { useState } from "react";
import UserArticles from "./UserArticles";
import UserProfile from "./UserProdile";
import UserMarks from "./UserMarks";
import UserSubscribers from "./UserSubscribers";
import UserFollowers from "./UserFollowers";
import CustomTabPanel from "../../../shared/Tabs/ui/CustomTabPanel";
import { useTranslation } from "react-i18next";

function Profile() {
  const user = useSelector(getUser);
  const activeUser = useSelector(getActiveUser);
  const [value, setValue] = useState(0);
  const {t} = useTranslation()

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
      name: t('tab_name1'),
      number: 0,
      component: <UserArticles edit={user.id == activeUser.id} />,
    },
    {
      name:t('tab_name2'),
      number: 1,
      component: <UserProfile edit={user.id == activeUser.id} />,
    },
    {
      name: t('tab_name3'),
      number: 2,
      component: <UserMarks />,
    },
    {
      name: t('tab_name4'),
      number: 3,
      component: <UserFollowers edit={user.id == activeUser.id} />,
    },
    {
      name: t('tab_name5'),
      number: 4,
      component: <UserSubscribers edit={user.id == activeUser.id} />,
     
    },
  ];

  const tabslListByUser = () => {
    if (user.id == activeUser.id) {
      return tabsList;
    } else {
      return tabsList.filter((item) => item.number != 2);
    }
  };

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
          </Box>
          <Box sx={{ borderBottom: 1, borderColor: "divider" }}>
            <Tabs
              value={value}
              onChange={handleChange}
              aria-label="basic tabs example"
            >
              {tabslListByUser().map((item, index) => (
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
      {tabslListByUser().map((item, index) => (
        <CustomTabPanel key={index} value={value} index={item.number}>
          {item.component}
        </CustomTabPanel>
      ))}
    </Box>
  );
}

export default Profile;
