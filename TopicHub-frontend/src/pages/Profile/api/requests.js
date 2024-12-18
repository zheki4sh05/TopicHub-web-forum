
import DomainNames from "../../../app/store/DomainNames";
import api from "../../../app/util/apiPath";
import ApiRequestCreator from "../../../app/util/requestFactory";


const apiFactory = new ApiRequestCreator(DomainNames.userArticles, api.profile.url);
export const fetchUserArticles = apiFactory.createGetRequest(api.profile.articles,true);

const apiFactor2 = new ApiRequestCreator(DomainNames.userArticles+"/del", api.profile.url);
export const delUserArticle = apiFactor2.createDeleteRequest(api.profile.articles,true);

const apiFactor3 = new ApiRequestCreator(DomainNames.user+"/update", api.profile.url);
export const updateUserData = apiFactor3.createPutRequest("");

const apiFactor4= new ApiRequestCreator(DomainNames.user+"/delete", api.profile.url);
export const deleteUser = apiFactor4.createDeleteRequest("",true);


const apiFactor5= new ApiRequestCreator(DomainNames.user+"/bookmarksList", api.bookmark.url);
export const fetchUserBookmarks = apiFactor5.createGetRequest("",true);

const apiFactor6= new ApiRequestCreator(DomainNames.subscription+"/list", api.subscription.url);
export const fetchUserSubscriptions = apiFactor6.createGetRequest("",true);

const apiFactor7= new ApiRequestCreator(DomainNames.followers+"/list", api.subscription.url);
export const fetchUserFollowers = apiFactor7.createGetRequest("",true);

const apiFactory8 = new ApiRequestCreator(DomainNames.article, api.article.url);
export const fetchAuthorArticles = apiFactory8.createGetRequest("",true);

const apiFactor9= new ApiRequestCreator(DomainNames.user, api.auth.url);
export const logoutUser = apiFactor9.createPostRequest(api.auth.logout);