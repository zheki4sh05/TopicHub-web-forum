import { Box, Button, Stack, Typography } from "@mui/material";
import MenuWrapper from "../../../widgets/menu/ui/MenuWrapper";

function Complaint({
  type,
  name,
  body,
  date,
  handleReject,
  handleOpen,
  handleDelete,
}) {
  return (
    <MenuWrapper>
      <Stack direction={"column"} sx={{width:"100%"}}>
        <Stack direction={"row"}>
          <Typography variant="h6">{name}</Typography>
          <Typography variant="body2" style={{ textTransform: "uppercase" }}>
            {type}
          </Typography>
        </Stack>
        <Stack direction={"row"}>
          <Typography variant="body2">{body}</Typography>
        </Stack>
        <Box sx={{ display: "flex", justifyContent: "space-between",width:"100%" }}>
          <Box sx={{ flex: 1 }}>
            <Typography>{date}</Typography>
          </Box>
          <Box
            sx={{
              display: "flex",
              justifyContent: "flex-end",
              gap: "15px",
              flex: 1,
            }}
          >
            <Button variant="contained" color="error" onClick={handleReject}>
              Отклонить
            </Button>
            <Button variant="outlined" onClick={handleOpen}>
              Открыть
            </Button>
            <Button variant="contained" color="warning" onClick={handleDelete}>
              Удалить
            </Button>
          </Box>
        </Box>
      </Stack>
    </MenuWrapper>
  );
}

export default Complaint;
