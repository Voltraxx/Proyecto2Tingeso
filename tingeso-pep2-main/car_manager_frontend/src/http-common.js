import axios from "axios";

export default axios.create({
    //xsdsdsdsdsds
    baseURL: `http://localhost:8080`,
    headers: {
        'Content-Type': 'application/json'
    }
});
