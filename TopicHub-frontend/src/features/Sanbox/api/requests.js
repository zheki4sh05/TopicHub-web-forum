import DomainNames from "../../../app/store/DomainNames";
import api from "../../../app/util/apiPath";
import ApiRequestCreator from "../../../app/util/requestFactory";

const apiFactory = new ApiRequestCreator(DomainNames.sandbox, api.article.url);
export const createArticle = apiFactory.createPostRequest(api.article.create);