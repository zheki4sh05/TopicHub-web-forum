import { Button } from "@mui/material";
import { useTranslation } from "react-i18next";
import LogoutIcon from '@mui/icons-material/Logout';
import { useDispatch, useSelector } from "react-redux";
import { logout } from "../api/request";
import { isAuth } from "../../../pages/Profile/model/userSlice";
import { PathConstants } from "../../../app/pathConstants";
import { useNavigate } from "react-router";
import { deleteTokens } from "../../../app/util/localstorageApi";
function LogoutBtn() {
    const {t} = useTranslation()
    const dispatch = useDispatch()
    const auth = useSelector(isAuth)
    const navigate = useNavigate();

    const handleClickLogout=()=>{
        dispatch(logout())
        deleteTokens()
        navigate(PathConstants.ARTICLE)
    }
    const handleClickLogin=()=>{
            navigate(PathConstants.LOGIN)
    }

    if(auth){
        return(
            <Button variant="outlined" color="secondary" startIcon={<LogoutIcon />} onClick={handleClickLogout} >
            {t('logout')}
          </Button>
        )
    }else{
        return(
            <Button variant="outlined" color="secondary" startIcon={<LogoutIcon />} onClick={handleClickLogin} >
            {t('login')}
          </Button>
        )

    }


}

export default LogoutBtn;