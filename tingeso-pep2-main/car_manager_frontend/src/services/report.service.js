import httpClient from "../http-common"

const report1 = () => {
    return httpClient.get('/report/report1');
}

const report2 = monthYear => {
    return httpClient.get(`/report/report2/${monthYear}`);
}

export default { report1, report2 };