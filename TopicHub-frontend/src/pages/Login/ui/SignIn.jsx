import { Box, Button, TextField, Typography } from "@mui/material";
import { useEffect, useState } from "react";
import { Controller, useForm } from "react-hook-form";
import { useDispatch } from "react-redux";
import { signin } from "../api/requests";

function Signin({onTogglePage}) {
    const dispatch = useDispatch()
    const [message, setMessage] = useState("");
    const { handleSubmit, control, reset } = useForm();
    const onSubmit = (data) => {
      
      
      dispatch(signin({
        data:data.login,
        password:data.password
      }))
  
     
    };

    
    return (
      <form
        style={{
          display: "flex",
          width: "100%",
          flexDirection: "column",
        }}
        onSubmit={handleSubmit(onSubmit)}
      >
        <Typography variant="h6" gutterBottom>Авторизация</Typography>
        <Controller
          name="login"
          control={control}
          defaultValue=""
          render={({ field }) => (
            <TextField
              {...field}
              sx={{ marginBottom: "10px" }}
              id="login"
              type="login"
              name="login"
              label="Логин либо почту"
              variant="outlined"
            />
          )}
        />
  
        
  
        <Controller
          name="password"
          control={control}
          defaultValue=""
          render={({ field }) => (
            <TextField
              {...field}
              sx={{ marginBottom: "15px" }}
              id="password"
              name="password"
              label="Пароль"
              variant="outlined"
              type="password"
            />
          )}
        />
  
        <Box sx={{ height: "20px" }}>
          <Typography variant="caption" sx={{ color: "red" }}>
            {message}
          </Typography>
        </Box>
  
        <Button
          sx={{ width: "100%", marginTop: "20px" }}
          size="small"
          variant="contained"
          type="submit"
        >
          Войти
        </Button>
        <Box sx={{marginTop:"10px", display:"flex", flexDirection:"row", gap:"10px",alignItems:"center"}} >
          <Typography>Впервые на форуме?</Typography>
          <Button variant="text" onClick={(event)=>onTogglePage(1)}> Регистрация </Button>
        </Box>
      </form>
    );
}

export default Signin;