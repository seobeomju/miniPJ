package com.beomju.minipj.todo.service;

import com.beomju.minipj.todo.dto.TodoDTO;
import com.beomju.minipj.todo.entities.Todo;
import com.beomju.minipj.todo.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class TodoServiceImpl implements TodoService{

    private final TodoRepository repository;
    private final TodoRepository todoRepository;

    @Override
    public Long add(TodoDTO dto) {
        Todo todo = Todo.builder()
                .title(dto.getTitle())
                .writer(dto.getWriter())
                .build();
        repository.save(todo);

        log.info("Todo: ", todo );
        return todo.getTno();
    }

    @Override
    public TodoDTO getOne(Long tno) {


        return todoRepository.selectDTO(tno);
    }



}