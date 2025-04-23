import React, {type ChangeEvent, type FormEvent, useState} from "react";
import {getToken} from "~/api/memberAPI";
import {setCookie} from "~/util/cookieUtil";
import {useNavigate} from "react-router";
import {getKakaoLoginLink} from "~/api/kakaoAPI";


const LoginComponent = () => {
    const [mid, setMid] = useState("");
    const [mpw, setMpw] = useState("");

    const navigate = useNavigate();

    const handleSubmit = (e:FormEvent<HTMLFormElement>) => {
        e.preventDefault();
        // 여기에 로그인 로직 추가
        console.log("ID:", mid, "PW:", mpw);

        getToken(mid, mpw).then((res) => {
            const accessToken = res[0]
            const refreshToken = res[1]

            setCookie('access_token', accessToken,1)
            setCookie('refresh_token',  refreshToken, 7)


            navigate("/todo/list")
        })
            .catch((err)=>{
                console.error("로그인 실패",err)
                alert("아이디 또는 비밀번호가 틀렸습니다.")
            })
    };
    const kakaoLink = getKakaoLoginLink()

    return (
        <div className="min-h-screen flex items-center justify-center bg-gray-100">
            <div className="bg-gray-500/20 border-2 p-8 rounded-2xl shadow-lg w-full max-w-md">
                <h2 className="text-2xl font-bold mb-6 text-center">로그인</h2>
                <form onSubmit={handleSubmit} className="space-y-4">
                    <div>
                        <label htmlFor="mid" className="block text-sm font-medium text-gray-700">아이디 (mid)</label>
                        <input
                            id="mid"
                            type="text"
                            value={mid}
                            onChange={(e) => setMid(e.target.value)}
                            className="mt-1 w-full px-4 py-2 border rounded-xl focus:outline-none focus:ring-2 focus:ring-blue-400"
                            placeholder="아이디 입력"
                            required
                        />
                    </div>
                    <div>
                        <label htmlFor="mpw" className="block text-sm font-medium text-gray-700">비밀번호 (mpw)</label>
                        <input
                            id="mpw"
                            type="password"
                            value={mpw}
                            onChange={(e) => setMpw(e.target.value)}
                            className="mt-1 w-full px-4 py-2 border rounded-xl focus:outline-none focus:ring-2 focus:ring-blue-400"
                            placeholder="비밀번호 입력"
                            required
                        />
                    </div>
                    <button
                        type="submit"
                        className="w-full bg-blue-500 text-white py-2 rounded-xl hover:bg-blue-600 transition-colors"
                    >
                        로그인
                    </button>
                </form>
                <div className="mt-6 space-y-2 text-center">
                    <button
                        onClick={() => navigate("/member/join")}
                        className="text-sm text-blue-600 hover:underline"
                    >
                        아직 회원이 아니신가요? 회원가입
                    </button>
                    <div>
                        <a
                            href={kakaoLink}
                            className="text-sm text-yellow-600 hover:underline font-semibold"
                        >
                            🟡 카카오 로그인
                        </a>
                    </div>
                </div>
            </div>
        </div>
    );
};

export default LoginComponent;
