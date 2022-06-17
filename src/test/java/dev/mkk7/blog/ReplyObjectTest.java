package dev.mkk7.blog;

import org.junit.jupiter.api.Test;

import dev.mkk7.blog.model.Reply;


public class ReplyObjectTest {

	@Test
	public void toStringTest() {
		Reply reply = Reply.builder()
				.id(1)
				.users(null)
				.board(null)
				.content("안녕")
				.build();
		
		System.out.println(reply);
	}
}
