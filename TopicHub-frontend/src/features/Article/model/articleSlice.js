import { createSlice } from "@reduxjs/toolkit";
import DomainNames from "../../../app/store/DomainNames";
import { addBookmark, checkReactions, removeBookmark, subscribe, unsubscribe } from "../../../pages/ArticleView/api/requests";
import { complaintArticle } from "../../../shared/Complaint/api/requests";


//----state---
const initialState = {
  article:{},
  reaction:{},
  status: "idle",
  subscriptionStatus:'idle',
  bookmarkStatus:'idle',
  complaintStatus:'idle',
  error: null,
};
//-------------



const articleSlice = createSlice({
  name: DomainNames.article,
  initialState,
  reducers: {

      setArticle(state,action){
        state.article = action.payload
      },
      controlArticleStatus(state,action){
          state.status = action.payload
      },
      manageSubscriptionStatus(state,action){
        state.subscriptionStatus = action.payload
      },
      manageBookmarkStatus(state,action){
        state.bookmarkStatus = action.payload
      },
      manageComplaintStatus(state,action){
        state.complaintStatus = action.payload
      },
   
  },
  extraReducers(builder) {
    builder
       //---проверка подписки и добавления в закладки-------------
       .addCase(checkReactions.pending, (state, action) => {
        state.status = "loading";
      })
      .addCase(checkReactions.fulfilled, (state, action) => {
        state.status = "succeeded";
        state.reaction =action.payload
        state.error = null
      })
      .addCase(checkReactions.rejected, (state, action) => {
        state.status = "failed";
        state.error = action.error.message;
      })
    
    //----------------------------------------
     //---подписаться-------------
     .addCase(subscribe.pending, (state, action) => {
      state.subscriptionStatus = "loading";
    })
    .addCase(subscribe.fulfilled, (state, action) => {
      state.subscriptionStatus = "succeeded";
    state.reaction.isSubscribe = !state.reaction.isSubscribe
      state.error = null
    })
    .addCase(subscribe.rejected, (state, action) => {
      state.subscriptionStatus = "failed";
      state.error = action.error.message;
    })
  
  //----------------------------------------
      //--отписаться-------------
      .addCase(unsubscribe.pending, (state, action) => {
        state.subscriptionStatus = "loading";
      })
      .addCase(unsubscribe.fulfilled, (state, action) => {
        state.subscriptionStatus = "succeeded";
        state.reaction.isSubscribe = !state.reaction.isSubscribe
        state.error = null
      })
      .addCase(unsubscribe.rejected, (state, action) => {
        state.subscriptionStatus = "failed";
        state.error = action.error.message;
      })
    
     //----------------------------------------
     //--добавить закладку-------------
     .addCase(addBookmark.pending, (state, action) => {
      state.bookmarkStatus = "loading";
    })
    .addCase(addBookmark.fulfilled, (state, action) => {
      state.bookmarkStatus = "succeeded";
      state.reaction.isMarked = !state.reaction.isMarked
      state.error = null
    })
    .addCase(addBookmark.rejected, (state, action) => {
      state.bookmarkStatus = "failed";
      state.error = action.error.message;
    })
  
  // //----------------------------------------
    //--убрать закладку-------------
    .addCase(removeBookmark.pending, (state, action) => {
      state.bookmarkStatus = "loading";
    })
    .addCase(removeBookmark.fulfilled, (state, action) => {
      state.bookmarkStatus = "succeeded";
      state.reaction.isMarked = !state.reaction.isMarked
      state.error = null
    })
    .addCase(removeBookmark.rejected, (state, action) => {
      state.bookmarkStatus = "failed";
      state.error = action.error.message;
    })
  // //----------------------------------------
    //--создать жалобу-------------
    .addCase(complaintArticle.pending, (state, action) => {
      state.complaintStatus = "loading";
    })
    .addCase(complaintArticle.fulfilled, (state, action) => {
      state.complaintStatus = "succeeded";
      state.error = null
    })
    .addCase(complaintArticle.rejected, (state, action) => {
      state.complaintStatus = "failed";
      state.error = action.error.message;
    });
  // //----------------------------------------

    
   
  },
});



export function getArticle(state) {
  return state[DomainNames.article].article;
}
export function getArticleStatus(state) {
  return state[DomainNames.article].status;
}


export function getSubscriptionStatus(state) {
  return state[DomainNames.article].subscriptionStatus;
}
export function getBookmarksStatus(state) {
  return state[DomainNames.article].bookmarkStatus;
}
export function getComplaintStatus(state) {
  return state[DomainNames.article].complaintStatus;
}
export function getReactions(state) {
  return state[DomainNames.article].reaction;
}
export const { setArticle,manageBookmarkStatus,manageSubscriptionStatus,controlArticleStatus,manageComplaintStatus } = articleSlice.actions;

export default articleSlice.reducer;
