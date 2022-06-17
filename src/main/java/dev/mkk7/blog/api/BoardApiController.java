package dev.mkk7.blog.api;

import dev.mkk7.blog.config.auth.PrincipalDetail;
import dev.mkk7.blog.dto.ReplySaveRequestDto;
import dev.mkk7.blog.dto.ResponseDto;
import dev.mkk7.blog.model.Board;
import dev.mkk7.blog.model.Reply;
import dev.mkk7.blog.model.User;
import dev.mkk7.blog.repository.BoardRepository;
import dev.mkk7.blog.service.BoardService;
import dev.mkk7.blog.service.UserService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
public class BoardApiController {

    @Autowired
    private BoardService boardService;
    

    @PostMapping("/api/board")
    public ResponseDto<Integer> save(@RequestBody Board board,
                                     @AuthenticationPrincipal PrincipalDetail principal) {
        System.out.println("board.getContent() = " + board.getContent());
        boardService.save(board, principal.getUser());
        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
    }

    @DeleteMapping("/api/board/{id}")
    public ResponseDto<Integer> deleteById(@PathVariable int id) {
        System.out.println("api : delete : ....");
        boardService.delete(id);
        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
    }

    @PutMapping("/api/board/{id}")
    public ResponseDto<Integer> update(@PathVariable int id,
                                       @RequestBody Board board) {
        boardService.update(id, board);
        return new ResponseDto<>(HttpStatus.OK.value(), 1);
    }
    
    /* 데이터를 받을 때 컨트롤러에서 DTO를 만들어서 받는게 좋다. */
    @PostMapping("/api/board/{boardId}/reply")
    public Reply replySave(@RequestBody ReplySaveRequestDto replySaveRequestDto) {
    	
        return boardService.replySave(replySaveRequestDto);
    }

}
