


function getRequestConfig(lng){
    return {
       withCredentials: true,
        credentials: 'include',
        headers: 
        {
          'Content-Type': 'application/json', 
          'Accept-Language':lng
        },
    }
} 

export default getRequestConfig;


