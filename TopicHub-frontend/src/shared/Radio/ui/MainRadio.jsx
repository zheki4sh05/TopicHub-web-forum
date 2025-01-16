import { FormControl,RadioGroup,Radio, FormControlLabel, FormLabel } from '@mui/material';
function MainRadio({title, list, changeHandler,defaultValue}){

  const onChange=(event)=>{
    changeHandler(event.target.value)
  }
    return (
        <FormControl>
        <FormLabel id="demo-radio-buttons-group-label">{title}</FormLabel>
        <RadioGroup
          aria-labelledby="demo-radio-buttons-group-label"
          value={defaultValue}
          name="radio-buttons-group"
          onChange={onChange}
        >
          {
            list.map(item=>(
              <FormControlLabel key={item.id} value={item.id} control={<Radio/>} label={item.name}/>
            ))
          }
         
         
        </RadioGroup>
      </FormControl>
    )
}
export default MainRadio;