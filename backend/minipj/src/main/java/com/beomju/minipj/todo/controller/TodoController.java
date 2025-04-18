package com.beomju.minipj.todo.controller;

import com.beomju.minipj.common.dto.ActionResultDTO;
import com.beomju.minipj.todo.dto.TodoDTO;
import com.beomju.minipj.todo.service.TodoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/todos")
@Log4j2
@RequiredArgsConstructor
//@PreAuthorize("premitAll")
public class TodoController {

    private final TodoService todoService;

    @PostMapping("add")
    public ResponseEntity<ActionResultDTO<Long>> add(TodoDTO dto){
        log.info("----------POST-----------------");
        log.info(dto);

        Long tno = todoService.add(dto);

        return ResponseEntity.ok(
                ActionResultDTO.<Long>builder()
                        .result("success")
                        .data(tno)
                        .build()
        );
    }

}
