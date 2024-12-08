import httpClient from "../http-common"

const getAll = () => {
    return httpClient.get('/repair_price/');
}

const create = data => {
    return httpClient.post("/repair_price/",data);
}

const get = id => {
    return httpClient.get(`/repair_price/${id}`);
}

const getByType = type => {
    return httpClient.get(`/repair_price/type/${type}`);
}

const update = data => {
    return httpClient.put('/repair_price/',data);
}

const remove = id => {
    return httpClient.delete(`/repair_price/${id}`);
}
export default { getAll, create, get, getByType, update, remove };