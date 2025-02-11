const ACCESS_NAME= 'topichub_access'
const REFRESH_NAME= 'topichub_refresh'

export function saveTokens(access, refresh) {
     localStorage.setItem(ACCESS_NAME, access);
     localStorage.setItem(REFRESH_NAME, refresh);
}
export function getAccessStorage(){
  return localStorage.getItem(ACCESS_NAME)
}
export function getRefreshStorage(){
    return localStorage.getItem(REFRESH_NAME)
  }

  export function deleteTokens(){
    localStorage.removeItem(ACCESS_NAME)
    localStorage.removeItem(REFRESH_NAME)
  }
