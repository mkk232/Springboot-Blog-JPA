package dev.mkk7.blog.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CollectionId;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "board")
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increment
    private int id;

    @Column(nullable = false, length = 100)
    private String title;

    //@Lob // 대용량 데이터를 다룰때 사용
    @Column(columnDefinition = "TEXT")
    private String content; // 섬머노트 라이브러리를 사용할 것임. <html> 태그가 섞여서 디자인이 됨. 글자 용량이 커짐

    private int count; // 조회수

    @ManyToOne(fetch = FetchType.EAGER)
                // Board = Many, User = One -> 한 명의 유저는 여러개의 게시글을 쓸 수 있다.
                // OneToOne = 한 명의 유저는 하나의 게시글만 쓸 수 있다.
                // OneToMany = 여러명의 유저는 하나의 게시글만 쓸 수 있다. ?
    @JoinColumn(name = "userId")///
    private User user; // DB는 오브젝트를 저장할 수 없다. FK 사용, 자바는 오브젝트를 사용할 수 있다.
                         // JPA에서는 오브젝트를 저장할 수 있다.

    @OneToMany(mappedBy = "board", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
//    mappedBy : 연관관계의 주인이 아니다. 즉 FK가 아니다. DB에 컬럼을 만들지 마세요..
//    @JoinColumn(name = "replyId") 이게 필요없다 ?
//                                  Table에 FK가 필요없다..
    @JsonIgnoreProperties({"board"})
    @OrderBy("id desc")
    private List<Reply> replys;

    @CreationTimestamp
    private Timestamp createDate;
}
