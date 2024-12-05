import { Box, Button, Grid2, IconButton, TextField, Typography } from "@mui/material";
import { useState } from "react";
import SaveIcon from "@mui/icons-material/Save";
import EditIcon from "@mui/icons-material/Edit";
import { delItem, saveItem } from "../../model/sandboxSlice";
import { useDispatch } from "react-redux";
import DeleteIcon from "@mui/icons-material/Delete";
import ConfirmModal from "../../../../shared/ConfirmModal/ui/ConfirmModal";
import AddIcon from "@mui/icons-material/Add";
import FiberManualRecordIcon from '@mui/icons-material/FiberManualRecord';
function ListEdit({ list }) {
    console.log(list)
  const parseList = (item) => {
    return [{id:1, value:"value 1"}]
  };
  const dispatch = useDispatch();


  const [open, setOpen] = useState(false);
  const [state, setState] = useState(parseList(list.value));

  const handleSave = (item) => {
    let obj = state.find(value=>value.id===item.id);
    obj.save = false
        dispatch(saveItem({created:list.id, value:state}));

  };
  const handleEdit = (item) => {
    
   
     let obj = state.find(value=>value.id===item.id);
     obj.save = false
    
     dispatch(saveItem({created:list.id, value:state}));
  };

  const onChange = (event, item) => {

    let obj = state.find(value=>value.id===item.id);

     obj.value = event.target.value
    
     dispatch(saveItem({created:list.id, value:state}));
  };

  const handleDelete = () => {
    // if (state.trim().length != 0) {
    //   setOpen(true);
    // } else {
    //   handlerAgree();
    // }
  };

  const handlerAgree = (id) => {
    dispatch(delItem({ created: id }));
  };
  const handlerDisagree = () => {
    setOpen(false);
  };

  const handleAdd = () => {

    setState([...state, {id:new Date().getTime(), save:false, value:"" }]);
    dispatch(saveItem({created:list.id, value:state}));
  };

  return (
    <>
      {state.map((item, index) => (
        <Grid2 container sx={{ width: "100%", margin: "5px 0" }} key={index}>
          <Grid2 size={1}>
            <Box sx={{ display: "flex", alignItems: "flex-start" }}>
              {item.save ? (
                <IconButton onClick={()=>handleEdit(item)}>
                  <EditIcon />
                </IconButton>
              ) : (
                <IconButton onClick={()=>handleSave(item)}>
                  <SaveIcon />
                </IconButton>
              )}
            </Box>
          </Grid2>
          <Grid2 size={10}>
            {item.save ? (
              <Typography variant="body2" gutterBottom sx={{ width: "100%" }}>
             <FiberManualRecordIcon fontSize="small" />   {item.value}
              </Typography>
             
            ) : (
              <TextField
                id="standard-multiline-static"
                label="Абзац"
                multiline
                defaultValue="Default Value"
                variant="standard"
                value={item.value}
                sx={{ width: "100%" }}
                onChange={(event)=>onChange(event, item)}
              />
            )}
          </Grid2>
          <Grid2 size={1}>
            <Box
              sx={{
                display: "flex",
                alignItems: "flex-start",
                marginLeft: "10px",
              }}
            >
              <IconButton onClick={handleDelete}>
                <DeleteIcon />
              </IconButton>
            </Box>
          </Grid2>
        </Grid2>
      ))}

      <Button onClick={handleAdd}>Добавить пункт</Button>
      <ConfirmModal
        show={open}
        title={"Удаление списка"}
        body={"Вы действительно хотите удалить список?"}
        data={list}
        handlerAgree={handlerAgree}
        handlerDisagree={handlerDisagree}
      />
    </>
  );
}

export default ListEdit;
