package dev.mkk7.blog.repository;

import dev.mkk7.blog.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

// DAO
// 자동으로 bean 등록이 된다.
// @Repository 생략할 수 있다.
public interface UserRepository extends JpaRepository<User, Integer> { // JpaRepository는 User클래스는 관리해주고 User의 PK는 Integer이다.

    // SELECT * FROM user WHERE username = ?
    Optional<User> findByUsername(String username);
}


// JPA Naming 쿼리 전략
// SELECT * FROM user WHERE username = ? AND password = ? 이런 쿼리가 날라감.
// User findByUsernameAndPassword(String username, String password);

//    @Query(value = "SELECT * FROM user WHERE username = ? AND password = ?", nativeQuery = true) // 2가지 방법을 모두 사용가능
//    User login(String username, String password);
