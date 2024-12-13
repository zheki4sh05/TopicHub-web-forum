import DomainNames from "../../../app/store/DomainNames";
import api from "../../../app/util/apiPath";
import ApiRequestCreator from "../../../app/util/requestFactory";


const apiFactory = new ApiRequestCreator(DomainNames.authors, api.authors.url);
export const fetchAuthors = apiFactory.createGetRequest("",true);

const apiFactory2 = new ApiRequestCreator(DomainNames.authors+"/block", api.authors.url);
export const blockAuthors = apiFactory2.createPostRequest("");

const apiFactory3 = new ApiRequestCreator(DomainNames.authors+"/del", api.authors.url);
export const delAuthors = apiFactory3.createDeleteRequest("",true);