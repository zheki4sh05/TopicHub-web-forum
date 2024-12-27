import DomainNames from "../../../app/store/DomainNames";
import api from "../../../app/util/apiPath";
import ApiRequestCreator from "../../../app/util/requestFactory";


const apiFactory = new ApiRequestCreator(DomainNames.user+"/cookie", api.auth.url);
export const checkCookie = apiFactory.createGetRequest(api.auth.signin,false);

const apiFactory2 = new ApiRequestCreator(DomainNames.user, api.profile.url);
export const userData = apiFactory2.createGetRequest("",true);

