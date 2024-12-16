import { Box, Button, Typography } from "@mui/material";
import userSlice, { getUser, isAuth } from "./../../Profile/model/userSlice";
import statusTypes from "../../../app/util/statusTypes";
import { useNavigate } from "react-router";
import { useEffect, useState } from "react";
import { PathConstants } from "../../../app/pathConstants";
import { useDispatch, useSelector } from "react-redux";
import AdminMenu from "../../../widgets/admin/ui/AdminMenu";
import MenuWrapper from "./../../../widgets/menu/ui/MenuWrapper";
import ComplaintsList from "../../../widgets/admin/ui/Article/ComplaintsList";

import { getArticleComplaints } from "../../../widgets/admin/model/adminSlice";

function ManageArticle() {
  const navigate = useNavigate();
  const complaints = useSelector(getArticleComplaints)
  const user = useSelector(getUser);
  const auth = useSelector(isAuth)
  const [choice, setCoice] = useState(1);
  const dispatch = useDispatch()
  useEffect(() => {
    if (!auth || !user.roles.includes(statusTypes.admin)) {
      navigate(PathConstants.ARTICLE);
    }
  }, []);

  const handleClick = (value) => {
    setCoice(value);
  };

  const getContentByType=(value)=>{

      switch(value){
        case 1:{
          return (
            <ComplaintsList
            
            list={complaints}

            />
          )
        }
        case 2:{
          return (
          <>
          
          </>
          )
        }
      }

  }

  return (
    <Box>
      <AdminMenu />
      <Box sx={{ maxWidth: "1000px", margin: "0 auto" }}>
        <MenuWrapper>
          <Box
            sx={{
              display: "flex",
              justifyContent: "flex-start",
              alignItems: "center",
              width: "100%",
            }}
          >
            <Button variant="text" onClick={() => handleClick(1)}>
              <Typography
                variant="body1"
                style={{ textDecoration: choice == 1 ? "none" : "underline" }}
              >
                Жалобы
              </Typography>
            </Button>

            {/* <Button variant="text" onClick={() => handleClick(2)}>
              <Typography
                variant="body1"
                style={{ textDecoration: choice == 2 ? "none" : "underline" }}
              >
                Все
              </Typography>
            </Button> */}
          </Box>
        </MenuWrapper>

        {getContentByType(choice)}

       
      </Box>
    </Box>
  );
}

export default ManageArticle;
