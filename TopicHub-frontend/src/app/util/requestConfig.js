


function getRequestConfig(data){
    return {
       withCredentials: true,
        credentials: 'include',
        headers: 
        {
          'Content-Type': 'application/json', 
        },
       
        
        
   
    }

} 

export default getRequestConfig;