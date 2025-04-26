
export interface  Member{
    mid: string,
    email: string
}




interface Todo{
    tno: number,
    title: string,
    regDate? : Data | null,
    modDate? : Data | null,
    member?:Member;
}

interface TodoAdd{
    title: string,
    writer: string,
}

interface TodoReadProps {
    todo: Todo;
    onDelete: () => void;
    onModify: () => void;
}