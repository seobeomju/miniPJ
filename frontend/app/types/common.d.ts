
export interface ActionResult<T>{
    result: string,
    data: T
}

export interface PaginationProps {
    page: number;
    size: number;
    start: number;
    end: number;
    prev: boolean;
    next: boolean;
    movePage: (page: number) => void;
}

export interface PageResponseDTO<T> {
    dtoList: T[];
    page: number;
    size: number;
    total: number;
    start: number;
    end: number;
    prev: boolean;
    next: boolean;
}
interface DeleteModalProps {
    onConfirm: () => void;
    onCancel: () => void;
}