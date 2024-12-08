import httpClient from "../http-common"

const getAll = () => {
    return httpClient.get('/repair_detail/');
}

const create = data => {
    return httpClient.post("/repair_detail/",data);
}

const get = id => {
    return httpClient.get(`/repair_detail/${id}`);
}

const getByIdRepair = id_repair => {
    return httpClient.get(`/repair_detail/id_repair/${id_repair}`);
}

const getByType = type => {
    return httpClient.get(`/repair_detail/type/${type}`);
}

const getByTypeAndCarType = (type, carType) => {
    return httpClient.get(`/repair_detail/type_car-type/${type}/${carType}`);
}

const update = data => {
    return httpClient.put('/repair_detail/',data);
}

const remove = id => {
    return httpClient.delete(`/repair_detail/${id}`);
}

export default { getAll, create, get, getByIdRepair, getByType, getByTypeAndCarType, update, remove };