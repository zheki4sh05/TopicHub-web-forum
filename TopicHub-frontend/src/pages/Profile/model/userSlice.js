import { createSlice } from "@reduxjs/toolkit";
import DomainNames from "../../../app/store/DomainNames";


//----state---
const initialState = {
  user:{

    roles:["USER", "ADMIN"]
  },
  status: "idle",
  error: null,
};
//-------------



const userSlice = createSlice({
  name: DomainNames.user,
  initialState,
  reducers: {

    
  },
  extraReducers(builder) {
    builder
    //   //---запрос статей-------------
    //   .addCase(fetchFeed.pending, (state, action) => {
    //     state.status = "loading";
    //   })
    //   .addCase(fetchFeed.fulfilled, (state, action) => {
    //     state.status = "succeeded";
    //     state.list = action.payload
    //     state.error=null
    //   })
    //   .addCase(fetchFeed.rejected, (state, action) => {
    //     state.status = "failed";
    //     state.error = action.error.message;
    //   })
    // //----------------------------------------

  },
});


export function getUser(state) {
  return state[DomainNames.user].user;
}
export function getStatus(state){
  return state[DomainNames.user].status;
}



export default userSlice.reducer;
