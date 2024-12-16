import { Box, Button, Stack, Typography } from "@mui/material";
import MenuWrapper from "../../../widgets/menu/ui/MenuWrapper";
import { useState } from "react";
import Article from './../../../features/Article/ui/Article';

function Complaint({
  id,
  type,
  name,
  body,
  date,
  handleReject,
  handleDelete,
  article
}) {

  const [open,setOpen]  = useState(false)

  return (
    <>
      <MenuWrapper>
      <Stack direction={"column"} sx={{width:"100%"}}>
        <Stack direction={"row"} sx={{justifyContent:"space-between", alignItems:"center"}}>
          <Typography variant="h6">{name}</Typography>
          <Typography variant="body2" style={{ textTransform: "uppercase" }}>
            {type}
          </Typography>
        </Stack>
        <Stack direction={"row"}>
          <Typography variant="body2">{body}</Typography>
        </Stack>
        <Box sx={{ display: "flex", justifyContent: "space-between",width:"100%" }}>
          <Box sx={{ flex: 1 }}>
            <Typography>{date}</Typography>
          </Box>
          <Box
            sx={{
              display: "flex",
              justifyContent: "flex-end",
              gap: "15px",
              flex: 1,
            }}
          >
            <Button variant="contained" color="error" onClick={()=>handleReject(id)}>
              Отклонить
            </Button>

            {

              open ? 

              <Button variant="outlined" onClick={()=>{setOpen(false)}}>
              Закрыть
            </Button>
            :
            <Button variant="outlined" onClick={()=>{setOpen(true)}}>
          Открыть
          </Button>
          

            }
    
            <Button variant="contained" color="warning" onClick={()=>handleDelete(article.id)}>
              Удалить
            </Button>
          </Box>
        </Box>
      </Stack>
    </MenuWrapper>
   
    {

          open ? 
        <Box sx={{marginTop:"5px", marginBottom:"10px"}}>
 <Article
         item={article}
         mode={"short"}
         edit={false}
         handleDelete={()=>{}}
         handleEdit={()=>{}}

         
         />
        </Box>
        
          :
        null


          }
              
    </>
  
  );
}

export default Complaint;
