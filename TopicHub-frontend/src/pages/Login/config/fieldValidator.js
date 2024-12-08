export function areAllFieldsFilled(obj) {
    for (let key in obj) {
        if (obj[key] === null || obj[key] === undefined || obj[key] === '') {
            return false;
        }
    }
    return true;
}

export function isValidLogin(value){
    const regex = /^[a-zA-Z_]+$/;
    return regex.test(value)
}


export function isValidPass(value){
    const regex = /^[a-zA-Z#!?0-9]+$/
    return regex.test(value)
}
