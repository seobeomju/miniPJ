package com.beomju.minipj.todo.service;

import com.beomju.minipj.common.dto.PageRequestDTO;
import com.beomju.minipj.common.dto.PageResponseDTO;
import com.beomju.minipj.todo.dto.TodoDTO;
import com.beomju.minipj.todo.entities.Todo;
import com.beomju.minipj.todo.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class TodoServiceImpl implements TodoService{

    private final TodoRepository repository;



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

        return repository.selectDTO(tno);
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponseDTO<TodoDTO> list(PageRequestDTO requestDTO) {

        Pageable pageable = PageRequest.of(
                requestDTO.getPage()-1,
                requestDTO.getSize()
        );

        Page<TodoDTO> reuslt = repository.getList(pageable);

        return PageResponseDTO.<TodoDTO>withAll()
                .pageRequestDTO(requestDTO)
                .dtoList(reuslt.getContent())
                .total((int) reuslt.getTotalElements())
                .build();
    }




}