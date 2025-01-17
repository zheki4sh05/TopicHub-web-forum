import { Button, IconButton, Popover, Typography } from "@mui/material";
import AccountCircleIcon from "@mui/icons-material/AccountCircle";
import { useNavigate } from "react-router";
import { useEffect, useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import {
  getUser,
  isAuth,
  setActiveUser,
} from "../../../pages/Profile/model/userSlice";
import { PathConstants } from "./../../../app/pathConstants";
import { SignalCellularNull } from "@mui/icons-material";
import { useTranslation } from "react-i18next";

function UserSettings() {
  const { t } = useTranslation();
  const dispatch = useDispatch();
  const user = useSelector(getUser);
  const navigate = useNavigate();
  const [anchorEl, setAnchorEl] = useState(null);
  const auth = useSelector(isAuth);
  const handleClick = (event) => {
    if (!auth) {
      setAnchorEl(event.currentTarget);
    } else {
      dispatch(setActiveUser(user));
      navigate(PathConstants.PROFILE);
    }
  };
  const handleClose = () => {
    setAnchorEl(null);
  };
  const open = Boolean(anchorEl);
  const id = open ? "simple-popover" : undefined;
  return (
    <>
      <>
        <IconButton
          size="large"
          aria-label="account of current user"
          aria-controls="menu-appbar"
          aria-haspopup="true"
          onClick={handleClick}
          aria-describedby={id}
        >
          <AccountCircleIcon color="white" />
          <Typography variant="subtitle1" sx={{ marginLeft: "5px" }}>
            {auth ? user.login : null}
          </Typography>
        </IconButton>
        <Popover
          id={id}
          open={open}
          anchorEl={anchorEl}
          onClose={handleClose}
          anchorOrigin={{
            vertical: "bottom",
            horizontal: "left",
          }}
        >
          <Typography sx={{ p: 2 }}>{t("txt_warn_auth")}</Typography>

          <Button onClick={() => navigate(PathConstants.LOGIN)}>
            {t("btn_auth")}
          </Button>
        </Popover>
      </>
    </>
  );
}

export default UserSettings;
