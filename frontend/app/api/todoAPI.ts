import axios from "axios";
import type {ActionResult} from "~/types/common";

const host = "http://localhost:8080/api/v1/todos"

export async function addTodoForm(formData: FormData): Promise<ActionResult<number>>{
    const res = await axios.post(`${host}`, formData);
    console.log(res)

    return res.data
}

export async function TodoList(page:string, size:string){
    const res = await axios.get(`${host}/list?page=${page}&size=${size}`)

    return res.data
}

export async function getTodo(tno:number): Promise<Todo>{
    const res = await axios.get(`${host}/read/${tno}`);
    return res.data.data;
}

export async function modifyTodoForm(formData: FormData): Promise<ActionResult<number>>{

    const res = await axios.post(`${host}/modify`,formData);

    return res.data;
}