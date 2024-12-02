import { createAsyncThunk } from "@reduxjs/toolkit";
import axios from "axios";


class ApiRequestCreator{

    constructor(domainName, url) {
        this.domainName = domainName;
        this.url = url;
      }

    createGetRequest(uri, withParms=false){

        let fullUrl = this.url.concat(uri)

        if(withParms){

              return  createAsyncThunk(this.domainName.concat(uri)  , async (initial) => {
                const response = await axios.get(fullUrl.concat(addParams(initialUser.data)),  initial);
                  return response.data
            })

        }else{
            return  createAsyncThunk(this.domainName.concat(uri)  , async (initial) => {
                const response = await axios.get(fullUrl,  initial);
                  return response.data
            })
        }

    }
    
    createPostRequest(uri){

        return  createAsyncThunk(this.domainName.concat(uri)  , async (initial) => {
            const response = await axios.post(this.url.concat(uri),initial.data,initial);
              return response.data
        })
    
    }
    
    createDeleteRequest(){
    
    }

    
    createPatchRequest(uri){
      return  createAsyncThunk(this.domainName.concat(uri)  , async (initial) => {
        const response = await axios.patch(this.url.concat(uri),initial.data,initial);
          return response.data
    })
    }

}

export default ApiRequestCreator;