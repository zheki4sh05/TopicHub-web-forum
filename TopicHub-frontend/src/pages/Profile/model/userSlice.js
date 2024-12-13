import { createSlice } from "@reduxjs/toolkit";
import DomainNames from "../../../app/store/DomainNames";
import { signin, signup } from "../../Login/api/requests";
import { delUserArticle, fetchAuthorArticles, fetchUserArticles, fetchUserBookmarks, fetchUserFollowers, fetchUserSubscriptions, updateUserData } from "../api/requests";


//----state---
const initialState = {
  // user:{

  //   roles:["USER", "ADMIN"],
  //   id:"y8ofh234v9b34[v3",
  //   login:"evgeniy",
  //   email:"evgeniy.shostak.04@mail.ru",
  //   password:null
  // },

  user:{
    roles:[],
    id:0,
    login:"",
    email:"",

  },
  activeUser:{
    roles:[],
    id:0,
    login:"",
    email:"",

  },
  articles:{
    articleDtoList:[],
    pageCount:0
  },
  bookMarksList:{
    articleDtoList:[],
    pageCount:0
  },
  subscribes:[],
  followers:[],
  // activeUser:{

  //   roles:["USER", "ADMIN"],
  //   id:"y8ofh234v9b34[v3",
  //   login:"evgeniy",
  //   email:"evgeniy.shostak.04@mail.ru",
  //   password:null
  // },
  others:{},
  status: "idle",

  auth:false,
  error: null,
};
//-------------



const userSlice = createSlice({
  name: DomainNames.user,
  initialState,
  reducers: {

    controlUserStatus(state,action){
      state.status = action.payload
    },
    controlResetError(state,action){
        state.error = null
        
    },
    setActiveUser(state,action){
      state.activeUser = action.payload
    },
    cleareUserData(state,action){
      state.activeUser = {}
      state.user = {}
      state.articles = {}
      state.auth = false
    },
    pushBookmark(state,action){
      state.bookMarksList.articleDtoList.push(action.payload)
    },
    deleteBookmark(state,action){
      state.bookMarksList.articleDtoList =  state.bookMarksList.articleDtoList.filter((item)=>item.id!=action.payload)
    },
  
    
  },
  extraReducers(builder) {
    builder
      //---регистрация-------------
      .addCase(signup.pending, (state, action) => {
        state.status = "loading";
      })
      .addCase(signup.fulfilled, (state, action) => {
        state.status = "succeeded";
        state.error=null
      })
      .addCase(signup.rejected, (state, action) => {
        state.status = "failed";
      
        state.error = action.payload.error;
    
      
      })
    //----------------------------------------
    //---авторизация-------------
    .addCase(signin.pending, (state, action) => {
      state.status = "loading";
    })
    .addCase(signin.fulfilled, (state, action) => {
      state.status = "succeeded";
      state.user = action.payload
      state.auth = true;
      state.error=null
    })
    .addCase(signin.rejected, (state, action) => {
      state.status = "failed";
      state.error = action.payload.error;
    })
  //----------------------------------------
    //---Пользовательские статьи-------------
    .addCase(fetchUserArticles.pending, (state, action) => {
      state.status = "loading";
    })
    .addCase(fetchUserArticles.fulfilled, (state, action) => {
      state.status = "succeeded";
      state.articles = action.payload
      state.error=null
    })
    .addCase(fetchUserArticles.rejected, (state, action) => {
      state.status = "failed";
      state.error = action.error;
    })
  //----------------------------------------
    //---Удаление пользовательской статьи-------------
    .addCase(delUserArticle.pending, (state, action) => {
      state.status = "loading";
    })
    .addCase(delUserArticle.fulfilled, (state, action) => {
      state.status = "succeeded";
      let articles = state.articles.articleDtoList

      state.articles.articleDtoList = articles.filter(item=>item.id!=action.payload)

      state.error=null
    })
    .addCase(delUserArticle.rejected, (state, action) => {
      state.status = "failed";
      state.error = action.error;
    })
      //---Обновление данных пользователя-------------
      .addCase(updateUserData.pending, (state, action) => {
        state.status = "loading";
      })
      .addCase(updateUserData.fulfilled, (state, action) => {
        state.status = "succeeded";
       
  
        state.activeUser.email = action.payload.email
        state.activeUser.login = action.payload.login

        state.user.email = action.payload.email
        state.user.login = action.payload.login

  
        state.error=null
      })
      .addCase(updateUserData.rejected, (state, action) => {
        state.status = "failed";
        state.error = action.payload.error;
      })
       //---закладки пользователя-------------
       .addCase(fetchUserBookmarks.pending, (state, action) => {
        state.status = "loading";
      })
      .addCase(fetchUserBookmarks.fulfilled, (state, action) => {
        state.status = "succeeded";
       
        state.bookMarksList = action.payload
  
        state.error=null
      })
      .addCase(fetchUserBookmarks.rejected, (state, action) => {
        state.status = "failed";
        state.error = action.error;
      })
        //---подписки пользователя-------------
        .addCase(fetchUserSubscriptions.pending, (state, action) => {
          state.status = "loading";
        })
        .addCase(fetchUserSubscriptions.fulfilled, (state, action) => {
          state.status = "succeeded";
          state.subscribes = action.payload
          state.error=null
        })
        .addCase(fetchUserSubscriptions.rejected, (state, action) => {
          state.status = "failed";
          state.error = action.error;
        })
          //---подписчики пользователя-------------
          .addCase(fetchUserFollowers.pending, (state, action) => {
            state.status = "loading";
          })
          .addCase(fetchUserFollowers.fulfilled, (state, action) => {
            state.status = "succeeded";
            state.followers = action.payload
            state.error=null
          })
          .addCase(fetchUserFollowers.rejected, (state, action) => {
            state.status = "failed";
            state.error = action.error;
          })
            //---статьи другого пользователя-------------
            .addCase(fetchAuthorArticles.pending, (state, action) => {
              state.status = "loading";
            })
            .addCase(fetchAuthorArticles.fulfilled, (state, action) => {
              state.status = "succeeded";
              state.articles = action.payload
              state.error=null
            })
            .addCase(fetchAuthorArticles.rejected, (state, action) => {
              state.status = "failed";
              state.error = action.error;
            })
   
 


  },
});


export function getUser(state) {
  return state[DomainNames.user].user;
}
export function getUserBookmarks(state) {
  return state[DomainNames.user].bookMarksList;
}
export function getUserArticles(state) {
  return state[DomainNames.user].articles;
}
export function getActiveUser(state){
  return state[DomainNames.user].activeUser;
}
export function isAuth(state) {
  return state[DomainNames.user].auth;
}
export function getUserStatus(state){
  return state[DomainNames.user].status;
}
export function getUserError(state){
  return state[DomainNames.user].error;
}
export function getUserSubscribes(state){
  return state[DomainNames.user].subscribes;
}
export function getUserFollowers(state){
  return state[DomainNames.user].followers;
}


export const { controlUserStatus,controlResetError, setActiveUser,cleareUserData,pushBookmark,deleteBookmark } = userSlice.actions;

export default userSlice.reducer;
