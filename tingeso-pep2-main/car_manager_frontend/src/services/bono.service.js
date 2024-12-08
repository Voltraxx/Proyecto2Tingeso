import httpClient from "../http-common"

const getAll = () => {
    return httpClient.get('/bono/');
}

const create = data => {
    return httpClient.post("/bono/",data);
}

const get = id => {
    return httpClient.get(`/bono/${id}`);
}

const getBonosByMarca = marca => {
    return httpClient.get(`/bono/marca/${marca}`);
}

const update = data => {
    return httpClient.put('/bono/',data);
}

const remove = id => {
    return httpClient.delete(`/bono/${id}`);
}
export default { getAll, create, get, getBonosByMarca, update, remove };