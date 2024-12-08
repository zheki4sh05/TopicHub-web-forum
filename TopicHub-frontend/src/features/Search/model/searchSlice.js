import { createSlice } from "@reduxjs/toolkit";
import DomainNames from "../../../app/store/DomainNames";
import { searchRequest } from "../api/request";


//----state---
const initialState = {
  result: {


  },
  options:{
    theme:"",
    author:"",
    keywords:""
  },
  target:"",
  status: "idle",
  error: null,
};
//-------------



const searchSlice = createSlice({
  name: DomainNames.search,
  initialState,
  reducers: {

    cleareSearch(state,action){
        state.result={}
        state.options={
            theme:"",
            author:"",
            keyWords:""
          }
        state.status="idle"
        state.error=null

    },

    setSearchOptions(state,action){
      
        state.options=action.payload
    }
  },
  extraReducers(builder) {
    builder
    //---поиск статей-------------
    .addCase(searchRequest.pending, (state, action) => {
        state.status = "loading";
      })
      .addCase(searchRequest.fulfilled, (state, action) => {
        state.status = "succeeded";
        state.result = action.payload
        state.error=null
      })
      .addCase(searchRequest.rejected, (state, action) => {
        state.status = "failed";
        state.error = action.error.message;
      })
    //----------------------------------------
      
  },
});


export function getSearchResult(state) {
  return state[DomainNames.search].result;
}

export function getSearchOptions(state) {
    return state[DomainNames.search].options;
  }
  
export function getSearchStatus(state) {
  return state[DomainNames.search].status;
}
export function getSearchState(state) {
    return state[DomainNames.search].options.length ==0 && state[DomainNames.search].author.length==0 && state[DomainNames.search].keywords.length==0;
  }
export const { cleareSearch,setSearchOptions } = searchSlice.actions;

export default searchSlice.reducer;
