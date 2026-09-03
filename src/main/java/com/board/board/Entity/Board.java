package com.board.board.Entity;

import java.time.LocalDate;

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

@Entity
@Table(name = "boards")
public class Board {
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "slug", unique = true)
  private String slug;

  @Column(nullable = false)
  private String name;

  private String color="#dddddd";

  private boolean is_shared = false;

  private boolean is_archived = false;

  private LocalDate createdAt;

  private LocalDate updatedAt;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

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
