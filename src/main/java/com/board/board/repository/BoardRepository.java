package com.board.board.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.board.board.Entity.Board;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {
  Board findBySlug(String slug);

  List<Board> findByUserId(Long userId);

  List<Board> findByUserEmail(String email);
}
