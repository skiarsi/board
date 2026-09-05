package com.board.board.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.server.ResponseStatusException;

import com.board.board.Entity.Board;
import com.board.board.Entity.Boardlist;
import com.board.board.repository.BoardRepository;
import com.board.board.repository.BoardlistRepository;

@Service
public class BoardlistService {

  @Autowired
  BoardlistRepository repository;
  
  @Autowired
  BoardRepository boardRepository;

  // get a board's lists
  public List<Boardlist> getalllists(Long boardlist){
    return repository.findByBoardId(boardlist);
  }


  // new list
  public Boardlist createList(Long boardId, Boardlist listDetails, String currentUserEmail) {
      Board board = boardRepository.findById(boardId)
          .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Board not found"));

      if (!board.getUser().getEmail().equals(currentUserEmail)) {
          throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access denied");
      }

      listDetails.setBoard(board);
      return repository.save(listDetails);
  }

  // delete bord list
  public void deleteList(Long id, String currentUserEmail) {
    Boardlist list = repository.findByIdWithBoardAndUser(id)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "List not found"));

    if (!list.getBoard().getUser().getEmail().equals(currentUserEmail)) {
      throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access denied");
    }

    repository.delete(list);
  }

  // update border list
  public Boardlist updateList(Long id,Boardlist listDetails, String currentUserEmail ){
    Boardlist list = repository.findByIdWithBoardAndUser(id)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "List not found"));
    
    if (!list.getBoard().getUser().getEmail().equals(currentUserEmail)) {
      throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access denied");
    }

    if (listDetails.getTitle() != null) {
      list.setTitle(listDetails.getTitle());
    }
    if (listDetails.getColor() != null) {
      list.setColor(listDetails.getColor());
    }
    list.setCollapsed(listDetails.isCollapsed());
    list.setPosition(listDetails.getPosition());
    list.setArchived(listDetails.isArchived());

    return repository.save(list);
  }

}
