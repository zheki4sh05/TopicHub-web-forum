import DomainNames from "../../../app/store/DomainNames";
import api from "../../../app/util/apiPath";
import ApiRequestCreator from "../../../app/util/requestFactory";


const apiFactory = new ApiRequestCreator(DomainNames.feed, api.article.url);

export const fetchFeed = apiFactory.createGetRequest(api.article.feed,true);

const apiFactory2 = new ApiRequestCreator(DomainNames.hubs, api.hubs.url);
export const fetchHubs = apiFactory2.createGetRequest("",false);