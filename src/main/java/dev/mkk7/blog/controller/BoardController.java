package dev.mkk7.blog.controller;

import dev.mkk7.blog.config.auth.PrincipalDetail;
import dev.mkk7.blog.model.Board;
import dev.mkk7.blog.service.BoardService;
import dev.mkk7.blog.utils.DateUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class BoardController {

    @Autowired
    private BoardService boardService;

    @GetMapping({"/",""})
    public String index(Model model, @PageableDefault(size = 3, sort = "id", direction = Sort.Direction.DESC)Pageable pageable) {
        model.addAttribute("boards", boardService.list(pageable));
        return "index";
    }

    @GetMapping("/board/{id}")
    public String findById(@PathVariable int id, Model model) {
    	Board board = boardService.view(id);
        model.addAttribute("board", board);
        return "board/detail";
    }

    @GetMapping("/board/saveForm")
    public String saveForm() {
        return "board/saveForm";
    }

    @GetMapping("/board/{id}/updateForm")
    public String updateForm(@PathVariable int id, Model model) {
        model.addAttribute("board", boardService.view(id));
        return "board/updateForm";

    }

}
