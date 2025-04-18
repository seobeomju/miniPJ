package com.beomju.minipj.todo.controller;

import com.beomju.minipj.common.dto.ActionResultDTO;
import com.beomju.minipj.common.dto.PageRequestDTO;
import com.beomju.minipj.common.dto.PageResponseDTO;
import com.beomju.minipj.todo.dto.TodoDTO;
import com.beomju.minipj.todo.service.TodoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.coyote.Response;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClient;

import java.awt.print.Pageable;

@RestController
@RequestMapping("api/v1/todos")
@Log4j2
@RequiredArgsConstructor
//@PreAuthorize("premitAll")
public class TodoController {

    private final TodoService todoService;
    private final RestClient.Builder builder;

    @PostMapping("")
    public ResponseEntity<ActionResultDTO<Long>> add(@ModelAttribute TodoDTO dto){
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

    @GetMapping("read/{tno}")
    public ResponseEntity<ActionResultDTO<TodoDTO>> read(@PathVariable("tno") Long tno) {
        log.info("----------GET---------------");

        TodoDTO dto = todoService.getOne(tno);


        return ResponseEntity.ok(ActionResultDTO.<TodoDTO>builder()
                .result("success")
                .data(dto)
                .build());
    }

    @GetMapping("/list")
    public ResponseEntity<PageResponseDTO<TodoDTO>> list(PageRequestDTO pageRequestDTO){
        log.info("list: ", pageRequestDTO);

        PageResponseDTO<TodoDTO> responseDTO = todoService.list(pageRequestDTO);

        return ResponseEntity.ok(responseDTO);
    }

    @PostMapping("/modify")
    public ResponseEntity<ActionResultDTO<Long>> modify(TodoDTO todoDTO){

        todoService.modify(todoDTO);

        return ResponseEntity.ok(ActionResultDTO.<Long>builder()
                        .result("success")
                        .data(todoDTO.getTno())
                        .build());
    }

    @DeleteMapping("/{tno}")
    public ResponseEntity<ActionResultDTO<Long>> delete(@PathVariable("tno") Long tno){
        todoService.delete(tno);

        return ResponseEntity.ok(
                ActionResultDTO.<Long>builder()
                        .result("success")
                        .data(tno)
                        .build()
        );
    }
}
