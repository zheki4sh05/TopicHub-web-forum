import { Box, IconButton, Typography } from "@mui/material";
import ThumbUpIcon from "@mui/icons-material/ThumbUp";
import ThumbDownIcon from "@mui/icons-material/ThumbDown";
import InsertCommentIcon from "@mui/icons-material/InsertComment";
import DangerousIcon from "@mui/icons-material/Dangerous";
function ReactionBox({ item, handleLike, handleDislike, handleComment, handleDanger, showDanger=false }) {
  return (
    <Box sx={{ display: "flex", justifyContent: "space-between" }}>
      <Box sx={{ display: "flex", justifyContent: "start", marginTop: "10px" }}>
        <IconButton onClick={handleLike}>
          <Typography variant="subtitle2" sx={{ marginRight: "5px" }}>
            {item.likes}
          </Typography>
          <ThumbUpIcon />
        </IconButton>
        <IconButton onClick={handleDislike}>
          <Typography variant="subtitle2" sx={{ marginRight: "5px" }}>
            {item.dislikes}
          </Typography>
          <ThumbDownIcon />
        </IconButton>
        <IconButton onClick={handleComment}>
          <Typography variant="subtitle2" sx={{ marginRight: "5px" }}>
            0
          </Typography>
          <InsertCommentIcon />
        </IconButton>
      </Box>

      {
            showDanger  ?

            <Box>
            <IconButton onClick={handleLike}>
                <Typography variant="caption" sx={{ marginRight: "5px" }}>
                 Пожаловаться
                </Typography>
                <DangerousIcon />
              </IconButton>
            </Box>
            :
            null


      }
     
    </Box>
  );
}

export default ReactionBox;
