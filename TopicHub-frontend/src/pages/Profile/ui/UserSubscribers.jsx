import { Box, LinearProgress, Stack, Typography } from "@mui/material";
import { useDispatch, useSelector } from "react-redux";
import { getUserSubscribes } from "../model/userSlice";
import Author from "./../../../widgets/author/ui/Author";
import SubscribeBtn from "../../../features/Subscribe/ui/SibsctibeBtn";
import { useEffect } from "react";
import { fetchUserSubscriptions } from "../api/requests";
import {
  getSubscriptionStatus,
  manageSubscriptionStatus,
} from "../../../features/Article/model/articleSlice";
import statusTypes from "../../../app/util/statusTypes";

function UserSubscribers({ edit }) {
  const subscribeStatus = useSelector(getSubscriptionStatus);
  const subscribes = useSelector(getUserSubscribes);
  const dispatch = useDispatch();

  const makeRequest = () => {
    dispatch(
      fetchUserSubscriptions({
        type: "subscribes",
      })
    );
  };

  useEffect(() => {
    if (subscribeStatus == statusTypes.idle) {
      makeRequest();
    }
  }, []);

  useEffect(() => {
    if (subscribeStatus == statusTypes.idle) {
      makeRequest();
    }
  }, [subscribeStatus]);

  useEffect(() => {
    if (subscribeStatus == statusTypes.succeeded) {
      dispatch(manageSubscriptionStatus(statusTypes.idle));
    }
  }, [subscribeStatus]);

  return (
    <>
      {subscribeStatus == statusTypes.loading ? (
        <LinearProgress />
      ) : subscribes.length > 0 ? (
        <>
          <Typography sx={{ marginBottom: "15px" }}>
            Всего {subscribes.length}
          </Typography>
          <Stack direction="row">
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
                    <SubscribeBtn
                      state={!subscribes.length > 0}
                      authorId={item.id}
                    />
                  ) : null}
                </Box>
              </Box>
            ))}
          </Stack>
        </>
      ) : (
        <Typography>Нет подписок</Typography>
      )}
    </>
  );
}

export default UserSubscribers;
