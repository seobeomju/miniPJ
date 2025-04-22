import axios, {AxiosError, type AxiosRequestConfig, type AxiosResponse, type InternalAxiosRequestConfig} from "axios";
import {getCookie, setCookie} from "~/util/cookieUtil";

const jwtAxios = axios.create()

//before request
//요청 보내기 전에 추가 작업
const beforeReq = (config: InternalAxiosRequestConfig) => {
    console.log("before request.............")

    const accessToken = getCookie("access_token")
    config.headers.Authorization = `Bearer ${accessToken}`
    return config
}
//fail request
const requestFail = (err: AxiosError) => {
    console.log("request error............")

    return Promise.reject(err)
}

//before return response
//성공적인 응답이 왔을 때 추가 작업
const beforeRes = async (res: AxiosResponse): Promise<AxiosResponse> => {
    console.log("before return response...........")

    return res
}

//fail response
const responseFail = async (err: AxiosError) => {
    console.log("response fail error.............")
    console.log(err)

    // 401 unauthorized
    if (err.response?.status === 401) {
        const msg = getErrorMsg(err)

        if (msg === 'Expired token') {
            console.log("token expired so refreshing tokens")

            try {
                // 토큰을 갱신한 후 원래 요청을 다시 시도
                const newResponse = await refreshTokens(err.config)
                return newResponse
            } catch (refreshError) {
                console.log("Token refresh failed", refreshError)
            }
        }
    }

    return Promise.reject(err);
}

async function refreshTokens(originalConfig: AxiosRequestConfig | undefined) {
    const accessToken = getCookie("access_token");
    const refreshToken = getCookie("refresh_token");

    const header = {
        headers: {
            'Authorization': `Bearer ${accessToken}`,
            'Content-Type': 'application/x-www-form-urlencoded'
        }
    }

    // 토큰 갱신 요청
    const res = await axios.post(
        'http://localhost:8080/api/v1/member/refresh',
        { refreshToken },
        header
    )

    const newAccessToken = res.data[0]
    const newRefreshToken = res.data[1]

    setCookie("access_token", newAccessToken, 1)
    setCookie("refresh_token", newRefreshToken, 7)

    // 원래 요청의 Authorization 헤더를 새로운 토큰으로 수정
    if (originalConfig) {
        originalConfig.headers = {
            ...originalConfig.headers,
            Authorization: `Bearer ${newAccessToken}`,
        }

        // 원래 요청 재시도
        return axios(originalConfig)
    }
}

function getErrorMsg(err: AxiosError){
    const errorObj = err.response?.data as { error?: string }

    if (errorObj?.error) {
        const errorMsg: string = errorObj.error
        console.log("에러 메시지:", errorMsg)
        return errorMsg
    }
}

jwtAxios.interceptors.request.use( beforeReq, requestFail )

jwtAxios.interceptors.response.use( beforeRes, responseFail)

export default jwtAxios