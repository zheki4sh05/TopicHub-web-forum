


function getRequestConfig(lng){
    
      return {

          headers: 
          {
            'Content-Type': 'application/json', 
            'Accept-Language':lng, 
          },
      }

  }

    


export default getRequestConfig;


