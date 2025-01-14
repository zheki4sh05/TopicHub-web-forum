import { configureStore } from "@reduxjs/toolkit";
import DomainNames from "./DomainNames";
import sandboxSlice from './../../features/Sanbox/model/sandboxSlice';
import hubsSlice from './../../entities/hubs/model/hubsSlice'
import feedSlice from "../../pages/Article/model/feedSlice";
import articleSlice from './../../features/Article/model/articleSlice';
import userSlice from './../../pages/Profile/model/userSlice';
import searchSlice from "../../features/Search/model/searchSlice";
import commentSlice from "../../widgets/comments/model/commentSlice";
import adminSlice from "../../widgets/admin/model/adminSlice"
import settingsSlice from "../../processes/header/model/settingsSlice";

export default configureStore({
    reducer:{
        [DomainNames.sandbox]:sandboxSlice,
        [DomainNames.hubs]:hubsSlice,
        [DomainNames.feed]:feedSlice,
        [DomainNames.article]:articleSlice,
        [DomainNames.user]:userSlice,
        [DomainNames.search]:searchSlice,
        [DomainNames.comment]:commentSlice,
        [DomainNames.admin]:adminSlice,
        [DomainNames.settings]:settingsSlice
    },
   
})