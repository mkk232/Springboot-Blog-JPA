package dev.mkk7.blog.service;

import dev.mkk7.blog.config.auth.PrincipalDetail;
import dev.mkk7.blog.model.Board;
import dev.mkk7.blog.model.RoleType;
import dev.mkk7.blog.model.User;
import dev.mkk7.blog.repository.BoardRepository;
import dev.mkk7.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class BoardService {

    @Autowired
    private BoardRepository boardRepository;

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
}
