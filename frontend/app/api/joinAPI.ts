import axios from "axios";
const host="http://localhost:8080/api/v1/member/join"


export const postJoin = async (joinData: { mid: string; mpw: string; email: string }) => {
    const res = await axios.post(host, joinData);
    return res.data;
};