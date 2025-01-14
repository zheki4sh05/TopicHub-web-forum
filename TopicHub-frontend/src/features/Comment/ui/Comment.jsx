import { Box, IconButton, Stack, TextField, Typography } from "@mui/material";
import ReactionBox from "../../../shared/ReactionBox/ui/ReactionBox";
import { useDispatch, useSelector } from "react-redux";
import { getUser, isAuth } from "../../../pages/Profile/model/userSlice";
import { useState } from "react";
import EditIcon from "@mui/icons-material/Edit";
import CloseIcon from "@mui/icons-material/Close";
import SaveIcon from "@mui/icons-material/Save";
import { deleteComment, updateComment } from "../../../widgets/comments/api/requests";
import DeleteIcon from '@mui/icons-material/Delete';
import Author from "../../../widgets/author/ui/Author";
function Comment({ item, handleComment, level=0 }) {
    const dispatch = useDispatch()
  const [update, setUpdate] = useState(false);
  const [value, setValue] = useState(item.value);

  const user = useSelector(getUser);
  const auth = useSelector(isAuth);

  const handleEdit = () => {
    setUpdate(true);
  };
  const handleClose = () => {
    setUpdate(false);
    setValue(item.value)
  };
  const handleSave = () => {

    dispatch(
        updateComment({
            id:item.id,
            articleId:item.articleId,
            value:value
        })
    )
    setUpdate(false);

  };

  const handleDelete=()=>{
    dispatch(
        deleteComment({
            commentId:item.id
        })
    )
  }

  

  return (
    <>
     <Box sx={{ display: "flex", flexDirection: "column", marginLeft:level*10+"px" }}>
      <Stack direction="row" spacing={1}>
        <Author
        
        user={item.authorDto}
        size={50}
        />
        
      </Stack>

      {auth && user.id == item.authorDto.id ? (
        <>
          <Box sx={{ display: "flex", justifyContent: "space-between" }}>
            <Box sx={{ flex: 1 }}>
              {update ? (
                <TextField
                  id="standard-multiline-static"
                  label="Введите новый комментарий"
                  multiline
                  rows={4}
                  onChange={(event) => setValue(event.target.value)}
                  defaultValue={value}
                  variant="standard"
                  sx={{ width: "100%" }}
                />
              ) : (
                <Typography variant="body2">{item.value}</Typography>
              )}
            </Box>
            <Box>
              {update ? (
                <Box
                  sx={{ display: "flex", flexDirection: "column", gap: "10px" }}
                >
                  <IconButton onClick={handleClose}>
                    <CloseIcon />
                  </IconButton>
                  <IconButton onClick={handleSave} disabled={value==item.value}>
                    <SaveIcon />
                  </IconButton>
                </Box>
              ) : (
                <IconButton onClick={handleEdit}>
                  <EditIcon />
                </IconButton>
              )}
                <IconButton onClick={handleDelete}>
                  <DeleteIcon />
                </IconButton>
            </Box>
          </Box>
        </>
      ) : (
        <Box>
          <Typography variant="body2">{item.value}</Typography>
        </Box>
      )}

      <ReactionBox
        item={item}
        handleLike={() => {}}
        handleDislike={() => {}}
        handleComment={(event) => handleComment(item.id)}
        showDanger={true}
        showEdit={true}
        showLikes={false}
        showComment={true}
      />
    </Box>
      {

        item.replies ?
       
        item.replies.map((item, index) => (
            <Comment
              key={index}
              item={item}
              handleComment={handleComment}
              level={level+1}
            />
          ))

          :
          null
      }

    
    
    </>
   
  );
}

export default Comment;
