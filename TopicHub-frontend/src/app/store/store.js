import { configureStore } from "@reduxjs/toolkit";
import DomainNames from "./DomainNames";
import sandboxSlice from './../../features/Sanbox/model/sandboxSlice';
import hubsSlice from './../../entities/hubs/model/hubsSlice'
import feedSlice from "../../pages/Article/model/feedSlice";
import articleSlice from './../../features/Article/model/articleSlice';

export default configureStore({
    reducer:{
        [DomainNames.sandbox]:sandboxSlice,
        [DomainNames.hubs]:hubsSlice,
        [DomainNames.feed]:feedSlice,
        [DomainNames.article]:articleSlice
    },
   
})