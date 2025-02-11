import { useEffect } from "react";
import { checkCookie, refreshToken, userData } from "../api/request";
import { useDispatch, useSelector } from "react-redux";
import { isAuth, setAuth, setRefresh, setToken } from "../../../pages/Profile/model/userSlice";
import { getHubsList } from "../../../entities/hubs/model/hubsSlice";
import { fetchHubs } from "../../../pages/Article/api/requests";
import { getRefreshStorage } from "../../../app/util/localstorageApi";

function LoadingUserData() {
    const dispatch = useDispatch()
    const auth = useSelector(isAuth)
    const hubs = useSelector(getHubsList)
    // useEffect(()=>{
    //     dispatch(checkCookie())
    // },[])
     useEffect(()=>{

        const token = getRefreshStorage()

        if(token!=null && !auth){
           refreshToken(token).then(data=>{
                const access = data.access_token;
                const refresh = data.refresh_token;
                dispatch(setToken(access))
                dispatch(setRefresh(refresh))
                dispatch(setAuth())
                
           })
           .catch(error=>{

           })
           
        }

      
    },[])
    useEffect(()=>{
        if(auth){
            dispatch(userData({
                type:"profile",
                page:1
            }))
        }
    },[auth])
      useEffect(()=>{
      
          if(hubs.length==0){
            dispatch(fetchHubs())
          }
           
        },[])

    return null;
}

export default LoadingUserData;