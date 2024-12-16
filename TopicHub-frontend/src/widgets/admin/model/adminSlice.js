import { createSlice } from "@reduxjs/toolkit";
import DomainNames from "../../../app/store/DomainNames";
import { blockAuthors, delAuthors, fetchAuthors } from "../../../pages/ManageAuthor/api/requests";
import { delComplaint, fetchComplaints, rejectComplaint } from "../api/requests";
// {
//     id:"1",
//     login:"login",
//     email:"email"
//   }

//----state---
const initialState = {
  authors: [],
  commentComplaints:[],
  articleComplaints:[],
  status: "idle",
  error: null,
};
//-------------



const adminSlice = createSlice({
  name: DomainNames.admin,
  initialState,
  reducers: {

  },
  extraReducers(builder) {
    builder
     //---запрос авторов-------------
     .addCase(fetchAuthors.pending, (state, action) => {
        state.status = "loading";
      })
      .addCase(fetchAuthors.fulfilled, (state, action) => {
        state.status = "succeeded";
        state.authors = action.payload
        state.error=null
      })
      .addCase(fetchAuthors.rejected, (state, action) => {
        state.status = "failed";
        state.error = action.error.message;
      })
    //----------------------------------------
    //---блокировка авторов-------------
    .addCase(blockAuthors.pending, (state, action) => {
        state.status = "loading";
      })
      .addCase(blockAuthors.fulfilled, (state, action) => {
        state.status = "succeeded";
        
        console.log(action.payload)

        let author = state.authors.find(item=>item.id==action.payload)
        state.authors.remove(author)
        author.status = !author.status
        state.authors.push(author)
        state.error=null
      })
      .addCase(blockAuthors.rejected, (state, action) => {
        state.status = "failed";
        state.error = action.error.message;
      })
    //----------------------------------------
    //---удаление авторов----------------
    .addCase(delAuthors.pending, (state, action) => {
        state.status = "loading";
      })
      .addCase(delAuthors.fulfilled, (state, action) => {
        state.status = "succeeded";
       
        state.authors = state.authors.filter(item=>item.id!=action.payload)

        state.error=null
      })
      .addCase(delAuthors.rejected, (state, action) => {
        state.status = "failed";
        state.error = action.error.message;
      })
    //----------------------------------------
     //---запрос жалоб-------------
     .addCase(fetchComplaints.pending, (state, action) => {
      state.status = "loading";
    })
    .addCase(fetchComplaints.fulfilled, (state, action) => {
      state.status = "succeeded";
      state.articleComplaints = action.payload
      state.error=null
    })
    .addCase(fetchComplaints.rejected, (state, action) => {
      state.status = "failed";
      state.error = action.error.message;
    })
  //----------------------------------------
       //---запрос жалоб-------------
       .addCase(rejectComplaint.pending, (state, action) => {
        state.status = "loading";
      })
      .addCase(rejectComplaint.fulfilled, (state, action) => {
        state.status = "succeeded";
        state.articleComplaints = state.articleComplaints.filter(item=>item.id!=action.payload)
        state.error=null
      })
      .addCase(rejectComplaint.rejected, (state, action) => {
        state.status = "failed";
        state.error = action.error.message;
      })
    //----------------------------------------
          //---запрос жалоб-------------
          .addCase(delComplaint.pending, (state, action) => {
            state.status = "loading";
          })
          .addCase(delComplaint.fulfilled, (state, action) => {
            state.status = "succeeded";
            state.articleComplaints = state.articleComplaints.filter(item=>item.articleDto.id!=action.payload)
            state.error=null
          })
          .addCase(delComplaint.rejected, (state, action) => {
            state.status = "failed";
            state.error = action.error.message;
          })
        //----------------------------------------
    
      
  },
});

export function getAuthors(state) {
    return state[DomainNames.admin].authors;
  }
  export function getArticleComplaints(state) {
    return state[DomainNames.admin].articleComplaints;
  }
  


export default adminSlice.reducer;
