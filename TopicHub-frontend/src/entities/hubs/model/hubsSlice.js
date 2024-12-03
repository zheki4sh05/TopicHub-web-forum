import { createSlice } from "@reduxjs/toolkit";
import DomainNames from "../../../app/store/DomainNames";
import { fetchHubs } from "../api/request";
//----state---
const initialState = {
  list: [{
    id:1,
    name:"hub1"
  },
  {
    id:2,
    name:"hub2"
  }



],
  status: "idle",
  error: null,
};
//-------------


const hubsSlice = createSlice({
  name: DomainNames.hubs,
  initialState,
  reducers: {

    
  },
  extraReducers(builder) {
    builder
      //---создание статьи-------------
      .addCase(fetchHubs.pending, (state, action) => {
        state.status = "loading";
      })
      .addCase(fetchHubs.fulfilled, (state, action) => {
        state.status = "succeeded";

        state.list = action.payload
       
      })
      .addCase(fetchHubs.rejected, (state, action) => {
        state.status = "failed";
        state.error = action.error.message;
      });
    //----------------------------------------
  },
});


export function getHubsList(state) {
  return state[DomainNames.hubs].list;
}


export default hubsSlice.reducer;
