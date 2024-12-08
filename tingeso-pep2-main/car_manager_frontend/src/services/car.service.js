import httpClient from "../http-common"

const getAll = () => {
    return httpClient.get('/car/');
}

const create = data => {
    return httpClient.post("/car/",data);
}

const get = id => {
    return httpClient.get(`/car/${id}`);
}

const getCarsByType = type => {
    return httpClient.get(`/car/tipo/${type}`);
}

const update = data => {
    return httpClient.put('/car/',data);
}

const remove = id => {
    return httpClient.delete(`/car/${id}`);
}
export default { getAll, create, get, getCarsByType, update, remove };