package com.beomju.minipj.todo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TodoDTO {
    private Long tno;
    private String title;
    private String writer;
    private LocalDateTime regDate;
    private LocalDateTime modDate;
}
