package com.beomju.minipj.todo.entities;

import com.beomju.minipj.common.entities.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tbl_todo")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
public class TodoEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tno;

    @Column(nullable = false, length =300)
    private String title;

    private String writer;

    public void changeTitle(String newTitle) {
        this.title = newTitle;
    }
}
