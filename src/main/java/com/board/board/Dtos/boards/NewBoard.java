package com.board.board.Dtos.boards;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class NewBoard {
  private String slug;
  private String name;
}
