import {useEffect, useState} from "react";

interface ResultComponentProps {
    msg: string,
    closeFn: () => void
}

export default function ResultComponent({msg, closeFn}: ResultComponentProps) {

    const [showFlag, setShowFlag] = useState(msg && true)

    // 자동 닫기 효과 (선택사항)
    useEffect(() => {
        const timer = setTimeout(() => {
            setShowFlag(false);
            closeFn();
        }, 5000); // 6초 후 자동 닫힘

        return () => clearTimeout(timer);
    }, [closeFn]);

    if (!showFlag) return null;

    return (
        <div className="fixed inset-0 z-50 flex items-center justify-center bg-gray-500/40 backdrop-blur-sm">
            <div className="w-96 bg-white p-8 rounded-2xl shadow-2xl border border-gray-300"
                 onClick={(e) => e.stopPropagation()}>
                <p className="text-xl font-bold text-gray-800 text-center mb-4">{msg}</p>
                <div className="flex justify-center">
                    <button
                        className="px-4 py-2 bg-blue-600 text-white rounded hover:bg-blue-700"
                        onClick={() => {
                            setShowFlag(false);
                            closeFn();
                        }}
                    >
                        확인
                    </button>
                </div>
            </div>
        </div>
    );
}
