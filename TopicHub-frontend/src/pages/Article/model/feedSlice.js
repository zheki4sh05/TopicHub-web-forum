import { createSlice } from "@reduxjs/toolkit";
import DomainNames from "../../../app/store/DomainNames";
import { fetchFeed, fetchHubs } from "../api/requests";
import { makeReaction, removeReaction } from "../../../shared/ReactionBox/api/request";
import { fetchAuthorArticles, fetchUserArticles, fetchUserBookmarks } from "../../Profile/api/requests";
import statusTypes from "../../../app/util/statusTypes";

//----state---
const initialState = {
  list: {

    items:[],
    page:0,
    total:0,
    maxPage:0

  },

  status: "idle",
  error: null,
};
//-------------



const feedSlice = createSlice({
  name: DomainNames.feed,
  initialState,
  reducers: {

    
  },
  extraReducers(builder) {
    builder
      //---запрос статей-------------
      .addCase(fetchFeed.pending, (state, action) => {
        state.status = "loading";
      })
      .addCase(fetchFeed.fulfilled, (state, action) => {
        state.status = "succeeded";
        state.list = action.payload
        state.error=null
      })
      .addCase(fetchFeed.rejected, (state, action) => {
        state.status = "failed";
        state.error = action.error.message;
    
      })
      //---оставить реакцию-------------
      .addCase(makeReaction.fulfilled, (state, action) => {
        
       const {targetId, value}= action.payload

       const article = state.list.items.find(item => item.id == targetId);
    
       if (article) { 
        if(value==1){
            article.likes = article.likes+1
            article.likeState = 1
        }else if(value==-1){
          article.dislikes = article.dislikes+1
          article.likeState = -1
        }
       }
       
      })
      //---убрать реакцию-------------
      .addCase(removeReaction.fulfilled, (state, action) => {
        const targetId= action.payload

        const article = state.list.articleDtoList.find(item => item.id == targetId);
 
        if (article) { 

          if(article.likeState==1){
            article.likes = article.likes-1
          }else{
            article.dislikes = article.dislikes-1
          }
          article.likeState = 0
        
        }

        
      })
          //---Пользовательские статьи-------------
          .addCase(fetchUserArticles.pending, (state, action) => {
            state.status = "loading";
          })
          .addCase(fetchUserArticles.fulfilled, (state, action) => {
            state.status = "succeeded";
            state.list = action.payload
            state.error=null
          })
          .addCase(fetchUserArticles.rejected, (state, action) => {
            state.status = "failed";
            state.list.articleDtoList=[]
            state.error = action.error;
          })
        //----------------------------------------
          //---закладки пользователя-------------
       .addCase(fetchUserBookmarks.pending, (state, action) => {
        state.status = "loading";
      })
      .addCase(fetchUserBookmarks.fulfilled, (state, action) => {
        state.status = "succeeded";
       
        state.list = action.payload
  
        state.error=null
      })
      .addCase(fetchUserBookmarks.rejected, (state, action) => {
        state.status = "failed";
        state.list.articleDtoList=[]
        state.error = action.error;
      })
        //---статьи другого пользователя-------------
        .addCase(fetchAuthorArticles.pending, (state, action) => {
          state.status = "loading";
        })
        .addCase(fetchAuthorArticles.fulfilled, (state, action) => {
          state.status = "succeeded";
          state.list = action.payload
          state.error=null
        })
        .addCase(fetchAuthorArticles.rejected, (state, action) => {
          state.status = "failed";
          state.error = action.error;
        })







  },
});


export function getFeed(state) {
  return state[DomainNames.feed].list;
}
export function getFeedStatus(state) {
  return state[DomainNames.feed].status;
}


export default feedSlice.reducer;
