
import { getRefresh, getToken, setRefresh, setToken } from "../../pages/Profile/model/userSlice";
import api from "./api";
import apiPath from "./apiPath";
import { saveTokens } from "./localstorageApi";
const MAX_RETRY_COUNT = 3; 

export const getState = store => store.getState();
const refreshUrl = apiPath.token.url

const hasKey=(obj)=>{
  const keys = Object.keys(obj);
  if ('Authorization' in obj) {
   return true
} else {
  return false
}
}

const applyInterceptors = (store) => {
  api.interceptors.request.use(
    (config) => {
      const state = getState(store);
      const token = getToken(state);
      if (token!="no") {
        if (!config.retryCount) {
          config.retryCount = 0;  
        }
      
        if(hasKey(config.headers) && config.headers['Authorization']!=`Bearer ${token}`){
          return config;
        }else{
          config.headers['Authorization'] = `Bearer ${token}`;
        }
      }
      
      return config;
    },
    (error) => {
      return Promise.reject(error);
    }
  );

  let count=0;

  api.interceptors.response.use(
    (response) => {
      return response;
    },
    async (error) => {
      const originalRequest = error.config;
      if (error.response.status === 401 && count < MAX_RETRY_COUNT) {
        originalRequest._retry = true;
        count += 1;
      
        try {
      
        
          const state = getState(store);
          const tokenRefresh = getRefresh(state);   
          const { data } = await api.post(refreshUrl,{}, {
            headers: {
              'Authorization': `Bearer ${tokenRefresh}` 
            }
          });
          count=0;
          saveTokens(data.access_token, data.refresh_token)
          store.dispatch(setToken(data.access_token))
          store.dispatch(setRefresh(data.refresh_token))
       
          return api(originalRequest);
        } catch (refreshError) {
          
          console.error('Ошибка обновления токена:', refreshError);
        
        }
      }
      return Promise.reject(error);
    }
  );
};

export { applyInterceptors };
