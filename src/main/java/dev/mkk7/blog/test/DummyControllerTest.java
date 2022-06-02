package dev.mkk7.blog.test;

import dev.mkk7.blog.model.RoleType;
import dev.mkk7.blog.model.User;
import dev.mkk7.blog.repository.UserRepository;
import org.hibernate.usertype.UserType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.function.Supplier;

@RestController
public class DummyControllerTest {

    @Autowired // 의존성 주입
    private UserRepository userRepository;

    @DeleteMapping("/dummy/user/{id}")
    public String delete(@PathVariable int id) {
        try {
            userRepository.deleteById(id);

        } catch (EmptyResultDataAccessException e) {
            return "삭제에 실패하였습니다. 해당 아이디는 존재하지 않습니다.";
        }

        return "삭제되었습니다. id : " + id;
    }


    // save()는 id를 전달해주지 않으면 insert를 해준다.
    // save()는 id를 전달해주면 해당 id에 대한 데이터가 있다면 update를 해준다.
    // save()는 id를 전달해주면 해당 id에 대한 데이터가 없다면 insert를 해준다.
    // email, password 수정할 예정
    @Transactional // 함수 종료 시 자동 commit이 됨.
    @PutMapping("/dummy/user/{id}")
    public User updateUser(@PathVariable int id, @RequestBody User requestUser) {
        System.out.println("id = " + id);
        System.out.println("requestUser.getPassword() = " + requestUser.getPassword());
        System.out.println("requestUser.getEmail() = " + requestUser.getEmail());

        // save할 때 id값을 넘겨주면 update를 해준다. But 다른 null값들이 들어감. 그래서 사용불가.
        // requestUser.setId(id);
        // requestUser.setUsername("ssar");

        User user = userRepository.findById(id).orElseThrow(() -> {
            return new IllegalArgumentException("수정에 실패하였습니다.");
        });

        user.setPassword(requestUser.getPassword());
        user.setEmail(requestUser.getEmail());


        // userRepository.save(user);

        // 더티 체킹
       return user;
    }

    // http://localhost:8000/blog/user
    @GetMapping("/dummy/users")
    public List<User> list() {
        return userRepository.findAll();
    }

    // 한 페이지당 2건의 데이터를 리턴받기
    @GetMapping("/dummy/user")
    public List<User> pageList(@PageableDefault(size = 1, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<User> pagingUser = userRepository.findAll(pageable);
        List<User> users = pagingUser.getContent();

        return users;
    }

    // {id} 주소로 파라미터를 전달받을 수 있다.
    // http://localhost:8000/blog/dummy/user/3
    @GetMapping("/dummy/user/{id}")
    public User detail(@PathVariable int id) {

        // Optional인 이유 ?
        // user 4번을 찾으면 내가 DB에서 못찾으면 null이 됨.
        // 그럼 return할 때 null 이 되어 문제가 됨.
        // Optional로 User 객체를 감싸서 가져올테니 null인지 아닌지 판단해서 return해.
        /*User user = userRepository.findById(id).orElseGet(new Supplier<User>() { // 익명객체
            // 값이 존재하면 user객체에 정상적인 값이 들어갈것이고 존재하지 않으면 비어있는 User객체를 user에 넣게 될것이다.
            @Override
            public User get() {
                return new User();
            }
        });*/

        // 추천 방법
        User user = userRepository.findById(id).orElseThrow(new Supplier<IllegalArgumentException>() {
            @Override
            public IllegalArgumentException get() {
                return new IllegalArgumentException("해당 사용자는 없습니다. id : " + id);
            }
        });
        // 요청 : 웹브라우저
        // user 객체는 자바 오브젝트임.
        // 변환(웹 브라우저가 이해할 수 있는 데이터) -> JSON (Gson 라이브러리)
        // 스프링부트 = MessageConverter 라는 애가 응답시에 자동 작동.
        // 만약에 자바 오브젝트를 리턴하게 되면 MessageConverter가 Jackson이라는 라이브러리를 호출해서
        // user 오브젝트를 json으로 변환해서 브라우저에게 던져줌.
        return user;

        // 람다식 있음.
        /*User user1 = userRepository.findById(id).orElseThrow(()-> {
           return new IllegalArgumentException("해당 사용자는 없습니다.");
        });
        return user1;*/
    }

    // http://localhost:8000/blog/dummy/join (요청)
    // http의 body에 username, password, email 데이터를 가지고 요청하게 되면 이 3가지 값이 파라미터에 쏙쏙 들어옴
    @PostMapping("/dummy/join")
    public String join(User user) {
        System.out.println("user.getId() = " + user.getId());
        System.out.println("user.getUsername() = " + user.getUsername());
        System.out.println("user.getPassword() = " + user.getPassword());
        System.out.println("user.getEmail() = " + user.getEmail());
        System.out.println("user.getRole() = " + user.getRole());
        System.out.println("user.getCreateDate() = " + user.getCreateDate());

        user.setRole(RoleType.USER);
        userRepository.save(user);
        return "회원가입이 완료되었습니다.";
    }
}

