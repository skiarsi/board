package com.board.board.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.board.board.Entity.Boardlist;
import com.board.board.service.BoardlistService;

@RestController
@RequestMapping({"/api/lists","/api/lists/"})
public class BoardlistController {

  @Autowired
  BoardlistService service;


  // get all board's list
  @GetMapping({"/board/{id}","/board/{id}/"})
  public List<Boardlist> boardslist(@PathVariable Long id){
    return service.getalllists(id);
  }

  // new board
  @PostMapping("/board/{boardId}")
  public Boardlist createList(@PathVariable("boardId") Long boardId, 
                              @RequestBody Boardlist list, 
                              Principal principal) {
      return service.createList(boardId, list, principal.getName());
  }


  @PutMapping({"/{id}", "/{id}/"})
  public Boardlist updateList(@PathVariable("id") Long id, @RequestBody Boardlist listDetails, Principal principal) {
    return service.updateList(id, listDetails, principal.getName());
  }

  @DeleteMapping({"/{id}", "/{id}/"})
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteList(@PathVariable("id") Long id, Principal principal) {
    service.deleteList(id, principal.getName());
  }
}
