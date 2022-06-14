package dev.mkk7.blog.service;

import dev.mkk7.blog.model.RoleType;
import dev.mkk7.blog.model.User;
import dev.mkk7.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // 주의


// 스프링이 컴포넌트 스캔을 통해서 Bean에 등록을 해줌. IoC를 해준다. 메모리에 대신 띄어준다.
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder encoder;



    @Transactional
    public int save(User user) {
        String rawPassword = user.getPassword(); // password 원문
        String encPassword = encoder.encode(rawPassword); // 해쉬화
        user.setPassword(encPassword);
        user.setRole(RoleType.USER);
        try {
            userRepository.save(user);
            return 1;
        }catch (Exception e) {
            e.printStackTrace();
            System.out.println("UserService : 회원가입() : " + e.getMessage());
            return -1;
        }
    }

/*    @Transactional(readOnly = true) // Select 할 때 트랜잭션 시작, 서비스 종료시에 트랜잭션 종료 (정합성)
    public User login(User user) {
        return this.userRepository.findByUsernameAndPassword(user.getUsername(), user.getPassword());
    }*/

    @Transactional
    public void userUpdate(User user) {
        // 수정시에는 영속성 컨텍스트 User 오브젝트를 영속화 시키고, 영속화된 User 오브젝트를 수정하면 된다. (Best)
        // select를 해서 user 오브젝트를 DB로 부터 가져오는 이유는 영속화를 하기 위해서임.
        // 영속화된 오브젝트를 변경하면 자동으로 DB에 update문을 날려줌.
        User persistance = userRepository.findById(user.getId()).orElseThrow(() -> {
            return new IllegalArgumentException("회원 찾기 실패");
        });
        String rawPassword = user.getPassword();
        String encPassword = encoder.encode(rawPassword);
        persistance.setPassword(encPassword);
        persistance.setEmail(user.getEmail());

        // 회원수정 함수 종료 시 = 서비스 종료 = 트랜잭션 종료 = commit이 자동으로 됩니다.
        // 영속화된 persistance 객체의 변화가 감지되면 더티체킹이 되어 update문을 날려줌.
    }


}
