package com.beomju.minipj.todo.service;

import com.beomju.minipj.common.dto.PageRequestDTO;
import com.beomju.minipj.common.dto.PageResponseDTO;
import com.beomju.minipj.todo.dto.TodoDTO;
import com.beomju.minipj.todo.entities.Todo;


public interface TodoService {
    Long add(TodoDTO dto, String mid);


    TodoDTO getOne(Long tno);


    //회원처리 하기전
    // PageResponseDTO<TodoDTO>  list(PageRequestDTO requestDTO);

    void modify(TodoDTO dto);

    void delete(Long tno);

    PageResponseDTO<TodoDTO> list(PageRequestDTO requestDTO, String mid);

}
