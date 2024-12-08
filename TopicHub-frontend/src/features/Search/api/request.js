import DomainNames from "../../../app/store/DomainNames";
import api from "../../../app/util/apiPath";
import ApiRequestCreator from "../../../app/util/requestFactory";


const apiFactory = new ApiRequestCreator(DomainNames.search, api.search.url);
export const searchRequest = apiFactory.createGetRequest(api.search.search,true);

