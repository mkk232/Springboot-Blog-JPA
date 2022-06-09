package dev.mkk7.blog.repository;

import dev.mkk7.blog.model.Board;
import dev.mkk7.blog.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Integer> {

}


