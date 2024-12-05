const  base="http://localhost:8080/";
const api={
    article:{
        url:base.concat("article"),
        hubs:"hubs/",
        feed:"",
        create:"",
        comment:"/comment"
    },
    hubs:{
        url:base.concat("hubs"),
        fetch:"",
    },
   
}

export default api;