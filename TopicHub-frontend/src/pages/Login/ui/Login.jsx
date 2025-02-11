import MenuWrapper from "../../../widgets/menu/ui/MenuWrapper";
import { useNavigate } from "react-router";
import { useDispatch, useSelector } from "react-redux";
import {
  clearUserError,
  controlUserStatus,
  getUser,
  getUserError,
  getUserStatus,
  isAuth,
  setActiveUser,
} from "../../Profile/model/userSlice";
import { useEffect, useState } from "react";
import {
  Alert,
  Box,
  LinearProgress,
  Snackbar,
  Typography,
} from "@mui/material";
import Signup from "./Signup";
import Signin from "./SignIn";
import statusTypes from "./../../../app/util/statusTypes";
import { PathConstants } from "./../../../app/pathConstants";
import { useTranslation } from "react-i18next";

function Login() {
  const dispatch = useDispatch();
  const signUp = 1;
  const signIn = 2;
  const [page, togglePage] = useState(signUp);
  const [open, setOpen] = useState({ state: false, message: "" });
  const status = useSelector(getUserStatus);
  const error = useSelector(getUserError);
  const user = useSelector(getUser)
  const {t} = useTranslation()
  

  const navigate = useNavigate();
  const auth = useSelector(isAuth);
  useEffect(() => {
    if (auth) {
      navigate(PathConstants.ARTICLE);
    }
  }, []);

  const handleTogglePage = () => {
    togglePage((prevState) => (prevState === signIn ? signUp : signIn));
  };

  useEffect(() => {
    if (status == statusTypes.succeeded) {
      if (page != signIn) {
        togglePage((prevState) => (prevState === signIn ? signUp : signIn));

        setOpen({
          state: true,
          message: t('message_success'),
          type: "success",
        });
      } else {
        dispatch(setActiveUser(user))
        navigate(PathConstants.PROFILE);
      }
    } else if (status == statusTypes.failed) {
      setOpen({ state: true, message: error.data.message, type: "error" });
    }
    dispatch(controlUserStatus(statusTypes.idle));
  }, [status]);

  const handleClose = (event, reason) => {
    if (reason === "clickaway") {
      return;
    }

    setOpen({ state: false, message: "", type: "" });
    dispatch(clearUserError())
  };

  return (
    <>
      <Box sx={{ maxWidth: "400px", margin: "0 auto", marginTop: "100px" }}>
        {status == statusTypes.loading ? <LinearProgress /> : null}
        <MenuWrapper>
          <Box sx={{ display: "flex", flexDirection: "column", width: "100%" }}>
            <Typography
              sx={{ textAlign: "center", marginBottom: "10px" }}
              variant="h5"
            >
              {t('txt_welcome')}
            </Typography>
            {page == signIn ? (
              <Signin onTogglePage={handleTogglePage} />
            ) : (
              <Signup onTogglePage={handleTogglePage} />
            )}
          </Box>
        </MenuWrapper>
      </Box>

      <Snackbar open={open.state} autoHideDuration={6000} onClose={handleClose}>
        <Alert
          onClose={handleClose}
          severity={open.type}
          variant="filled"
          sx={{ width: "100%" }}
        >
          {open.message}
        </Alert>
      </Snackbar>
    </>
  );
}

export default Login;
