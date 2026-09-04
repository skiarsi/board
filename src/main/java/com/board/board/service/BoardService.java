package com.board.board.service;

import java.util.List;

import javax.management.RuntimeErrorException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.board.board.Dtos.boards.NewBoard;
import com.board.board.Dtos.boards.UpdateBoardDto;
import com.board.board.Entity.Board;
import com.board.board.Entity.User;
import com.board.board.repository.BoardRepository;
import com.board.board.repository.userRepository;

@Service
public class BoardService {


  @Autowired
  BoardRepository repository;

  @Autowired
  userRepository userRepository;


  public List<Board> allBoards(){
    return repository.findAll();
  }


  public Board oneBoard(String slug){
    return repository.findBySlug(slug);
  }

  public void deleteBoard(Long id){
    repository.deleteById(id);
  }

  public List<Board> getUserBoards(Long id){
    return repository.findByUserId(id);
  }

  public List<Board> userBoard(String email){
    return repository.findByUserEmail(email);
  }


  public Board createBoard(NewBoard boardDto, String userEmail){
    User user = userRepository.findByEmail(userEmail).orElseThrow(()-> new UsernameNotFoundException("User not found"));

    Board board = new Board();

    board.setSlug(boardDto.getSlug());
    board.setName(boardDto.getName());
    board.setUser(user);

    return repository.save(board);
  }

  // update a board
  public Board updateBoard(Long id, UpdateBoardDto dto, String currenUserEmail){
    Board board = repository.findById(id)
            .orElseThrow(() -> new RuntimeException("Board not found"));

    if(!board.getUser().getEmail().equals(currenUserEmail)) {
      throw new RuntimeException("You do not have permission to update this board");
    }

    if(dto.getName() != null) board.setName(dto.getName());
    if(dto.getSlug() != null) board.setSlug(dto.getName().toLowerCase().replace(" ","-"));
    if(dto.getColor() != null) board.setColor(dto.getColor());

    board.setArchived(dto.is_archived());
    board.setShared(dto.is_shared());


    return repository.save(board);
  }


  // delete a board
  public void removeBoard(Long id){
    repository.deleteById(id);
  }
}
