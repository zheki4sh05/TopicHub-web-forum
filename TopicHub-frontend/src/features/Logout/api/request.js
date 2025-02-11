import DomainNames from "../../../app/store/DomainNames";
import api from "../../../app/util/apiPath";
import ApiRequestCreator from "../../../app/util/requestFactory";



const apiFactory = new ApiRequestCreator(DomainNames.authors, api.token.logout);
export const logout = apiFactory.createPostRequest("");