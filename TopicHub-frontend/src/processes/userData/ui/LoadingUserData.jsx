import { useEffect } from "react";
import { checkCookie, userData } from "../api/request";
import { useDispatch, useSelector } from "react-redux";
import { isAuth } from "../../../pages/Profile/model/userSlice";
import { getHubsList } from "../../../entities/hubs/model/hubsSlice";
import { fetchHubs } from "../../../pages/Article/api/requests";

function LoadingUserData() {
    const dispatch = useDispatch()
    const auth = useSelector(isAuth)
    const hubs = useSelector(getHubsList)
    // useEffect(()=>{
    //     dispatch(checkCookie())
    // },[])
    // useEffect(()=>{
    //     if(auth){
    //         dispatch(userData({
    //             type:"profile",
    //             page:1
    //         }))
    //     }
    // },[auth])
      useEffect(()=>{
      
          if(hubs.length==0){
            dispatch(fetchHubs())
          }
           
        },[])

    return null;
}

export default LoadingUserData;