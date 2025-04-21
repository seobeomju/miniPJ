import {Link, useNavigate, useParams} from "react-router";
import {useMutation, useQuery, useQueryClient} from "@tanstack/react-query";
import {deleteTodo, getTodo} from "~/api/todoAPI";
import {useState} from "react";
import DeleteModal from "~/components/common/DeleteModal";


function TodoReadComponent() {
    //url에서 tno 파라미터를 가져옴
    const {tno} = useParams();
    //페이지 이동을 위한 훅
    const navigate = useNavigate()
    const queryClient = useQueryClient();

    const [showModal, setShowModal] = useState(false);

    //useQuery훅을 사용해 서버에서 Todo 데이터를 비동기로 조화
    const query = useQuery({
        queryKey: ['todo', tno], // 고유 키 (tno가 바귀면 새로 요청)
        queryFn: () => getTodo(Number(tno)), //실제로 데이터 들고오기
        enabled: !!tno, // tno가 있을 때만 쿼리를 실행
        retry: false //실패 시 자동 재시도 x
    });
    const {isLoading, data: todo, error} = query;

    if (isLoading) {
        return <div className="text-xl text-blue-500">불러오는 중...</div>;
    }

    if (error || !todo) {
        return <div className="text-xl text-red-500">할 일 정보를 불러오지 못했습니다.</div>;
    }
    ;

    const deleteMutaion = useMutation({
        mutationFn: deleteTodo,
        onSuccess: () => {
            queryClient.invalidateQueries({queryKey: ["todos"]});
            navigate("/todo/list")
        },
        onError: () => {
            alert("삭제 실패")
        }
    })


    console.log(todo)
    return (
        <>
            <div className="max-w-3xl mx-auto mt-10 p-6 bg-white shadow-lg rounded-xl">
                <h2 className="text-3xl font-bold mb-6">📝 할 일 상세</h2>

                {/* 제목, 작성자, 날짜 등 출력 */}
                <div className="text-xl font-semibold text-gray-800 mb-4">{todo.title}</div>
                <div className="text-sm text-gray-500 mb-1">작성자: {todo.writer}</div>
                <div className="text-sm text-gray-400 mb-1">
                    작성일: {new Date(todo.regDate).toLocaleString()}
                </div>
                <div className="text-sm text-gray-400 mb-6">
                    수정일: {new Date(todo.modDate).toLocaleString()}
                </div>

                {/* 수정/삭제/목록 버튼 */}
                <div className="flex gap-3">
                    <button>
                        <Link
                            to={`/todo/modify/${tno}`}
                            className="px-4 py-2 bg-blue-600 text-white rounded hover:bg-blue-700"
                        >
                            수정
                        </Link>
                    </button>
                    <button
                        onClick={() => setShowModal(true)}
                        className="px-4 py-2 bg-red-600 text-white rounded hover:bg-red-700"
                    >
                        삭제
                    </button>
                    <button>
                        <Link
                            to={`/todo/list`}
                            className="px-4 py-2 bg-gray-300 text-gray-800 rounded hover:bg-gray-400"
                        >
                            목록
                        </Link>
                    </button>
                </div>

            </div>
            {showModal && (
                <DeleteModal
                    onCancel={() => setShowModal(false)}
                    onConfirm={() => {
                        deleteMutaion.mutate(Number(tno));
                        setShowModal(false);
                    }}/>
            )}
        </>

    );
}

export default TodoReadComponent;