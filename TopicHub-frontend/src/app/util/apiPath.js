const  base="http://localhost:8080/";
const api={
    article:{
        url:base.concat("article"),
        hubs:"hubs/",
        feed:"",
        comment:"/comment"
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
    }
    
   
   
}

export default api;