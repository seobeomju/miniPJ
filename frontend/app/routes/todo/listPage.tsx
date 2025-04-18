import React from 'react';
import TodoListComponent from "~/components/todo/listComponent";
import PaginationComponent from "~/components/common/PaginationComponent";
import {data} from "react-router";

function TodoListPage() {


    return (
        <div>
            <TodoListComponent></TodoListComponent>

        </div>
    );
}

export default TodoListPage;