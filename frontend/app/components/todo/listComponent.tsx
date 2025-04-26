import {useQuery} from "@tanstack/react-query";
import {Link, Navigate, useNavigate, useSearchParams} from "react-router";
import {TodoList} from "~/api/todoAPI";
import PaginationComponent from "~/components/common/PaginationComponent";
import type {Todo} from "~/types/todo";

function TodoListComponent () {

    const [searchParams] = useSearchParams();
    const navigate = useNavigate();

    const pageStr = searchParams.get("page") || "1";
    const sizeStr = searchParams.get("size") || "10";

    console.log("pageStr: ", pageStr," sizeStr: ", sizeStr);

    const query = useQuery({
        queryKey: ['todos', pageStr, sizeStr],
        queryFn: () => TodoList(pageStr, sizeStr),
        staleTime: 10 * 60 * 1000,
        retry: false
    });

    const {isFetching, data, error } = query;

    if(error){
        return <Navigate to="/member/login" replace />;
    }
    const movePage = (page: number) => {
        navigate(`/todo/list?page=${page}&size=${sizeStr}`);

    };

    return (
        <div className="max-w-4xl mx-auto mt-10">
            <h2 className="text-4xl font-bold mb-6">📋 Todo List</h2>

            {isFetching && <div className="text-xl text-blue-500">불러오는 중...</div>}

            {!isFetching && data && data.dtoList.length === 0 && (
                <div className="text-gray-500 text-lg">등록된 할 일이 없습니다.</div>
            )}

            <ul className="space-y-4" >
                {data && data.dtoList.map((todo:Todo) => (
                    <li key={todo.tno}>
                        <Link
                            to={`/todo/read/${todo.tno}`}
                            className="p-5 bg-white rounded-lg shadow flex flex-col gap-2 border hover:border-blue-400 transition"
                            >
                            <div className="text-xl font-semibold text-gray-800">{todo.title}</div>
                            <div className="text-sm text-gray-500">작성자: {todo.member?.mid}</div>
                            <div className="text-sm text-gray-400">
                                작성일: {new Date(todo.regDate).toLocaleString()}
                            </div>
                        </Link>

                    </li>
                ))}
            </ul>

            {data && data.dtoList.length > 0 && (
                <PaginationComponent
                    page={data.page}
                    size={data.size}
                    start={data.start}
                    end={data.end}
                    prev={data.prev}
                    next={data.next}
                    movePage={movePage}
                />
            )}
        </div>
    );
}
export default TodoListComponent;
