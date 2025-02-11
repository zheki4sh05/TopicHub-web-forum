function getRequestImageConfig(token){
    return {

        headers: 
        {
          'Content-Type': 'image', 
           'Authorization': `Bearer ${token}`
        },
        responseType: 'arraybuffer',
       
    }
  
  } 
  export default getRequestImageConfig;