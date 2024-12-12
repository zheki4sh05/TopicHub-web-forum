function getRequestImageConfig(){
    return {
       withCredentials: true,
        credentials: 'include',
        headers: 
        {
          'Content-Type': 'image', 
        },
        responseType: 'arraybuffer'
    }
  
  } 
  export default getRequestImageConfig;