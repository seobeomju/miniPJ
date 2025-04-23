import React, {useEffect} from 'react';
import {useNavigate, useSearchParams} from "react-router";
import {getAccessToken, getMemberWithAccessToken} from "~/api/kakaoAPI";
import {setCookie} from "~/util/cookieUtil";

function KakaoRedirect() {

    const [searchParams] = useSearchParams()
    const navigate = useNavigate();

    //code=xsdfsefsef
    const authCode = searchParams.get('code')

    useEffect(() => {

        if(authCode){
            getAccessToken(authCode).then(data => {
                const accessToken = data
                console.log("accessToken" ,accessToken)

                getMemberWithAccessToken(accessToken).then(loginResult => {
                    const accessToken = loginResult[0]
                    const refreshToken = loginResult[1]

                    setCookie("access_token",accessToken,1)
                    setCookie("refresh_token",refreshToken,7)

                    navigate("/todo/list")
                    console.log(loginResult)
                })
            })
        }

    },[authCode])


    if(!authCode) {
        alert (<div>로그인 실패</div>)
        navigate("/member/login")
    }

    return (
        <div>
            <div>이 페이지는 카카오에서 호출해 주는 페이지 입니다</div>
            <div>{authCode}</div>
        </div>
    );
}

export default KakaoRedirect;