package com.beomju.minipj.todo.service;

import com.beomju.minipj.todo.dto.TodoDTO;


public interface TodoService {
    Long add(TodoDTO dto);


    TodoDTO getOne(Long tno);



}
