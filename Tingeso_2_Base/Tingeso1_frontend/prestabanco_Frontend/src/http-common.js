import axios from "axios";

const prestabancoServer = import.meta.env.VITE_PRESTABANCO_SERVER;
const prestabancoPort = import.meta.env.VITE_PRESTABANCO_PORT;

export default axios.create({
    baseURL: `http://${prestabancoServer}:${prestabancoPort}`,
    headers: {
        'Content-Type': 'application/json'
    },
    withCredentials: true
});