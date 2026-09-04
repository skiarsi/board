package com.board.board.Dtos.boards;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdateBoardDto {
  private String slug;
  private String name;
  private String color;
  private boolean is_shared;
  private boolean is_archived;
}
