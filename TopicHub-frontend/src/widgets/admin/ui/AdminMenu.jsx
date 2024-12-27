import { Box, Typography } from "@mui/material";
import { PathConstants } from "../../../app/pathConstants";
import { Link } from "react-router";

function AdminMenu() {
    return ( 

        <Box sx={{display:"flex", flexDirection:"column", backgroundColor:"#2196F3", padding:"15px", borderRadius:"10px", width:"127px", position:"absolute",top:"100px", left:"40px"}} >
                <Link to={PathConstants.MANAGE_ARTICLES} sx={{textDecoration:"none"}}>
                    <Typography variant="body1" sx={{color:"white"}}>Статьи</Typography>
                </Link>
                <Link to={PathConstants.MANAGE_USER} sx={{textDecoration:"none"}}>
                    <Typography variant="body1" sx={{color:"white"}} >Пользователи</Typography>
                </Link>
                <Link to={PathConstants.MANAGE_HUBS} sx={{textDecoration:"none"}}>
                    <Typography variant="body1" sx={{color:"white"}} >Хабы</Typography>
                </Link>
        </Box>

     );
}

export default AdminMenu;