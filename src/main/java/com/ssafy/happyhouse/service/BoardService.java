package com.ssafy.happyhouse.service;

import com.ssafy.happyhouse.dto.board.BoardDto;
import com.ssafy.happyhouse.entity.board.Board;
import com.ssafy.happyhouse.entity.board.BoardEditor;
import com.ssafy.happyhouse.global.error.ErrorCode;
import com.ssafy.happyhouse.global.error.exception.EntityNotFoundException;
import com.ssafy.happyhouse.mapper.BoardMapper;
import com.ssafy.happyhouse.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    public List<Board> findAll() {

        return boardRepository.findAll();
    }

    public Board findById(Long id) {

        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.NOT_EXISTS_BOARD));

        return board;
    }

    @Transactional
    public void writeBoard(BoardDto.Write dto){

        Board board = Board.builder()
                .title(dto.getTitle())
                .content(dto.getContent())
                .author(dto.getAuthor())
                .build();

        boardRepository.save(board);
    }

    @Transactional
    public void updateBoard(BoardDto.Update dto, Long id){
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.NOT_EXISTS_BOARD));

        BoardEditor.BoardEditorBuilder boardEditorBuilder = board.toEditorBuilder();

        BoardEditor boardEditor = boardEditorBuilder
                .title(dto.getTitle())
                .content(dto.getContent())
                .build();

        board.edit(boardEditor);
    }

    @Transactional
    public void deleteBoard(Long id) {

        Board board = boardRepository.findById(id)
                        .orElseThrow(() -> new EntityNotFoundException(ErrorCode.NOT_EXISTS_BOARD));

        boardRepository.delete(board);
    }
}
