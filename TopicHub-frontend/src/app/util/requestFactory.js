import { createAsyncThunk } from "@reduxjs/toolkit";
import axios from "axios";
import getRequestConfig from "./requestConfig";
import addParams from "./paramsConfig";


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
          response = await axios.get(
            fullUrl.concat(addParams(initial)),
            getRequestConfig(thunkAPI.getState().settings.activeLanguage, 
            thunkAPI.getState().user.user.email,
            thunkAPI.getState().user.user.password
          )
          );
        } else {
          response = await axios.get(fullUrl, 
          
            getRequestConfig(thunkAPI.getState().settings.activeLanguage, 
            thunkAPI.getState().user.user.email,
            thunkAPI.getState().user.user.password
          )
          
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
          const response = await axios.post(
            this.url.concat(uri),
            initial,
            getRequestConfig(thunkAPI.getState().settings.activeLanguage, 
            thunkAPI.getState().user.user.email,
            thunkAPI.getState().user.user.password
          )
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
        const response = await axios.delete(
          fullUrl.concat(addParams(initial)),
          getRequestConfig(thunkAPI.getState().settings.activeLanguage, 
          thunkAPI.getState().user.user.email,
          thunkAPI.getState().user.user.password
        )
        );
        return response.data;
      });
    } else {
      return createAsyncThunk(this.domainName.concat(uri), async (initial,thunkAPI) => {
        const response = await axios.delete(
          fullUrl,
          initial,
          getRequestConfig(thunkAPI.getState().settings.activeLanguage, 
          thunkAPI.getState().user.email,
          thunkAPI.getState().user.password
        )
        );
        return response.data;
      });
    }
  }

  createPatchRequest(uri) {
    
    return createAsyncThunk(this.domainName.concat(uri), async (initial, thunkAPI) => {
      const response = await axios.patch(
        this.url.concat(uri),
        initial.data,
        getRequestConfig(thunkAPI.getState().settings.activeLanguage, 
        thunkAPI.getState().user.user.email,
            thunkAPI.getState().user.user.password
      )
      );
      return response.data;
    });
  }
  createPutRequest(uri) {
   
    return createAsyncThunk(
      this.domainName.concat(uri),
      async (initial, thunkAPI) => {
        try {
          const response = await axios.put(
            this.url.concat(uri),
            initial,
            getRequestConfig(thunkAPI.getState().settings.activeLanguage, 
            thunkAPI.getState().user.user.email,
            thunkAPI.getState().user.user.password
          )
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
