import { IconButton, Paper, TextField } from "@mui/material";
import SearchIcon from "@mui/icons-material/Search";
import { useState } from "react";
function SearchBox() {

    const [state,setState]= useState("")
  const onClick=()=>{
    console.log(state)
  }

    const handleSearch=()=>{

    }

    return (
        <Paper
          component="form"
          elevation={1}
          sx={{
            display: "flex",
            alignItems: "center",
            width: 300,
            bgcolor: "white",
            padding:"0 5px",
            border:"#DCDCDC solid 2px",
            borderRadius:"10px"
          }}
          onSubmit={handleSearch}
        >
            
    
          <TextField
            id="input"
            name="input"
            sx={{ flex: 1}}
            
            siz="small"
            variant="standard"
            value={state}
             onChange={(event)=>setState(event.target.value)}
           
          />
          <IconButton  onClick={onClick} sx={{ p: "5px" }} aria-label="search">
            <SearchIcon />
          </IconButton>
        </Paper>
      );
}

export default SearchBox;