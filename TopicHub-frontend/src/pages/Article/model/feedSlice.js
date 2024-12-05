import { createSlice } from "@reduxjs/toolkit";
import DomainNames from "../../../app/store/DomainNames";
import { fetchFeed, fetchHubs } from "../api/requests";

//----state---
const initialState = {
  list: {},
  hubs:[
    {
      id:0,
      name:"Моя страница",
    },
    {
    id:1,
    name:"hub1",
  }],
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
    //----------------------------------------
    //---запрос хабов-------------
    .addCase(fetchHubs.pending, (state, action) => {
      state.status = "loading";
    })
    .addCase(fetchHubs.fulfilled, (state, action) => {
      state.status = "succeeded";
      state.hubs = action.payload
      state.error=null
    })
    .addCase(fetchHubs.rejected, (state, action) => {
      state.status = "failed";
      state.error = action.error.message;
    });
  //----------------------------------------
  },
});


export function getFeed(state) {
  return state[DomainNames.feed].list;
}
export function getHubs(state){
  return state[DomainNames.feed].hubs;
}
export function getFeedStatus(state) {
  return state[DomainNames.feed].status;
}


export default feedSlice.reducer;
