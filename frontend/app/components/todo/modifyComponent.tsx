import React, {type FormEvent, useRef} from 'react';
import {useNavigate, useParams} from "react-router";
import {useMutation, useQuery, useQueryClient} from "@tanstack/react-query";
import {getTodo, modifyTodoForm} from "~/api/todoAPI";
import LoadingComponent from "~/components/common/loadingComponent";
import ResultComponent from "~/components/common/resultComponent";

function TodoModifyComponent() {
    const {tno} = useParams();
    const navigate = useNavigate();
    //풀 참조형 ref객체
    const formRef = useRef<HTMLFormElement | null>(null);

    //리액트 쿼리 캐시 클라이언트
    const queryClient = useQueryClient();

    const query = useQuery(({
        queryKey: ["todo",tno],
        queryFn: () => getTodo(Number(tno)),
        enabled: !!tno,
        retry: false
    }))

    const {isLoading,data:todo} = query;

    const modifyMutation = useMutation({
        mutationFn: modifyTodoForm,
        onSuccess: () => {
            queryClient.invalidateQueries({queryKey: ["todos"]})
        }
    });

    const handleSubmit = (e: FormEvent<HTMLFormElement>)=>{
        e.preventDefault();
        const form = formRef.current;

        if(!form){
            return;
        }else{
            const formData = new FormData(form);
            modifyMutation.mutate(formData);
        }
    }

    if (isLoading) return <LoadingComponent isLoading={true} />;

    return (
        <div className="max-w-md mx-auto mt-10 p-6 bg-white shadow rounded-xl">
            <h2 className="text-2xl font-bold mb-4">✏️ 할 일 수정</h2>

            {/* 수정 폼 */}
            <form ref={formRef} onSubmit={handleSubmit} className="space-y-4">
                {/* 수정할 Todo 번호 hidden 필드로 전달 */}
                <input type="hidden" name="tno" value={todo?.tno} />

                <div>
                    <label className="text-gray-700">제목</label>
                    <input
                        defaultValue={todo?.title}
                        name="title"
                        className="w-full border rounded px-3 py-2"
                    />
                </div>

                <div>
                    <label className="text-gray-700">작성자</label>
                    <input
                        defaultValue={todo?.writer}
                        name="writer"
                        className="w-full border rounded px-3 py-2"
                        readOnly
                    />
                </div>

                <button
                    type="submit"
                    className="w-full py-2 bg-blue-600 text-white rounded hover:bg-blue-700"
                >
                    수정하기
                </button>
            </form>

            {/* 수정 완료 시 메시지 출력 */}
            {modifyMutation.isSuccess && (
                <ResultComponent
                    msg="수정 완료!"
                    closeFn={() => navigate(`/todo/list`)}
                />
            )}
        </div>
    );
}


export default TodoModifyComponent;