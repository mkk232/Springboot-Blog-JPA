package dev.mkk7.blog.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import dev.mkk7.blog.model.Board;
import dev.mkk7.blog.repository.BoardRepository;

@Controller
public class TempControllerTest {
	
	@Autowired
	private BoardRepository boardRepository;
	
	@GetMapping("/temp/home")
	public String tempHome() {
		System.out.println("tempHome()");
		// 파일리턴 기본경로 : src/main/resources/static
		// 리턴명 : /home.html이라고 해야함. 그래야 src/main/resources/static/home.html이 됨. 
		return "home";
	}
	
	@GetMapping(value = "/test/board/{id}")
	@ResponseBody
	public Board test(@PathVariable int id) {
		return boardRepository.findById(id).get();
	}

}
