package com.example.board.model.board;

import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class BoardWriteForm {
	@NotBlank
	private String title; 
	@NotBlank
	private String contents; 
	
	public static Board toBoard(BoardWriteForm boardWriteForm) {
		Board board = new Board();
		board.setTitle(boardWriteForm.getTitle());
		board.setContents(boardWriteForm.getContents());
		return board;
	}
}
