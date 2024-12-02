import { FormControl, InputLabel, MenuItem, Select } from "@mui/material";
import { useState } from "react";

function MainSelect({ title, list, handleChange, defaultValue,reset=false }) {
    const [state,setState]=useState(defaultValue)

    const onChange=(event)=>{
      setState(event.target.value)
      handleChange(event.target.value)
      if(reset){
        setState(defaultValue)
      }
    }

  return (
    <FormControl>
      <InputLabel id="demo-simple-select-helper-label"> {title}</InputLabel>

      <Select
          labelId="demo-simple-select-helper-label"
          id="demo-simple-select-helper"
          value={state}
          label="Хаб"
          onChange={onChange}
          sx={{width:"220px"}}
        >
           {list.map((item, index) => (
          <MenuItem  key={index} value={item.id}>
            {item.name}
          </MenuItem>
        ))}
        </Select>

      
    </FormControl>
  );
}

export default MainSelect;
