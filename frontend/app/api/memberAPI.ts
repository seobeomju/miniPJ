import axios from "axios";

const host = "http://localhost:8080/api/v1/member/login";

export const getToken = async (mid: string, mpw: string) => {

    const params = new URLSearchParams();
    params.append("uid", mid);
    params.append("upw", mpw);

    const res = await axios.post(host, params, {
        headers: {
            "Content-Type": "application/x-www-form-urlencoded"
        }
    });

    console.log(res.data);

    return res.data; // [accessToken, refreshToken]
};