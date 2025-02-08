import { Box, Button, TextField, Typography } from "@mui/material";
import { useEffect, useState } from "react";
import { Controller, useForm } from "react-hook-form";
import { useDispatch } from "react-redux";
import { signin } from "../api/requests";
import { useTranslation } from "react-i18next";
import { t } from "i18next";

function Signin({onTogglePage}) {
    const dispatch = useDispatch()
    const [message, setMessage] = useState("");
    const { handleSubmit, control, reset } = useForm();
    const {t} = useTranslation()
    const onSubmit = (data) => {
   
      
      dispatch(signin({
        login:data.login,
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
        <Typography variant="h6" gutterBottom>{t('btn_auth')}</Typography>
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
              label={t('message_login_or_email')}
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
          {t('btn_enter')}
        </Button>
        <Box sx={{marginTop:"10px", display:"flex", flexDirection:"row", gap:"10px",alignItems:"center"}} >
          <Typography> {t('txt_at_first')}</Typography>
          <Button variant="text" onClick={(event)=>onTogglePage(1)}> {t('btn_signup')} </Button>
        </Box>
      </form>
    );
}

export default Signin;