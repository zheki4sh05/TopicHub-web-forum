import DomainNames from "../../../app/store/DomainNames";
import api from "../../../app/util/apiPath";
import ApiRequestCreator from "../../../app/util/requestFactory";


const apiFactory = new ApiRequestCreator(DomainNames.reaction, api.reactions.url);
export const checkReactions = apiFactory.createGetRequest(api.reactions.check,true);

const apiFactory2 = new ApiRequestCreator(DomainNames.subscription, api.subscription.url);
export const subscribe = apiFactory2.createPostRequest("");

const apiFactory3 = new ApiRequestCreator(DomainNames.unsubscription, api.subscription.url);
export const unsubscribe = apiFactory3.createDeleteRequest("",true);



const apiFactory4 = new ApiRequestCreator(DomainNames.bookMarks, api.bookmark.url);
export const addBookmark = apiFactory4.createPostRequest("");

const apiFactory5 = new ApiRequestCreator(DomainNames.removeBookMarks, api.bookmark.url);
export const removeBookmark = apiFactory5.createDeleteRequest("",true);