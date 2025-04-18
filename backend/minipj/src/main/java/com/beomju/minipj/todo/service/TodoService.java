package com.beomju.minipj.todo.service;

import com.beomju.minipj.common.dto.PageRequestDTO;
import com.beomju.minipj.common.dto.PageResponseDTO;
import com.beomju.minipj.todo.dto.TodoDTO;
import com.beomju.minipj.todo.entities.Todo;


public interface TodoService {
    Long add(TodoDTO dto);


    TodoDTO getOne(Long tno);

    PageResponseDTO<TodoDTO>  list(PageRequestDTO requestDTO);

    void modify(TodoDTO dto);

    void delete(Long tno);
}
