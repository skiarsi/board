package com.board.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.board.board.Entity.Board;

import jakarta.persistence.Entity;

@Entity
public interface BoardRepository extends JpaRepository<Board, Long> {
  Board findBySlug(String slug);
}
