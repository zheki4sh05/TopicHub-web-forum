import {
  Alert,
  Box,
  Button,
  Chip,
  CircularProgress,
  IconButton,
  Paper,
  Snackbar,
  Typography,
} from "@mui/material";
import statusTypes from "../../../app/util/statusTypes";
import { formatDateFromTimestamp } from "../../../app/util/date";
import { useDispatch, useSelector } from "react-redux";
import { Link } from "react-router";
import { PathConstants } from "../../../app/pathConstants";
import {
  getComplaintStatus,
  manageComplaintStatus,
  setArticle,
} from "../model/articleSlice";
import ReactionBox from "../../../shared/ReactionBox/ui/ReactionBox";
import EditIcon from "@mui/icons-material/Edit";
import DeleteIcon from "@mui/icons-material/Delete";
import { getHubsList } from "../../../entities/hubs/model/hubsSlice";
import { useEffect, useState } from "react";
import MainModal from "../../../shared/ConfirmModal/ui/Modal";
import ModalCreation from "./../../../shared/Complaint/ui/ModalCreation";
import { useTranslation } from "react-i18next";
import { getActiveLanguage } from "../../../processes/header/model/settingsSlice";
import { fetchHubs } from "../../../pages/Article/api/requests";
function Article({ item = {}, mode, edit = false, handleDelete, handleEdit }) {
  const { t } = useTranslation();
  const lang = useSelector(getActiveLanguage);
  const hubs = useSelector(getHubsList);
  useEffect(() => {
    if (hubs.length == 0) {
      dispatch(fetchHubs());
    }
  }, []);

  const status = useSelector(getComplaintStatus);

  const dispatch = useDispatch();
  const getArticlePart = (item, index) => {
    switch (item.type) {
      case "list": {
        return <></>;
      }
      case "img": {
        return (
          <Box key={index}>
            <img
              src={item.value}
              alt={t('alt_error')}
              style={{ width: "100%" }}
            />
            <Typography variant="body2" gutterBottom sx={{ width: "100%" }}>
              {item.value}
            </Typography>
          </Box>
        );
      }
      case "paragraph": {
        return (
          <Typography
            variant="body2"
            gutterBottom
            sx={{ width: "100%" }}
            key={index}
          >
            {item.value}
          </Typography>
        );
      }
      case "chapter": {
        return (
          <Typography
            variant="h6"
            gutterBottom
            sx={{ width: "100%" }}
            key={index}
          >
            {item.value}
          </Typography>
        );
      }
    }
  };
  const getContent = (item) => {
    return item.list.map((item, index) => <> {getArticlePart(item, index)}</>);
  };

  const getViewByMode = (type, item) => {
    switch (type) {
      case statusTypes.short: {
        return (
          <Box sx={{ maxHeight: "170px", overflow: "hidden" }}>
            {getContent(item)}
          </Box>
        );
      }
      case statusTypes.long: {
        return (
          <>
            <Box sx={{ maxHeight: "auto" }}>{getContent(item)}</Box>
          </>
        );
      }
    }
  };

  const handleActive = () => {
    dispatch(setArticle(item));
  };

  const initialSnackBarState = { state: false, severity: "", text: "" };

  const [open, setOpen] = useState(false);
  const [scackBar, setSnacknar] = useState(initialSnackBarState);

  useEffect(() => {
    if (status == statusTypes.succeeded) {
      setSnacknar({
        state: true,
        severity: "success",
        text: "Жалоба отправлена",
      });
    } else if (status == statusTypes.failed) {
      setSnacknar({
        state: true,
        severity: "error",
        text: "Не удалось отправить",
      });
    }
    dispatch(manageComplaintStatus(statusTypes.idle));
  }, [status]);

  const handleCloseSnackBar = () => {
    setSnacknar(initialSnackBarState);
  };

  return (
    <>
      <Paper elevation={1}>
        <Box
          sx={{
            width: "100%",
            display: "flex",
            flexDirection: "column",
            padding: "15px 15px 5px 15px",
            boxSizing: "border-box",
          }}
        >
          {mode == "long" && edit ? (
            <Box
              sx={{
                display: "flex",
                justifyContent: "space-between",
                marginBottom: "15px",
              }}
            >
              <Link
                style={{ textDecoration: "none" }}
                to={{ pathname: PathConstants.EDIT }}
              >
                <IconButton>
                  <EditIcon />
                </IconButton>
              </Link>

              <IconButton onClick={() => handleDelete(item)}>
                <DeleteIcon />
              </IconButton>
            </Box>
          ) : null}

          <Box sx={{ display: "flex", justifyContent: "space-between" }}>
            <Box
              sx={{
                display: "flex",
                justifyContent: "flex-start",
                alignItems: "center",
              }}
            >
              <Typography sx={{ marginLeft: "15px" }} variant="subtitle1">
                {item.userDto.login}
              </Typography>
              <Typography sx={{ marginLeft: "15px" }} variant="caption">
                {formatDateFromTimestamp(item.created)}
              </Typography>
            </Box>
            <Box>
              <Typography
                variant="body1"
                sx={{ color: "#6495ED", textDecoration: "underline" }}
              >
                {hubs.length == 0 ? (
                  <CircularProgress />
                ) : (
                  hubs.find((hub) => hub.id == item.hub)[lang]
                )}
              </Typography>
            </Box>
          </Box>
          <Box sx={{ display: "flex", flexDirection: "column" }}>
            <Box>
              <Typography variant="h6">{item.theme}</Typography>
            </Box>
            {getViewByMode(mode, item)}
            <Box
              sx={{
                display: "flex",
                flexDirection: "row",
                borderBottom: "1px solid #C4C4C4",
                marginTop: "15px",
              }}
            >
              {mode == "short" ? (
                <Box sx={{ height: "30px", width: "140px" }}>
                  <Link
                    to={
                      edit ? PathConstants.PROFILE_ARTICLE : PathConstants.VIEW
                    }
                  >
                    <Button
                      variant="contained"
                      size="small"
                      onClick={handleActive}
                    >
                      {t("btn_read_more")}
                    </Button>
                  </Link>
                </Box>
              ) : null}

              <Box sx={{ paddingBottom: "10px" }}>
                {item.keyWords.map((item, index) => (
                  <Chip label={item} key={index} sx={{ margin: "0 5px" }} />
                ))}
              </Box>
            </Box>
            <ReactionBox
              item={item}
              showDanger={true}
              handleDanger={() => {
                setOpen(true);
              }}
            />
          </Box>
        </Box>
      </Paper>
      <MainModal open={open} handleClose={() => setOpen(false)}>
        <ModalCreation item={item} handleClose={() => setOpen(false)} />
      </MainModal>

      <Snackbar
        open={scackBar.state}
        autoHideDuration={6000}
        onClose={handleCloseSnackBar}
      >
        <Alert
          onClose={handleCloseSnackBar}
          severity={scackBar.severity}
          variant="filled"
          sx={{ width: "100%" }}
        >
          {scackBar.text}
        </Alert>
      </Snackbar>
    </>
  );
}

export default Article;
