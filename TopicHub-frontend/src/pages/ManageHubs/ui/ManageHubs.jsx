import { Box, Button, Stack, TextField, Typography } from "@mui/material";
import AdminMenu from "../../../widgets/admin/ui/AdminMenu";
import { useDispatch, useSelector } from "react-redux";
import { getHubs } from "../../Article/model/feedSlice";
import MenuWrapper from "../../../widgets/menu/ui/MenuWrapper";
import { useEffect, useState } from "react";
import { createHubs, doDeleteHubs, doUpdateHubs } from "../../../entities/hubs/api/request";
import { fetchHubs } from "../../Article/api/requests";
import { getHubsList } from "../../../entities/hubs/model/hubsSlice";

function ManageHubs() {
  const hubs = useSelector(getHubsList);
  const dispatch = useDispatch()

  const [create, setCreate] = useState(false);
  const [edit,setEdit] = useState(false)
  const [value, setValue] = useState("");
  const [id,setId]=useState("")

  useEffect(()=>{

    if(hubs.length==0){
      dispatch(fetchHubs())
    }
     
  },[])

  const handleDelHub = (id) => {
    dispatch(doDeleteHubs(
      {
        hubId:id
      }
    ))
  };

  const handleEditHub = (id) => {
    console.log(id)
    dispatch(doUpdateHubs(
      {
        id:id,
        name:value
      }
    ))
    setId("")
    setEdit(false)
    setValue("")
  };

  const handleAddHub = () => {
      dispatch(
        createHubs({
          name:value
        })
      )
      setCreate(false)
      setValue("")
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
            {create || edit ? (
              <Button
                variant="outlined"
                color="success"
                onClick={() => {
                  setCreate(false);
                  setEdit(false);
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

        {create || edit ? (
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
                  {
                      create ?
                      <Button
                      variant="outlined"
                      color="primary"
                      onClick={handleAddHub}
                    >
                      Сохранить
                    </Button>
                    : edit ? 
                    <Button
                    variant="outlined"
                    color="primary"
                    onClick={()=>handleEditHub(id)}
                  >
                    Сохранить
                  </Button>
                  :
                  null


                  }
                 
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
                    onClick={() => {setId(item.id); setEdit(true); setValue(item.name)}}
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
