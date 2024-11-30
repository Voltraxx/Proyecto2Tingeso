import httpClient from "../http-common";

const calculateCredit = (loan, interest, payment_quantity) => {
    return httpClient.get('/api/v1/creditsimulation/calculateCredit', {params:{loan, interest, payment_quantity}});
} 

export default { calculateCredit };