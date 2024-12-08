import { createSlice } from "@reduxjs/toolkit";
import DomainNames from "../../../app/store/DomainNames";
import { signin, signup } from "../../Login/api/requests";
import { delUserArticle, fetchUserArticles } from "../api/requests";


//----state---
const initialState = {
  // user:{

  //   roles:["USER", "ADMIN"],
  //   id:"y8ofh234v9b34[v3",
  //   login:"evgeniy",
  //   email:"evgeniy.shostak.04@mail.ru",
  //   password:null
  // },

  user:{},

  articles:{
    articleDtoList:[],
    pageCount:0
  },
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
        state.errorCode = null
    }
    
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
      state.error = action.error;
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
  //----------------------------------------

  },
});


export function getUser(state) {
  return state[DomainNames.user].user;
}
export function getUserArticles(state) {
  return state[DomainNames.user].articles;
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

export const { controlUserStatus,controlResetError } = userSlice.actions;

export default userSlice.reducer;
