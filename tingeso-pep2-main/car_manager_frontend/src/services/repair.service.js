import httpClient from "../http-common"

const getAll = () => {
    return httpClient.get('/repair/');
}

const create = data => {
    return httpClient.post("/repair/",data);
}

const get = id => {
    return httpClient.get(`/repair/${id}`);
}

const getRepairsByCar = id => {
    return httpClient.get(`/repair/car/${id}`);
}

const update = data => {
    return httpClient.put('/repair/',data);
}

const remove = id => {
    return httpClient.delete(`/repair/${id}`);
}
export default { getAll, create, get, update, remove, getRepairsByCar };