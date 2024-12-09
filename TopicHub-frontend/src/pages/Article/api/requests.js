import DomainNames from "../../../app/store/DomainNames";
import api from "../../../app/util/apiPath";
import ApiRequestCreator from "../../../app/util/requestFactory";


const apiFactory = new ApiRequestCreator(DomainNames.feed, api.article.url);

export const fetchFeed = apiFactory.createGetRequest(api.article.feed,true);


export const fetchHubs = apiFactory.createGetRequest(api.article.hubs,false);