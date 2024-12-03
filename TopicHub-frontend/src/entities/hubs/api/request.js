import DomainNames from "../../../app/store/DomainNames";
import api from "../../../app/util/apiPath";
import ApiRequestCreator from "../../../app/util/requestFactory";

const apiFactory = new ApiRequestCreator(DomainNames.hubs, api.hubs.url);
export const fetchHubs = apiFactory.createGetRequest(api.hubs.fetch,false);

