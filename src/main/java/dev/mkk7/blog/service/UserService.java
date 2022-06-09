package dev.mkk7.blog.service;

import dev.mkk7.blog.model.RoleType;
import dev.mkk7.blog.model.User;
import dev.mkk7.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
}
