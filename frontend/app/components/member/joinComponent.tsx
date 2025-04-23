import React, {useState} from "react";
import {postJoin} from "~/api/joinAPI";
import {useNavigate} from "react-router";

export default function JoinComponent() {
    const [mid, setMid] = useState("");
    const [mpw, setMpw] = useState("");
    const [email, setEmail] = useState("");
    const navigate = useNavigate();

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();
        try {
            await postJoin({mid, mpw, email});
            alert("회원가입 성공!");
            navigate("/member/login");
        } catch (err) {
            alert("회원가입 실패!");
        }
    };

    return (<div className="bg-gray-500/20 border-2 p-8 rounded-2xl shadow-lg w-full max-w-md">
        <form onSubmit={handleSubmit} className="p-4 space-y-4">

            <input placeholder="아이디" value={mid} onChange={(e) => setMid(e.target.value)}
                   className="border p-2 w-full"
                   required={true}
            />
            <input type="password" placeholder="비밀번호" value={mpw} onChange={(e) => setMpw(e.target.value)}
                   className="border p-2 w-full"
                   required={true}
            />
            <input placeholder="이메일" value={email} onChange={(e) => setEmail(e.target.value)}
                   className="border p-2 w-full"
                   required={true}
            />
            <button type="submit" className="w-full py-2 bg-blue-500 text-white rounded">회원가입</button>

        </form>
    </div>);
}
