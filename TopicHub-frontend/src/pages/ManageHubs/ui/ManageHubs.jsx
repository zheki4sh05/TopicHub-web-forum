import { Box, Button, Stack, TextField, Typography } from "@mui/material";
import AdminMenu from "../../../widgets/admin/ui/AdminMenu";
import { useSelector } from "react-redux";
import { getHubs } from "../../Article/model/feedSlice";
import MenuWrapper from "../../../widgets/menu/ui/MenuWrapper";
import { useState } from "react";

function ManageHubs() {
  const hubs = useSelector(getHubs);

  const [create, setCreate] = useState(false);
  const [value, setValue] = useState("");

  const handleDelHub = (id) => {};

  const handleEditHub = (id) => {};

  const handleAddHub = () => {

    

  };

  return (
    <Box>
      <AdminMenu />
      <Box sx={{ maxWidth: "1000px", margin: "0 auto", marginTop: "10px" }}>
        <MenuWrapper>
          <Box
            sx={{
              display: "flex",
              justifyContent: "space-between",
              width: "100%",
            }}
          >
            <Typography>Всего: {hubs.length}</Typography>
            {create ? (
              <Button
                variant="outlined"
                color="success"
                onClick={() => {
                  setCreate(false);
                  setValue("");
                }}
              >
                Отменить
              </Button>
            ) : (
              <Button
                variant="outlined"
                color="success"
                onClick={() => setCreate(true)}
              >
                Добавить
              </Button>
            )}
          </Box>
        </MenuWrapper>

        {create ? (
          <Box sx={{ marginTop: "20px", marginBottom: "20px" }}>
            <MenuWrapper>
              <Box
                sx={{
                  display: "flex",
                  justifyContent: "space-between",
                  width: "100%",
                }}
              >
                <TextField
                  value={value}
                  variant="standard"
                  size="small"
                  onChange={(event) => setValue(event.target.value)}
                />

                <Stack direction={"row"} gap={2}>
                  <Button
                    variant="outlined"
                    color="primary"
                    onClick={handleAddHub}
                  >
                    Сохранить
                  </Button>
                </Stack>
              </Box>
            </MenuWrapper>
          </Box>
        ) : null}
        <Stack direction={"column"} spacing={1} sx={{ marginTop: "25px" }}>
          {hubs.map((item, index) => (
            <MenuWrapper key={index}>
              <Box
                sx={{
                  display: "flex",
                  justifyContent: "space-between",
                  width: "100%",
                }}
              >
                <Typography variant="h6">{item.name}</Typography>
                <Stack direction={"row"} gap={2}>
                  <Button
                    variant="outlined"
                    color="error"
                    onClick={() => handleDelHub(item.id)}
                  >
                    Удалить
                  </Button>
                  <Button
                    variant="outlined"
                    color="primary"
                    onClick={() => handleEditHub(item.id)}
                  >
                    Изменить
                  </Button>
                </Stack>
              </Box>
            </MenuWrapper>
          ))}
        </Stack>
      </Box>
    </Box>
  );
}

export default ManageHubs;
