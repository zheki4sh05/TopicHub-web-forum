import {
  Box,
  FormControl,
  InputLabel,
  NativeSelect,
  TextField,
  Typography,
} from "@mui/material";
import { useState } from "react";
import SelectHub from "../../hubs/ui/SelectHub";
import { useDispatch, useSelector } from "react-redux";
import { getTheme, saveTheme, setHub } from "../../../features/Sanbox/model/sandboxSlice";

function ArticleTheme({ data }) {
  // const [theme, setTheme] = useState(useSelector(getTheme));
  const theme = useSelector(getTheme);
  const dispatch = useDispatch()



  const handleHubChange = (value) => {
    dispatch(setHub({id:value}))
  };

  const handleThemeChange=(data)=>{
    dispatch(saveTheme({theme:data}))
    //setTheme(data)
  }

  return (
    <Box sx={{ display: "flex", flexDirection: "column",width:"100%"}}>
      <Box>
        <Typography variant="subtitle1" gutterBottom>
          {data.login}
        </Typography>
      </Box>
      <Box sx={{ display: "flex", justifyContent: "space-between" }}>
        <TextField
          id="standard-basic"
          label="Введите название темы"
          variant="standard"
          value={theme}
          onChange={(event) =>handleThemeChange(event.target.value)}
        />
        <SelectHub handleChange={handleHubChange} />
      </Box>
    </Box>
  );
}

export default ArticleTheme;
