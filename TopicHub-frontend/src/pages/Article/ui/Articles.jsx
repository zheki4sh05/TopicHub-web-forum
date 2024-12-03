import { Box, Button, Pagination, Typography } from "@mui/material";
import MenuWrapper from "../../../widgets/menu/ui/MenuWrapper";
import { useSelector } from "react-redux";
import { getHubsList } from "../../../entities/hubs/model/hubsSlice";
import { Link } from "react-router";
import { useState } from "react";

function Articles() {
  const hubs = useSelector(getHubsList);

  const [select, setSelect] = useState(0)

  const handleClick=(id)=>{
    setSelect(id)
  }

  const handlePageChange=(event,page)=>{
   


  }
 
  return (
    <Box
      sx={{
        display: "flex",
        flexDirection: "column",
        maxWidth: "1000px",
        margin: "0 auto",
      }}
    >
      <MenuWrapper>
        <Box
          sx={{ display: "flex", justifyContent: "flex-start",alignItems:"center", width: "100%" }}
        >
          <Button variant="text"  onClick={ ()=>handleClick(0)}>
            <Typography variant="body1" style={{textDecoration: select==0 ? "none" : "underline" }} >Моя лента</Typography>
          </Button>

          {hubs.map((item, index) => (
            <Button variant="text" key={index} onClick={()=>handleClick(item.id)}>
              <Typography variant="body2"  style={{textDecoration: select==item.id ? "none" : "underline" }} >{item.name}</Typography>
            </Button>
          ))}
        </Box>
      </MenuWrapper>
      <Box sx={{margin:"20px 0 20px 0"}} >

        {}

      </Box>
      <MenuWrapper>
      <Box sx={{

        width:"100%",
        display:"flex",
        justifyContent:"center"
      }} >
            <Pagination count={10} variant="outlined" color="primary" onChange={handlePageChange} />            
      </Box>
      </MenuWrapper>
    </Box>
  );
}

export default Articles;
