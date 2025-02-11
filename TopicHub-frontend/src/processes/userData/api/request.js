import DomainNames from "../../../app/store/DomainNames";
import api from "../../../app/util/apiPath";
import ApiRequestCreator from "../../../app/util/requestFactory";


const apiFactory = new ApiRequestCreator(DomainNames.user+"/cookie", api.auth.url);
export const checkCookie = apiFactory.createGetRequest(api.auth.signin,false);

const apiFactory2 = new ApiRequestCreator(DomainNames.user, api.profile.url);
export const userData = apiFactory2.createGetRequest("",true);


export async function refreshToken(token) {
    const url = api.token.url
    try {
        const response = await fetch(url, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${token}`
            },
        });

        if (!response.ok) {
            console.error('Ошибка HTTP:', response.statusresponse.status);
        }

        const responseData = await response.json();
        return responseData;
    } catch (error) {
        console.error('Ошибка при отправке POST запроса:', error);
        
    }
}