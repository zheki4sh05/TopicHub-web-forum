import { configureStore } from "@reduxjs/toolkit";
import DomainNames from "./DomainNames";
import sandboxSlice from './../../features/Sanbox/model/sandboxSlice';

export default configureStore({
    reducer:{
        [DomainNames.sandbox]:sandboxSlice,
    },
   
})