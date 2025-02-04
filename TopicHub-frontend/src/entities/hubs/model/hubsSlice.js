import { createSlice } from "@reduxjs/toolkit";
import DomainNames from "../../../app/store/DomainNames";
import { createHubs, doDeleteHubs, doUpdateHubs } from "../api/request";
import { fetchHubs } from "../../../pages/Article/api/requests";
//----state---
const initialState = {
  list: [],
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
      //---запрос хабов-------------
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
      })
    //----------------------------------------
        //---создание хабов-------------
        .addCase(createHubs.pending, (state, action) => {
          state.status = "loading";
        })
        .addCase(createHubs.fulfilled, (state, action) => {
          state.status = "succeeded";
          state.list.push(action.payload) 
        })
        .addCase(createHubs.rejected, (state, action) => {
          state.status = "failed";
          state.error = action.error.message;
        })
      //----------------------------------------
        //---удаление хабов-------------
        .addCase(doDeleteHubs.pending, (state, action) => {
        state.status = "loading";
      })
      .addCase(doDeleteHubs.fulfilled, (state, action) => {
        state.status = "succeeded";
        state.list =  state.list.filter(item=>item.id!=action.payload)
      })
      .addCase(doDeleteHubs.rejected, (state, action) => {
        state.status = "failed";
        state.error = action.error.message;
      })
    //----------------------------------------
      //---изменение хабов-------------
      .addCase(doUpdateHubs.pending, (state, action) => {
      state.status = "loading";
    })
    .addCase(doUpdateHubs.fulfilled, (state, action) => {
      state.status = "succeeded";

      const { id, name } = action.payload; 
      const item = state.list.find(item => item.id === id); 
      if (item) { item.name = name; }
 
    })
    .addCase(doUpdateHubs.rejected, (state, action) => {
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
