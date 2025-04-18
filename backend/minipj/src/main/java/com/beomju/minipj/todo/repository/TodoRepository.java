package com.beomju.minipj.todo.repository;

import com.beomju.minipj.todo.dto.TodoDTO;
import com.beomju.minipj.todo.entities.Todo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TodoRepository extends JpaRepository<Todo, Long> {

    //제목 검색 + 페이징
    @Query("select t from Todo t where t.title like %:title% ")
    Page<TodoDTO> listOfTitle(@Param("title") String title, Pageable pageable);

    //엔티티 없이 DTO 직접 반환
    @Query("select new com.beomju.minipj.todo.dto.TodoDTO(t.tno, t.title, t.writer, t.regDate, t.modDate) from Todo t where t.tno = :tno")
    TodoDTO selectDTO( @Param("tno") Long tno  );

    //모든 페이지 DTO 변환
    @Query("select new com.beomju.minipj.todo.dto.TodoDTO(t.tno, t.title, t.writer, t.regDate, t.modDate) from Todo t")
    Page<TodoDTO> getList(Pageable pageable);

    @Query("select t from Todo t where t.tno = :tno")
    Todo selectOne(@Param("tno") Long tno);
}
