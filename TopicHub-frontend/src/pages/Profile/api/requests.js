
import DomainNames from "../../../app/store/DomainNames";
import api from "../../../app/util/apiPath";
import ApiRequestCreator from "../../../app/util/requestFactory";


const apiFactory = new ApiRequestCreator(DomainNames.userArticles, api.profile.url);
export const fetchUserArticles = apiFactory.createGetRequest(api.profile.articles,true);

const apiFactor2 = new ApiRequestCreator(DomainNames.userArticles+"/del", api.profile.url);
export const delUserArticle = apiFactor2.createDeleteRequest(api.profile.articles,true);