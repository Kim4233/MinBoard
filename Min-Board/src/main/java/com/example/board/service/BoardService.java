package com.example.board.service;

import com.example.board.model.board.AttachedFile;
import com.example.board.model.board.Board;
import com.example.board.repository.BoardMapper;
import com.example.board.util.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class BoardService {

    private final BoardMapper boardMapper;
    private final FileService fileService;

    @Value("${file.upload.path}")
    private String uploadPath;

    public void saveBoard(Board board, MultipartFile file) {

        boardMapper.saveBoard(board);
        // ファンが起動します。
        if (file != null && file.getSize() > 0) {
            AttachedFile attachedFile = fileService.saveFile(file);
            attachedFile.setBoard_id(board.getBoard_id());
            boardMapper.saveFile(attachedFile);
        }
    }

    public List<Board> findBoards(String searchText, int startRecord, int countPerPage) {
    	// 最初に検索キーワードを入力してから検索する
        RowBounds rowBounds = new RowBounds(startRecord, countPerPage);
        return boardMapper.findBoards(searchText, rowBounds);
    }

    public Board findBoard(Long board_id) {
        return boardMapper.findBoard(board_id);
    }

    public Board readBoard(Long board_id) {
        Board board = findBoard(board_id);
        board.addHit();
        updateBoard(board, false, null);
        return board;
    }

    @Transactional
    public void updateBoard(Board updateBoard, boolean isFileRemoved, MultipartFile file) {
        Board board = boardMapper.findBoard(updateBoard.getBoard_id());
        if (board != null) {
            boardMapper.updateBoard(board);
            AttachedFile attachedFile = boardMapper.findFileByBoardId(updateBoard.getBoard_id());
            if (attachedFile != null && (isFileRemoved || (file != null && file.getSize() > 0))) {
                removeAttachedFile(attachedFile.getAttachedFile_id());
            }
            if (file != null && file.getSize() > 0) {
                AttachedFile savedFile = fileService.saveFile(file);
                savedFile.setBoard_id(updateBoard.getBoard_id());
                boardMapper.saveFile(savedFile);
            }
        }
    }

    @Transactional
    public void removeAttachedFile(Long attachedFile_id) {
        AttachedFile attachedFile = boardMapper.findFileByAttachedFileId(attachedFile_id);
        if (attachedFile != null) {
            String fullPath = uploadPath + "/" + attachedFile.getSaved_filename();
            fileService.deleteFile(fullPath);
            log.info("기존 파일 삭제: {}", attachedFile);
            boardMapper.removeAttachedFile(attachedFile.getAttachedFile_id());
        }
    }

    public void removeBoard(Long board_id) {
        AttachedFile attachedFile = findFileByBoardId(board_id);
        if (attachedFile!= null) {
            removeAttachedFile(attachedFile.getAttachedFile_id());
        }
        boardMapper.removeBoard(board_id);
    }

    public AttachedFile findFileByBoardId(Long board_id) {
        return boardMapper.findFileByBoardId(board_id);
    }

    public AttachedFile findFileByAttachedFileId(Long attachedFile_id) {
        return boardMapper.findFileByAttachedFileId(attachedFile_id);
    }

    public int getTotal(String searchText) {
        return boardMapper.getTotal(searchText);
    }
}
