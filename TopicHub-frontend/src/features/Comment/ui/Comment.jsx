import { Box, Stack, Typography } from "@mui/material";
import ReactionBox from "../../../shared/ReactionBox/ui/ReactionBox";

function Comment({item, handleComment}) {

    const c = {
        likes:2,
        dislikes:2,
        comment:2
    }

    return (
    <Box sx={{display:"flex", flexDirection:"column"}} >
        <Stack direction="row" spacing={1}>
            <img alt="logo" />
            <Typography variant="subtitle2" >Имя автора</Typography>
        </Stack>
        <Box>

            <Typography variant="body2" >{item.value}</Typography>

        </Box>
        <ReactionBox
        
        item={c}
        handleLike={()=>{}}
        handleDislike={()=>{}}
        handleComment={(event)=>handleComment(item.id)}
        
        />
    </Box> 
    
);
}

export default Comment;