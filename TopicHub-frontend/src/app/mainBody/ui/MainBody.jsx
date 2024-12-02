import { Box } from "@mui/material";

function MainBody({ children }) {
  return (
    <Box sx={{padding:"10px 0 0 0"}} >
      {children}
    </Box>
  );
}

export default MainBody;