


function getRequestConfig(lng,login,password){
  if(login.length>0 && password.length>0){
    return {
        headers: 
        {
          'Content-Type': 'application/json', 
          'Accept-Language':lng, 
        'Authorization': `Basic ${btoa(`${login}:${password}`)}`
        },
     } }
    
    else{
      return {

          headers: 
          {
            'Content-Type': 'application/json', 
            'Accept-Language':lng, 
          },
      }
    }
  }

    


export default getRequestConfig;


