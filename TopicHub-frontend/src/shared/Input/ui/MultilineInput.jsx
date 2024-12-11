import { Box, Button, TextField } from "@mui/material";
import { useState } from "react";

function MultilineInput({handleSubmit, defaultValue=""}) {

    const [value,setValue] = useState(defaultValue)

    

    return ( 

        <Box sx={{margin:'0px', display:"flex",flexDirection:"column"}}>

        <TextField
          id="standard-multiline-static"
          label="Ввведите свой комментарий"
          multiline
          rows={4}
          onChange={(event)=>setValue(event.target.value)}
          defaultValue={value}
          variant="standard"
          sx={{width:"100%"}}
        /> 
        <Box sx={{marginTop:"10px"}}>
        <Button size={"small"} variant="outlined" color="success" disabled={value.length==0} onClick={()=>handleSubmit(value)}>Опубликовать</Button>
        </Box>
       

      </Box>

     );
}

export default MultilineInput;