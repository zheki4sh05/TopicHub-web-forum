import { Box, LinearProgress, Stack, Typography } from "@mui/material";
import { useDispatch, useSelector } from "react-redux";
import { getSubscriptionStatus } from "../../../features/Article/model/articleSlice";
import { getActiveUser, getUserFollowers, getUserStatus, getUserSubscribes } from "../model/userSlice";
import { fetchUserFollowers } from "../api/requests";
import { useEffect } from "react";
import statusTypes from "../../../app/util/statusTypes";
import Author from "../../../widgets/author/ui/Author";
import SubscribeBtn from "../../../features/Subscribe/ui/SibsctibeBtn";

function UserFollowers({edit}) {

    const user = useSelector(getActiveUser)

    const status = useSelector(getUserStatus);
    const subscribes = useSelector(getUserFollowers);
    const dispatch = useDispatch();
  
    const makeRequest = () => {
      dispatch(
        fetchUserFollowers({
          type: "followers",
        })
      );
    };
    useEffect(() => {
      
          makeRequest();
        
      }, []);

  

    return (
        <>
        {status == statusTypes.loading ?
        <LinearProgress/>

        : subscribes.length>0 ? 
        <Stack direction="column">
            <Typography sx={{marginBottom:"15px"}} >Всего: {subscribes.length}</Typography>
        {subscribes.map((item, index) => (
          <Box
            key={index}
            sx={{
              display: "flex",
              justifyContent: "space-between",
              marginBottom: "15px",
              alignItems: "center",
              width: "100%",
            }}
          >
            <Author user={item} edit={false} size={50} />
            <Box>
              {edit ? (
                <SubscribeBtn state={!subscribes.length > 0} authorId={user.id} text={"Отписать"}/>
              ) : null}
            </Box>
          </Box>
        ))}
      </Stack>
      :
      <Typography>Нет подписчиков</Typography>
        
        
        }
        
        
        </>
     
    )
}

export default UserFollowers;