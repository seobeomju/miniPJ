package com.beomju.minipj.todo.entities;

import com.beomju.minipj.common.entities.BaseEntity;
import com.beomju.minipj.member.entities.Member;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tbl_todo")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
public class Todo extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tno;

    @Column(nullable = false, length =300)
    private String title;

    public void changeTitle(String newTitle) {
        this.title = newTitle;
    }

    @ManyToOne(fetch =  FetchType.LAZY)
    @JoinColumn(name = "writer", referencedColumnName = "mid")
    private Member member;
}
