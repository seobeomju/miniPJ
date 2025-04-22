package com.beomju.minipj.member.repoitory;

import com.beomju.minipj.member.entities.Member;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface MemberRepository extends JpaRepository<Member, String> {

    @EntityGraph(attributePaths = {"roleSet"},type = EntityGraph.EntityGraphType.FETCH)
    @Query("select m from Member m where m.mid = :mid")
    Member selectOne(@Param("mid")String mid);

}
