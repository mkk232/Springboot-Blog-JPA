package dev.mkk7.blog.api;

import dev.mkk7.blog.config.auth.PrincipalDetail;
import dev.mkk7.blog.dto.ResponseDto;
import dev.mkk7.blog.model.RoleType;
import dev.mkk7.blog.model.User;
import dev.mkk7.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
public class UserApiController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

//    @Autowired
//    private HttpSession session;

    @PostMapping("/auth/joinProc")
    public ResponseDto<Integer> save(@RequestBody User user) {
        System.out.println("UserApiController 호출됨...");
        // 실제로 DB에 insert를 하고 아래에서 return이 되면 됨.
        this.userService.save(user);
        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1); // 1이면 성공, -1이면 실패
    }

    // 다음시간에 스프링 시큐리티 이용해서 로그인 !!
/*    @PostMapping("/api/user/login")
    public ResponseDto<Integer> login(@RequestBody User user, HttpSession session) {
        System.out.println("UserApiController : login 호출됨");
        User principal = userService.login(user); // principal(접근 주체)

        if(principal != null) {
            session.setAttribute("principal", principal);
        }
        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1); // 1이면 성공, -1이면 실패
    }*/

    @PutMapping("/user")
    public ResponseDto<Integer> update(@RequestBody User user) {
        userService.userUpdate(user);

        /* 여기서는 트랜잭션이 종료되기 때문에 DB에 값은 변경이 됐음.
         하지만 세션값은 변경되지 않은 상태이기 때문에 우리가 직접 세션갑을 변경해줄 것.*/
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication); // 세션 등록
        return new ResponseDto<>(HttpStatus.OK.value(), 1);
    }


}
