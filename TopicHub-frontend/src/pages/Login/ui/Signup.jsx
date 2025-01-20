import { Box, Button, TextField, Typography } from "@mui/material";
import { useState } from "react";
import { Controller, useForm } from "react-hook-form";
import { areAllFieldsFilled, isValidLogin, isValidPass } from "../config/fieldValidator";
import { useDispatch } from "react-redux";
import { signup } from "../api/requests";
import { useTranslation } from "react-i18next";

function Signup({ onTogglePage }) {
    const dispatch = useDispatch()
    const {t} = useTranslation()
  const [message, setMessage] = useState("");
  const { handleSubmit, control, reset } = useForm();

  const onSubmit = (data) => {
    
    if(!areAllFieldsFilled(data)){
        setMessage(t('auth_warn1'))
    }else if(data.password!==data.password2){
        setMessage(t('auth_warn2'))
        
    }else if(data.password.length<6 || data.password.lengt>12){
        setMessage(t('auth_warn3'))
    }
    else if (!isValidLogin(data.login)){
        setMessage(t('auth_warn4'))
    }else if(!isValidPass(data.password)){
        setMessage(t('auth_warn5'))
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
        <Typography variant="h6" gutterBottom>{t('txt_signup')}</Typography>
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
            label={t('message_login')}
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
            label={t('message_email')}
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
            label={t('message_pass')}
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
            label={t('message_pass_twise')}
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
        
        {t('btn_signup')}
      </Button>
      <Box sx={{marginTop:"10px", display:"flex", flexDirection:"row", gap:"10px",alignItems:"center"}} >
        <Typography>{t('txt_is_auth')}</Typography>
        <Button variant="text" onClick={(event)=>onTogglePage(2)}> {t('btn_signin')} </Button>
      </Box>
    </form>
  );
}

export default Signup;
