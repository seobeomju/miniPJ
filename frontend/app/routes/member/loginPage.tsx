import React from 'react';
import {Link} from "react-router";
import {getKakaoLoginLink} from "~/api/kakaoAPI";
import LoginComponent from "~/components/member/loginComponent";

function LoginPage() {

    const kakaoLink = getKakaoLoginLink()

    return (
        <div>

            <LoginComponent/>

            <Link to={kakaoLink}>카카오</Link>

        </div>
    );
}

export default LoginPage;