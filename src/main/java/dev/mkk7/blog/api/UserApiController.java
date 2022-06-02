package dev.mkk7.blog.api;

import dev.mkk7.blog.dto.ResponseDto;
import dev.mkk7.blog.model.RoleType;
import dev.mkk7.blog.model.User;
import dev.mkk7.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserApiController {

    @Autowired
    private UserService userService;

    @PostMapping("/api/user")
    public ResponseDto<Integer> save(@RequestBody User user) {
        System.out.println("UserApiController 호출됨...");
        // 실제로 DB에 insert를 하고 아래에서 return이 되면 됨.
        user.setRole(RoleType.USER);
        int result = this.userService.save(user);
        return new ResponseDto<Integer>(HttpStatus.OK.value(), result); // 1이면 성공, -1이면 실패
    }
}