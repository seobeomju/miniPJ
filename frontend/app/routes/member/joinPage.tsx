import React from "react";
import JoinComponent from "~/components/member/joinComponent";

export default function JoinPage() {
    return (
        <div className="min-h-screen flex items-center justify-center bg-gray-100">
            <div className="bg-white p-8 shadow-lg rounded-xl w-full max-w-md">
                <h2 className="text-2xl font-bold mb-6">회원가입</h2>
                <JoinComponent />
            </div>
        </div>
    );
}
