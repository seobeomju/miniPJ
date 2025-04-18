interface Todo{
    tno: number,
    title: string,
    writer: string,
    regDate? : Data | null,
    modDate? : Data | null,
}

interface TodoAdd{
    title: string,
    writer: string,
}