import DomainNames from "../../../app/store/DomainNames";
import api from "../../../app/util/apiPath";
import ApiRequestCreator from "../../../app/util/requestFactory";



const apiFactory = new ApiRequestCreator(DomainNames.complaint, api.complaint.url);
export const fetchComplaints = apiFactory.createGetRequest("",true);

const apiFactory2 = new ApiRequestCreator(DomainNames.complaint+"/reject", api.complaint.url);
export const rejectComplaint = apiFactory2.createDeleteRequest("",true);

const apiFactory3 = new ApiRequestCreator(DomainNames.complaint+"/delTarget", api.articles.url);
export const delComplaint = apiFactory3.createDeleteRequest("",true);
