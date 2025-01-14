import { createSlice } from "@reduxjs/toolkit";
import DomainNames from "../../../app/store/DomainNames";
import { createComment, deleteComment, fetchComments, updateComment } from "../api/requests";

function addCommentFn(comments, parentId, newComment) {
 
    for (let comment of comments) {
  
      if (comment.id === parentId) {
        if(comment.replies==null){
          comment.replies=[]
        }
        comment.replies.push(newComment);
        return true; 
      }
  
 
      if (comment.replies.length > 0) {
        const found = addCommentFn(comment.replies, parentId, newComment);
        if (found) return true; 
      }
    }
  
    return false; 
  }

  function deleteCommentFn(comments, commentIdToDelete) {
    for (let i = 0; i < comments.length; i++) {
      const comment = comments[i];
      if (comment.id === commentIdToDelete) {
        comments.splice(i, 1);
        i--; 
      } 
      if (comment.replies && comment.replies.length > 0) {
        deleteCommentFn(comment.replies, commentIdToDelete);
      }
    }
  }

  function updateCommentFn(comments, commentIdToUpdate, newText) {
    for (let i = 0; i < comments.length; i++) {
      const comment = comments[i];
  
      
      if (comment.id === commentIdToUpdate) {
        comment.value = newText;
      }
  
      
      if (comment.replies && comment.replies.length > 0) {
        updateCommentFn(comment.replies, commentIdToUpdate, newText);
      }
    }
  }
  

  
  


//----state---
const initialState = {
    list:{},
    status: "idle",
    error: null,
  };
  //-------------
  
  
  
  const commentsSlice = createSlice({
    name: DomainNames.comment,
    initialState,
    reducers: {
        manageCommentStatus(state,action){
            state.status = action.payload
        }

    },
    extraReducers(builder) {
      builder
         //---создание коммента-------------
         .addCase(createComment.pending, (state, action) => {
          state.status = "loading";
        })
        .addCase(createComment.fulfilled, (state, action) => {
          state.status = "succeeded";

          if(action.payload.parentId==null){
            state.list.push(action.payload)
          }else{
            addCommentFn(state.list,action.payload.parentId,action.payload )
          }

          state.error = null
        })
        .addCase(createComment.rejected, (state, action) => {
          state.status = "failed";
          state.error = action.error.message;
        })
          //---запрос комментариев для статьи------------
        .addCase(fetchComments.pending, (state, action) => {
          state.status = "loading";
        })
        .addCase(fetchComments.fulfilled, (state, action) => {
          state.status = "succeeded";

          state.list = action.payload.filter(item=>item!=null)

          state.error = null
        })
        .addCase(fetchComments.rejected, (state, action) => {
          state.status = "failed";
          state.error = action.error.message;
        })
         //---обновление комментария------------
         .addCase(updateComment.pending, (state, action) => {
          state.status = "loading";
        })
        .addCase(updateComment.fulfilled, (state, action) => {
          state.status = "succeeded";

           let updatedComment = action.payload

  
          updateCommentFn(state.list, updatedComment.id, updatedComment.value)


          state.error = null
        })
        .addCase(updateComment.rejected, (state, action) => {
          state.status = "failed";
          state.error = action.error.message;
        })
          //---удаление комментария------------
          .addCase(deleteComment.pending, (state, action) => {
            state.status = "loading";
          })
          .addCase(deleteComment.fulfilled, (state, action) => {
            state.status = "succeeded";
  
            deleteCommentFn(state.list, action.payload)
  
            state.error = null
          })
          .addCase(deleteComment.rejected, (state, action) => {
            state.status = "failed";
            state.error = action.error.message;
          })
      
      
     
    },
  });
  

  export function getComments(state) {
    return state[DomainNames.comment].list;
  }

  export function getStatusComments(state) {
    return state[DomainNames.comment].status;
  }

  export const { manageCommentStatus} = commentsSlice.actions;
  
  export default commentsSlice.reducer;