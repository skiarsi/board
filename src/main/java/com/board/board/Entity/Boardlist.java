package com.board.board.Entity;

import java.time.LocalDate;


import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "lists")
public class Boardlist {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String title;

  private String color = "#dddddd";
  private boolean collapsed = false;
  private int position = 0;
  private boolean archived = false;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "board_id", nullable = false)
  @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
  @JsonIdentityReference(alwaysAsId = true)
  private Board board;

  @JsonProperty("board")
  public void setBoardById(Long boardId) {
      if (boardId != null) {
          this.board = new Board();
          this.board.setId(boardId);
      }
  }



  private LocalDate createdAt;
  private LocalDate updatedAt;


  @PrePersist
  protected void onCreate() {
    this.createdAt = LocalDate.now();
    this.updatedAt = LocalDate.now();
  }

  @PreUpdate
  protected void onUpdate(){
    this.updatedAt = LocalDate.now();
  }


}
