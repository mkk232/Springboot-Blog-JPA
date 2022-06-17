package dev.mkk7.blog.service;

import dev.mkk7.blog.config.auth.PrincipalDetail;
import dev.mkk7.blog.dto.ReplySaveRequestDto;
import dev.mkk7.blog.model.Board;
import dev.mkk7.blog.model.Reply;
import dev.mkk7.blog.model.RoleType;
import dev.mkk7.blog.model.User;
import dev.mkk7.blog.repository.BoardRepository;
import dev.mkk7.blog.repository.ReplyRepository;
import dev.mkk7.blog.repository.UserRepository;
import dev.mkk7.blog.utils.DateUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class BoardService {

    private final BoardRepository boardRepository;
    
    private final ReplyRepository replyRepository;
    
    private final UserRepository userRepository;

    protected BoardService(BoardRepository boardRepository, ReplyRepository replyRepository, UserRepository userRepository) {
		this.boardRepository = boardRepository;
		this.replyRepository = replyRepository;
		this.userRepository = userRepository;
	}

	@Transactional
    public void save(Board board, User user) { // title, content
        board.setUser(user);
        board.setCount(0);
        boardRepository.save(board);
    }

    @Transactional(readOnly = true)
    public Page<Board> list(Pageable pageable) {
        return boardRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Board view(int id) {
        return boardRepository.findById(id).orElseThrow(() -> {
            return new IllegalArgumentException("해당되는 게시글이 없습니다.");
        });
    }

    @Transactional
    public void delete(int id) {
        boardRepository.deleteById(id);
    }

    @Transactional
    public void update(int id, Board requestBoard) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> {
                    return new IllegalArgumentException("글 찾기 실패 : 아이디를 찾을 수 없습니다.");
                });
        board.setTitle(requestBoard.getTitle());
        board.setContent(requestBoard.getContent());
        // 해당 함수로 종료시(Service가 종료될 때) 트랜잭션이 종료됩니다. 이때 더티체킹이 일어남. 그래서 save함수를 사용하지 않아도 됨.
    }
    
    @SuppressWarnings("deprecation")
	@Transactional
    public Reply replySave(ReplySaveRequestDto replySaveRequestDto) {
    	
    	//replyRepository.mySave(replySaveRequestDto.getUserId(), replySaveRequestDto.getBoardId(), replySaveRequestDto.getContent());
    	    	
    	User user = userRepository.findById(replySaveRequestDto.getUserId()).orElseThrow(() -> {
    		return new IllegalArgumentException("댓글 쓰기 실패 : User id를 찾을 수 없습니다.");
    	});
    	
    	Board board = boardRepository.findById(replySaveRequestDto.getBoardId()).orElseThrow(() -> {
    		return new IllegalArgumentException("댓글 쓰기 실패 : 게시글 id를 찾을 수 없습니다.");
    	});
    	
    	System.out.println("user, board 가져오기 성공");
    	
    	Reply reply = Reply.builder()
    			.users(user)
    			.board(board)
    			.content(replySaveRequestDto.getContent())
    			.build();
    	

    	
    	replyRepository.save(reply);
    	System.out.println("reply id : " + reply.getId());
    	System.out.println("reply board content :" + reply.getBoard().getContent());
    	System.out.println("reply user id : " +reply.getUsers().getId());
    	System.out.println("reply createDate" + reply.getCreateDate()); 
//    	Reply reply = new Reply();  //이러한 방법도 있다 ~
//    	reply.update(user, board, replySaveRequestDto.getContent());
    	System.out.println("service -> reply 반환");
    	return reply;
    }
    
    @Transactional
    public void deleteReply(int replyId) {
    	replyRepository.deleteById(replyId);
    }
    
}
