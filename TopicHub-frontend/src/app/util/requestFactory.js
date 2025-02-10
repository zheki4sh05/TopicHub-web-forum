import { createAsyncThunk } from "@reduxjs/toolkit";
import getRequestConfig from "./requestConfig";
import addParams from "./paramsConfig";
import api from "./api";


class ApiRequestCreator {
  constructor(domainName, url) {
    this.domainName = domainName;
    this.url = url;
  }

  createGetRequest(uri, withParms = false) {
  
    let fullUrl = this.url.concat(uri);
    return createAsyncThunk(this.domainName.concat(uri), async (initial,thunkAPI) => {
        let response;
        if (withParms) {
          response = await api.get(
            fullUrl.concat(addParams(initial)),
            getRequestConfig(thunkAPI.getState().settings.activeLanguage)
          );
        } else {
          response = await api.get(fullUrl, 
          
            getRequestConfig(thunkAPI.getState().settings.activeLanguage)
          
          );
        }

        return response.data;
     
    });
  }

  createPostRequest(uri) {
   
    return createAsyncThunk(
      this.domainName.concat(uri),
      async (initial, thunkAPI) => {
    
        try {
          const response = await api.post(
            this.url.concat(uri),
            initial,
            getRequestConfig(thunkAPI.getState().settings.activeLanguage)
          );
          return response.data;
        } catch (error) {
          return thunkAPI.rejectWithValue({ error: error.response });
        }
      }
    );
  }

  createDeleteRequest(uri, withParms = false) {
   
    let fullUrl = this.url.concat(uri);
    if (withParms) {
      return createAsyncThunk(this.domainName.concat(uri), async (initial,thunkAPI) => {
        const response = await api.delete(
          fullUrl.concat(addParams(initial)),
          getRequestConfig(thunkAPI.getState().settings.activeLanguage)
        );
        return response.data;
      });
    } else {
      return createAsyncThunk(this.domainName.concat(uri), async (initial,thunkAPI) => {
        const response = await api.delete(
          fullUrl,
          initial,
          getRequestConfig(thunkAPI.getState().settings.activeLanguage)
        );
        return response.data;
      });
    }
  }

  createPatchRequest(uri) {
    
    return createAsyncThunk(this.domainName.concat(uri), async (initial, thunkAPI) => {
      const response = await api.patch(
        this.url.concat(uri),
        initial.data,
        getRequestConfig(thunkAPI.getState().settings.activeLanguage)
      );
      return response.data;
    });
  }
  createPutRequest(uri) {
   
    return createAsyncThunk(
      this.domainName.concat(uri),
      async (initial, thunkAPI) => {
        try {
          const response = await api.put(
            this.url.concat(uri),
            initial,
            getRequestConfig(thunkAPI.getState().settings.activeLanguage)
          );
          return response.data;
        } catch (error) {
          return thunkAPI.rejectWithValue({ error: error.response.data });
        }
      }
    );
  }
}

export default ApiRequestCreator;
