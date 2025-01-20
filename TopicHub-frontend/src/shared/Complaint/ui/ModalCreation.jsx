import { Box, Button, Stack, TextField, Typography } from "@mui/material";
import { useEffect, useState } from "react";
import { useDispatch } from "react-redux";
import { complaintArticle } from "../api/requests";
import { useTranslation } from 'react-i18next';

function ModalCreation({ item, handleClose }) {
  const [title, setTitle] = useState("");
  const [body, setBody] = useState("");
  const [edit, setIsEdit] = useState(false);
  const {t} = useTranslation()
  const dispatch = useDispatch();

  const valid = () => {
    return title.trim().length != 0 && body.trim().length != 0;
  };

  useEffect(() => {
    if (valid()) {
      setIsEdit(true);
    }
  }, [title, body]);

  const handleCreate = () => {
    dispatch(complaintArticle({
        id:item.id,
        type:"article",
        title:title,
        body:body,
        targetId:item.id
    }

    ));
    handleClose();
  };

  return (
    <Stack direction={"column"}>
      <Stack direction={"row"} sx={{ justifyContent: "space-between",marginBottom:"15px" }}>
        <Box>
          <Typography
            variant="body2"
            style={{ textTransform: "uppercase", color: "red" }}
          >
            {t('txt_complaint_title')}
          </Typography>
        </Box>
        <Box>
          <Typography variant="body2" style={{ textTransform: "uppercase" }}>
          {t('txt_article')}
          </Typography>
        </Box>
      </Stack>
      <Stack direction={"column"}>
        <TextField
          id="outlined-basic"
          sx={{ marginBottom: "10px" }}
          label= {t('input_title')}
          variant="outlined"
          value={title}
          onChange={(event) => {
            setTitle(event.target.value);
          }}
        />

        <TextField
          id="outlined-multiline-static"
          label={t('input_description')}
          multiline
          rows={4}
          value={body}
          onChange={(event) => {
            setBody(event.target.value);
          }}
        />
        <Button onClick={handleCreate} disabled={!edit}>
          {t('btn_send')}
        </Button>
      </Stack>
    </Stack>
  );
}

export default ModalCreation;
