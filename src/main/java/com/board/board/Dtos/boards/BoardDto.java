package com.board.board.Dtos.boards;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BoardDto {
  private String slug;
  private String name;
  private String color;
  private boolean isShared;
  private boolean isArchived;
}
