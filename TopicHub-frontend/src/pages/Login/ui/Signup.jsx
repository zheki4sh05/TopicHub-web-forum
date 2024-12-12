import { Box, Button, TextField, Typography } from "@mui/material";
import { useEffect, useState } from "react";
import { Controller, useForm } from "react-hook-form";
import { areAllFieldsFilled, isValidLogin, isValidPass } from "../config/fieldValidator";
import { useDispatch, useSelector } from "react-redux";
import { signup } from "../api/requests";
import { getUserError } from "../../Profile/model/userSlice";

function Signup({ onTogglePage }) {
    const dispatch = useDispatch()
  const [message, setMessage] = useState("");
  const { handleSubmit, control, reset } = useForm();

  const onSubmit = (data) => {
    
    if(!areAllFieldsFilled(data)){
        setMessage("Не все поля заполнены!")
    }else if(data.password!==data.password2){
        setMessage("Пароли не совпадают!")
        
    }else if(data.password.length<6 || data.password.lengt>12){
        setMessage("Пароль слишком короткий! Минимальный: 6 символов, Максимальный: 12")
    }
    else if (!isValidLogin(data.login)){
        setMessage("Логин должен содержать только латинские буквы и _ ")
    }else if(!isValidPass(data.password)){
        setMessage("Пароль должен содержать только латинские буквы либо цифры и #!? ")
    }else{
      setMessage("")
      dispatch(signup({
        login:data.login,
        email:data.email,
        password:data.password
      }))
    }   
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
        <Typography variant="h6" gutterBottom>Регистрация</Typography>
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
            label="Логин"
            variant="outlined"
          />
        )}
      />

      <Controller
        name="email"
        control={control}
        defaultValue=""
        render={({ field }) => (
          <TextField
            {...field}
            sx={{ marginBottom: "10px" }}
            id="email"
            type="email"
            name="email"
            label="Почта"
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

      <Controller
        name="password2"
        control={control}
        defaultValue=""
        render={({ field }) => (
          <TextField
            {...field}
            id="password2"
            name="password2"
            label="Еще раз пароль"
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
        Зарегистрироваться
      </Button>
      <Box sx={{marginTop:"10px", display:"flex", flexDirection:"row", gap:"10px",alignItems:"center"}} >
        <Typography>Уже есть аккаунт?</Typography>
        <Button variant="text" onClick={(event)=>onTogglePage(2)}> Войти </Button>
      </Box>
    </form>
  );
}

export default Signup;
