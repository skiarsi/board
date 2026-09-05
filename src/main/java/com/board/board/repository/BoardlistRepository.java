package com.board.board.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.board.board.Entity.Boardlist;

public interface BoardlistRepository extends JpaRepository<Boardlist, Long> {
  List<Boardlist> findByBoardId(Long boardid);

  @Query("SELECT l FROM Boardlist l JOIN FETCH l.board b JOIN FETCH b.user WHERE l.id = :id")
  Optional<Boardlist> findByIdWithBoardAndUser(@Param("id") Long id);
}
