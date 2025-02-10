import DomainNames from "../../../app/store/DomainNames";
import api from "../../../app/util/apiPath";
import ApiRequestCreator from "../../../app/util/requestFactory";


const apiFactory2 = new ApiRequestCreator(DomainNames.hubs+"/create", api.hubs.url);
export const createHubs = apiFactory2.createPostRequest("");

const apiFactory4 = new ApiRequestCreator(DomainNames.hubs+"/del", api.hubs.url);
export const doDeleteHubs = apiFactory4.createDeleteRequest("",true);

const apiFactory5 = new ApiRequestCreator(DomainNames.hubs+"/update", api.hubs.url);
export const doUpdateHubs = apiFactory5.createPutRequest("");