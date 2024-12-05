
function addParams(data){
    return `?${Object.entries(data).map(([key,value])=> `${key}=${value}`).join('&')}`
  }


  export default addParams;