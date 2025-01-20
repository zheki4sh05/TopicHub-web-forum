import { createSlice } from "@reduxjs/toolkit";
import DomainNames from "../../../app/store/DomainNames";

//----state---
const initialState = {
    languagesList:[{id:"1",name:"Русский",code:"ru"}, {id:"2",name:"English",code:"en"}],
  activeLanguage: "ru",
};
//-------------


const settingsSlice = createSlice({
  name: DomainNames.settings,
  initialState,
  reducers: {
        setLanguage(state,action){
            state.activeLanguage = action.payload;
        }
  },
});


export function getLanguagesList(state) {
  return state[DomainNames.settings].languagesList;
}
export function getActiveLanguage(state) {
    return state[DomainNames.settings].activeLanguage;
}

export const {setLanguage} = settingsSlice.actions

export default settingsSlice.reducer;
