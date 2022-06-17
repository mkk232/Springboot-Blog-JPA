package dev.mkk7.blog.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import dev.mkk7.blog.dto.ReplySaveRequestDto;
import dev.mkk7.blog.utils.DateUtil;

import javax.persistence.*;
import java.sql.Timestamp;

@Table(name = "reply")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Reply {

    @Id // Primary Key
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, length = 200)
    private String content;

    @ManyToOne // 여러개의 댓글은 하나의 게시글에 존재할 수 있다.
    @JoinColumn(name = "boardId")
    private Board board;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User users;

    @CreationTimestamp
    private Timestamp createDate;

	public String getCreateDate() {
		return DateUtil.dateFormat(createDate);
	}
	
	public void update(User user, Board board, String content) {
		setUsers(user);
		setBoard(board);
		setContent(content);
	}

	@Override
	public String toString() {
		return "Reply [id=" + id + ", content=" + content + ", board=" + board + ", users=" + users + ", createDate="
				+ createDate + "]";
	}
	
	
    
    
}
