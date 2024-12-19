import DomainNames from "../../../app/store/DomainNames";
import api from "../../../app/util/apiPath";
import ApiRequestCreator from "../../../app/util/requestFactory";



const apiFactory = new ApiRequestCreator(DomainNames.like+"/add", api.reactions.url);
export const makeReaction = apiFactory.createPostRequest(api.reactions.reaction);
const apiFactory2 = new ApiRequestCreator(DomainNames.like+"/remove", api.reactions.url);
export const removeReaction = apiFactory2.createDeleteRequest(api.reactions.reaction,true);

