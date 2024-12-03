import { configureStore } from "@reduxjs/toolkit";
import DomainNames from "./DomainNames";
import sandboxSlice from './../../features/Sanbox/model/sandboxSlice';
import hubsSlice from './../../entities/hubs/model/hubsSlice'

export default configureStore({
    reducer:{
        [DomainNames.sandbox]:sandboxSlice,
        [DomainNames.hubs]:hubsSlice
    },
   
})