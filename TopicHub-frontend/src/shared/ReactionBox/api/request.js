import DomainNames from "../../../app/store/DomainNames";
import api from "../../../app/util/apiPath";
import ApiRequestCreator from "../../../app/util/requestFactory";



const apiFactory = new ApiRequestCreator(DomainNames.like, api.reactions.url);
export const makeReaction = apiFactory.createPostRequest(api.reactions.reaction);
export const removeReaction = apiFactory.createDeleteRequest(api.reactions.reaction,true);

