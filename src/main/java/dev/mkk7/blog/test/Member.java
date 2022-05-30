package dev.mkk7.blog.test;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//@Getter
//@Setter
@Data
//@AllArgsConstructor // 전체 생성자
@NoArgsConstructor // 빈 생성자
//@RequiredArgsConstructor // final이 붙은 변수에 대한 생성자를 만들어 준다.
public class Member {
	private int id;
	private String username;
	private String password;
	private String email;
	
	@Builder
	public Member(int id, String username, String password, String email) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.email = email;
	}
	
	
}
