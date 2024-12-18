import axios from "axios";

const prestabancoServer = import.meta.env.VITE_PRESTABANCO_SERVER;
const prestabancoPort = import.meta.env.VITE_PRESTABANCO_PORT;
console.log("Base URL:", `http://172.20.165.105:31553`);
export default axios.create({
    baseURL: `http://172.20.165.105:31553`,
    headers: {
        'Content-Type': 'application/json'
    },
    withCredentials: true
});