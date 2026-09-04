package com.board.board.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.board.board.Dtos.api.ApiResponse;
import com.board.board.Dtos.boards.BoardDto;
import com.board.board.Dtos.boards.NewBoard;
import com.board.board.Dtos.boards.UpdateBoardDto;
import com.board.board.Entity.Board;
import com.board.board.service.BoardService;

@RestController
@RequestMapping({"/api/board","/api/board/"})
public class BoardController {

  @Autowired
  BoardService service;

  @GetMapping({"/",""})
  public ResponseEntity<?> allBoards(){
    List<BoardDto> boardDtos = service.allBoards().stream()
                    .map(board -> new BoardDto(
                      board.getName(),
                      board.getSlug(),
                      board.getColor(),
                      board.isShared(),
                      board.isArchived()
                    )).toList();
    return ResponseEntity.ok(new ApiResponse<>("Board fetched successfully", boardDtos));
  }

  @GetMapping("/{id}")
  public Board oneBoard(@PathVariable String slug){
    return service.oneBoard(slug);
  }

  // specific user's board
  @GetMapping({"/user/{id}","/user/{id}/"})
  public ResponseEntity<?> getBoardsByUser(@PathVariable Long id) {
    List<Board> boards = service.getUserBoards(id);
    return ResponseEntity.ok(new ApiResponse<>("User board fetched", boards));
  }

  // online user's board
  @GetMapping({"/user/","/user"})
  public ResponseEntity<?> getAuthUser(@AuthenticationPrincipal UserDetails userDetails){
    String currentUserEmail = userDetails.getUsername();
    
    List<Board> boards = service.userBoard(currentUserEmail);

    return ResponseEntity.ok(new ApiResponse<>("User boards fetch successfully", boards));
  }

  @PostMapping
  public ResponseEntity<?> newBoard( @RequestBody NewBoard dto, @AuthenticationPrincipal UserDetails userdetails){
    try {
      String currentUserEmail = userdetails.getUsername();

      Board newBoard = service.createBoard(dto, currentUserEmail);

      return ResponseEntity.status(HttpStatus.CREATED)
                                      .body(new ApiResponse<>("Board is created", newBoard));
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse<>("It's an error during crateion", null));
    }
  }

  // update a board
  @PutMapping("/{id}")
  public ResponseEntity<?> updateBoard(
          @PathVariable Long id,
          @RequestBody UpdateBoardDto dto,
          @AuthenticationPrincipal UserDetails userDetails) {
      try {
          Board updatedBoard = service.updateBoard(id, dto, userDetails.getUsername());
          return ResponseEntity.ok(new ApiResponse<>("Board updated successfully", updatedBoard));
      } catch (RuntimeException e) {
          return ResponseEntity.status(HttpStatus.FORBIDDEN)
                  .body(new ApiResponse<>(e.getMessage(), null));
      }
  }


  // delete board
  @DeleteMapping({"/{id}/","{id}"})
  public void removeBoard(@PathVariable Long id){
    service.deleteBoard(id);
  }
}
