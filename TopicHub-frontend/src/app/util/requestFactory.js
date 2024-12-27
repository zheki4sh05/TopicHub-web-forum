import { createAsyncThunk } from "@reduxjs/toolkit";
import axios from "axios";
import getRequestConfig from "./requestConfig";
import addParams from "./paramsConfig";
import statusTypes from "./statusTypes";

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
            getRequestConfig()
          );
        } else {
          response = await axios.get(fullUrl, getRequestConfig());
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
            getRequestConfig()
          );
          return response.data;
        } catch (error) {
          return thunkAPI.rejectWithValue({ error: error.response.data });
        }
      }
    );
  }

  createDeleteRequest(uri, withParms = false) {
   
    let fullUrl = this.url.concat(uri);
    if (withParms) {
      return createAsyncThunk(this.domainName.concat(uri), async (initial) => {
        const response = await axios.delete(
          fullUrl.concat(addParams(initial)),
          getRequestConfig()
        );
        return response.data;
      });
    } else {
      return createAsyncThunk(this.domainName.concat(uri), async (initial) => {
        const response = await axios.delete(
          fullUrl,
          initial,
          getRequestConfig()
        );
        return response.data;
      });
    }
  }

  createPatchRequest(uri) {
    
    return createAsyncThunk(this.domainName.concat(uri), async (initial) => {
      const response = await axios.patch(
        this.url.concat(uri),
        initial.data,
        initial
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
            getRequestConfig()
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
