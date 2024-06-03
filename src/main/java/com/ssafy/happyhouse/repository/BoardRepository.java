package com.ssafy.happyhouse.repository;

import com.ssafy.happyhouse.entity.board.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long> {
}
