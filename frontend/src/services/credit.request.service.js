import httpClient from "../http-common";

const getAll = () => {
    return httpClient.get('/api/v1/creditrequests/');
}

const update = data => {
    return httpClient.put('/api/v1/creditrequests/', data);
}

const get = id => {
    return httpClient.get(`/api/v1/creditrequests/${id}`);
}

const create = data => {
    return httpClient.post("/api/v1/creditrequests/", data);
}

const remove = id => {
    return httpClient.delete(`/api/v1/creditrequests/${id}`);
}

// Nuevo método para obtener las condiciones de préstamo según el tipo
const getLoanConditions = (loanType) => {
    return httpClient.get('/api/v1/creditrequests/loan-conditions', {
        params: { loanType }
    });
}

const evaluateRule = (id, rule) => {
    return httpClient.post(`/api/v1/creditrequests/${id}/evaluate/${rule}`);
}

// Método para subir documentos
const uploadDocuments = (id, formData) => {
    return httpClient.post(`/api/v1/creditrequests/${id}/documents`, formData, {
      headers: {
        "Content-Type": "multipart/form-data",
      },
    });
};

const downloadDocument = (id, docNumber) => {
    return httpClient.get(`/api/v1/creditrequests/${id}/document/${docNumber}`, {
      responseType: 'blob',
    });
};

const calculateTotalCost = (id, data) => {
    return httpClient.post(`/api/v1/creditrequests/${id}/calculateTotalCost`, data);
};

const updateStatus = (id, action) => {
    return httpClient.post(`/api/v1/creditrequests/${id}/status`, { action });
};

export default { getAll, update, create, remove, get, getLoanConditions, evaluateRule, uploadDocuments, downloadDocument, calculateTotalCost, updateStatus };
