import { Box, Button, Chip, TextField, Typography } from "@mui/material";
import { useForm, Controller } from "react-hook-form";
import { useEffect, useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { getSandboxList, getSandboxWords, setKeywords } from "../../../features/Sanbox/model/sandboxSlice";

function KeyWords() {
  const { handleSubmit, control, reset } = useForm();
 
 const list = useSelector(getSandboxWords)
  const [error, setError] = useState({ show: false, message: "" });
  const dispatch = useDispatch()
  const handleDelete = (e,item) => {
    
    dispatch(setKeywords([...list.filter(i=>i.name!=item.name)]))
  };

  const onSubmit = (data) => {
    if (
      !list.find(
        (obj) =>
          obj.name.toLowerCase().trim() === data.name.toLowerCase().trim()
      )
    ) {
  

      dispatch(setKeywords([...list, { id: new Date().getTime(), name: data.name }]))

      setError({ show: false, message: "" });

    } else {
      setError({ show: true, message: "Такое имя уже существует!" });
    }
    reset();
  };


  return (
    <Box sx={{ display: "flex", flexDirection: "column", width: "100%" }}>
      <Typography variant="subtitle1" gutterBottom>
        Ключевые слова
      </Typography>
      <Box sx={{ display: "flex", flexDirection: "row", width: "100%" }}>
        <Box
          sx={{
            display: "flex",
            flexDirection: "row",
            flexWrap: "wrap",
            maxWidth: "400px",
            justifyContent: "flex-start",
          }}
        >
          {list.map((item) => (
            <Box key={item.id} sx={{ marginBottom: "5px", marginRight: "5px" }}>
              <Chip  label={item.name} onDelete={(e)=>handleDelete(e, item)} />
            </Box>
          ))}
        </Box>
        <form onSubmit={handleSubmit(onSubmit)}>
          <Box
            sx={{
              display: "flex",
              flexDirection: "row",
              width: "auto",
              justifyContent: "flex-start",
              marginLeft: "20px",
            }}
          >
            <Box>
              <Controller
                name="name"
                control={control}
                defaultValue=""
                render={({ field }) => (
                  <TextField
                    {...field}
                    id="standard-basic"
                    label="Название"
                    variant="standard"
                  />
                )}
              />
              {error.show ? (
                <Typography
                  variant="caption"
                  gutterBottom
                  color="error"
                  sx={{ display: "block" }}
                >
                  {error.message}
                </Typography>
              ) : null}
            </Box>

            <Button sx={{ marginLeft: "5px" }} type="submit" variant="text">
              Добавить
            </Button>
          </Box>
        </form>
      </Box>
    </Box>
  );
}

export default KeyWords;
