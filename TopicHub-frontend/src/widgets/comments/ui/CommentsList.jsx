import { Box, Typography } from "@mui/material";
import Comment from "../../../features/Comment/ui/Comment";
import { useState } from "react";

function CommentsList({article}) {

    const [comment,setComment] = useState({value:""})
    const [create,setCreate] = useState(false)

    const handleClickComment=(id)=>{
        setCreate(true)
    }

    return ( 
    <Box sx={{display:"flex", flexDirection:"column"}} >
        <Box  sx={{display:"flex", flexDirection:"row", padding:"0 0 12px 0", alignItems:"center"}}>

        <Typography variant="h6" >Комментарии</Typography>
        <Typography variant="body2" sx={{marginLeft:"20px"}}>Всего: 0</Typography>
        
        </Box>

        <Box sx={{display:"flex",flexDirection:"column"}} >
            <Comment 
                    item={{id:1,value:"сообщение"}}
                    handleComment={handleClickComment}
            />
        </Box>
    </Box>
    
);
}

export default CommentsList;