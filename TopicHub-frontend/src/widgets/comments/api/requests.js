import DomainNames from "../../../app/store/DomainNames";
import api from "../../../app/util/apiPath";
import ApiRequestCreator from "../../../app/util/requestFactory";




const apiFactory = new ApiRequestCreator(DomainNames.comment, api.comment.url);
export const createComment = apiFactory.createPostRequest("");

const apiFactory2 = new ApiRequestCreator(DomainNames.answers, api.article.url);
export const fetchComments = apiFactory2.createGetRequest(api.article.answers,true);

const apiFactory3 = new ApiRequestCreator("comment/update", api.comment.url);
export const updateComment = apiFactory3.createPutRequest("");

const apiFactory4 = new ApiRequestCreator("comment/delete", api.comment.url);
export const deleteComment = apiFactory4.createDeleteRequest("",true);
