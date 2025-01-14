import { createSlice } from "@reduxjs/toolkit";
import DomainNames from "../../../app/store/DomainNames";
import { createArticle } from "../api/requests";
//----state---
const initialState = {
  id:"",
  theme:"",
  hub:null,
  keywords:[],
  list: [],
  components: [
    {
      id: 0,
      name: "Раздел",
      type: "part",
      value:""
     
    },
    {
      id: 1,
      name: "Aбзац",
      type: "paragraph",
      value:""
     
    },
    // {
    //   id: 2,
    //   name: "Cписок",
    //   type: "list",
    //   value:[]
    // },
    {
      id: 3,
      name: "Заголовок",
      type: "chapter",
      value:""
    },
    {
      id: 4,
      name: "Изображение",
      type: "img",
       value:""
     
    },
  ],
  status: "idle",
  error: null,
};
//-------------


const sandboxSlice = createSlice({
  name: DomainNames.sandbox,
  initialState,
  reducers: {
    setId(state,action){
      state.id = action.payload
  },
    saveTheme(state,action){
        state.theme = action.payload
    },
    saveItem(state, action) {

      const { created, value } = action.payload
      const existing = state.list.find(item => item.created === created)
      if (existing) {
        existing.value = value
      }else{
        state.list.push(action.payload);
      }
    },
    saveAllItems(state, action) {

      state.list=action.payload
    },
    setKeywords(state,action){
    
      state.keywords = action.payload
    },
    setHub(state,action){
      state.hub = action.payload
    },
    delItem(state,action){
      const { created } = action.payload
      state.list = state.list.filter(item=>item.created!==created)
    },
    resetSandBox(state,action){
      state.theme=""
      state.keywords=[]
      state.list = []
      state.status='idle'
      state.error=null

      
    },
    
  },
  extraReducers(builder) {
    builder
      //---создание статьи-------------
      .addCase(createArticle.pending, (state, action) => {
        state.status = "loading";
      })
      .addCase(createArticle.fulfilled, (state, action) => {
        state.status = "succeeded";
      })
      .addCase(createArticle.rejected, (state, action) => {
        state.status = "failed";
        state.error = action.error.message;
      });
    //----------------------------------------
  },
});

export function getSandboxId(state) {
  return state[DomainNames.sandbox].id;
}
export function getSandboxList(state) {
  return state[DomainNames.sandbox].list;
}
export function getSandboxWords(state) {
  return state[DomainNames.sandbox].keywords;
}
export function getHub(state) {
  return state[DomainNames.sandbox].hub;
}
export function getSandboxComponents(state) {
    return state[DomainNames.sandbox].components;
  }
  export function getSandboxStatus(state) {
    return state[DomainNames.sandbox].status;
  }
  export function getTheme(state) {
    return state[DomainNames.sandbox].theme;
  }

  export function isEmpty(state) {
    return state[DomainNames.sandbox].theme.trim().length!=0 && state[DomainNames.sandbox].list.length!=0 && state[DomainNames.sandbox].list[0].value.length!=0 && state[DomainNames.sandbox].hub!=null;
  }

export const { saveItem,delItem,saveTheme,setKeywords,resetSandBox,setHub, saveAllItems,setId } = sandboxSlice.actions;

export default sandboxSlice.reducer;
