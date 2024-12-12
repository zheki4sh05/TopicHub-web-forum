import { Button, IconButton, Popover, Typography } from "@mui/material";
import AccountCircleIcon from '@mui/icons-material/AccountCircle';
import { useNavigate } from "react-router";
import { useEffect, useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { getUser, isAuth, setActiveUser } from "../../../pages/Profile/model/userSlice";
import { PathConstants } from './../../../app/pathConstants';

function UserSettings() {
  const dispatch = useDispatch()
  const user = useSelector(getUser)
  const navigate = useNavigate()

  const [anchorEl, setAnchorEl] = useState(null);
  const auth = useSelector(isAuth)


  const handleClick = (event) => {
    if(!auth){
      setAnchorEl(event.currentTarget);
    }else{
      dispatch(setActiveUser(user))
      navigate(PathConstants.PROFILE)
    }
   
  };

  const handleClose = () => {
    setAnchorEl(null);
  };


  const open = Boolean(anchorEl);
  const id = open ? 'simple-popover' : undefined;

    return ( <>
    
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
        </IconButton>
        <Popover
  id={id}
  open={open}
  anchorEl={anchorEl}
  onClose={handleClose}
  anchorOrigin={{
    vertical: 'bottom',
    horizontal: 'left',
  }}
>
  <Typography sx={{ p: 2 }}>Для доступа к профилю необходимо авторизоваться</Typography>

  <Button onClick={()=>navigate(PathConstants.LOGIN)}>Авторизация</Button>
</Popover>
    
      </>
    
    </> );
}

export default UserSettings;