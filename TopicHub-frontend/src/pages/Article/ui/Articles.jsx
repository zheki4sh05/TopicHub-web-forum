import { Box, Button, Typography } from "@mui/material";
import MenuWrapper from "../../../widgets/menu/ui/MenuWrapper";

function Articles() {
    return ( 
    <Box sx={{display:"flex", flexDirection:"column", maxWidth:"1000px",margin:"0 auto"}} >
        <MenuWrapper>
            <Box sx={{display:"flex",justifyContent:"space-between",width:"100%"}} >
                    <Typography variant="h6">Создание статьи</Typography>
                    <Button size="small" variant="outlined" color="error">Сбросить</Button>
            </Box>
           
        </MenuWrapper>
        
    </Box>
    
);
}

export default Articles;