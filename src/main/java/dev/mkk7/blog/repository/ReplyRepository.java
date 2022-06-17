package dev.mkk7.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import dev.mkk7.blog.dto.ReplySaveRequestDto;
import dev.mkk7.blog.model.Reply;

public interface ReplyRepository extends JpaRepository<Reply, Integer>{
	
	@Modifying
	@Query(value = "INSERT INTO reply(user_id, board_id, content, create_date) VALUES(?1, ?2, ?3, now())", nativeQuery = true)
	Reply mySave(int userId, int boardId, String content); // 업데이트된 행의 개수를 리턴해줌.
}
