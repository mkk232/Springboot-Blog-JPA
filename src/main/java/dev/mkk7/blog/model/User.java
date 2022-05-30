package dev.mkk7.blog.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

// ORM -> Java Object로 매핑을 해줌.
@Entity // User 클래스가 PostgreSQL에 테이블이 생성이 된다.
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "users")
public class User {

    @Id // Primary Key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 넘버링 전략, 우리가 해당 프로젝트에서 연결된 DB에 넘버링 전략을 따라간다.
    private int id; // 시퀀스, auto_increment

    @Column(nullable = false, length = 30) // false는 username이 null이 될 수 없다는 의미, length는 30을 넘을 수 없다.
    private String username; // 아이디

    @Column(nullable = false, length = 100) // username과 같음. 100인 경우는 hash를 이용해서 비밀번호를 암호화 할 것이다.
    private String password;

    @Column(nullable = false, length = 50) // 위와 같음.
    private String email;

    @ColumnDefault("'user'") // default는 안에 홑따옴표를 사용해야 함.
    private String role; // Enum을 사용하는게 좋음. Enum을 사용하면 도메인을 만들 수 있음. 회원가입 시 이 사람은 admin, user... 등

    @CreationTimestamp // 시간이 자동으로 입력이 됨.
    private Timestamp createDate;

}
