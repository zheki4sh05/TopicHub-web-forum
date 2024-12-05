import { createSlice } from "@reduxjs/toolkit";
import DomainNames from "../../../app/store/DomainNames";


//----state---
const initialState = {
  article:{},
  status: "idle",
  error: null,
};
//-------------



const articleSlice = createSlice({
  name: DomainNames.article,
  initialState,
  reducers: {

      setArticle(state,action){
        state.article = action.payload
      }
  },
  extraReducers(builder) {
    builder
      
    //----------------------------------------
  },
});


export function getArticle(state) {
  return state[DomainNames.article].article;
}
export function getArticleStatus(state) {
  return state[DomainNames.article].status;
}
export const { setArticle } = articleSlice.actions;

export default articleSlice.reducer;
