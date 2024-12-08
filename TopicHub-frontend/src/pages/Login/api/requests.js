import DomainNames from "../../../app/store/DomainNames";
import api from "../../../app/util/apiPath";
import ApiRequestCreator from "../../../app/util/requestFactory";

const apiFactory = new ApiRequestCreator(DomainNames.user, api.auth.url);

export const signup = apiFactory.createPostRequest(api.auth.signup);

export const signin = apiFactory.createPostRequest(api.auth.signin);