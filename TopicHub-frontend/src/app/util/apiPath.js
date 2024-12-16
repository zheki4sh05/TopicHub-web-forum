const BACKEND_HOST = import.meta.env.VITE_APP_BACKEND_ADDRESS
const  base="http://"+BACKEND_HOST+"/";
const api={
    article:{
        url:base.concat("article"),
        hubs:"/hubs",
        feed:"",
        comment:"/comment",
    },
    hubs:{
        url:base.concat("hubs"),
        fetch:"",
    },
    auth:{
        url:base.concat("auth"),
        signup:"/signup",
        signin:"/signin",
    },
    profile:{
        url:base.concat("profile"),
        articles:""

    },
    sandbox:{
        url :base.concat("sandbox"),
        create:""
    },
    search:{
        url :base.concat("search"),
        search:""
    },
    reactions:{
        url:base.concat("reaction"),
        check:"",
        reaction:""
    },
    subscription:{
        url:base.concat("subscription"),
    },
    bookmark:{
        url:base.concat("bookmarks"),
    },
    comment:{
        url:base.concat("comment"),
    },
    answers:{
        url:base.concat("answers"),
    },
    image:{
        url:base.concat("image"),
    },
    authors:{
        url:base.concat("authors"),
    },
    complaint:{
        url:base.concat("complaint"),
    },
    articles:{
        url:base.concat("admin"),
    },


    
   
   
}

export default api;