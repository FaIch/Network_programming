import axios from 'axios';

const compiler = axios.create({
    baseURL: "http://localhost:8090",
    headers: {
        "Content-Type": "application/json"
    }
})

export const compileCode = (code) => {
    return compiler.post("/compile", code);
}