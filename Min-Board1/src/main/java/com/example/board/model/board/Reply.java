package com.example.board.model.board;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class Reply {
	private Long reply_id;//리플 아이디
	private Long board_id;//게시글 아이디
	private String member_id;//작성자 아이디
	private String contents;//리플 내용
	private LocalDateTime created_time;//등록시간
}
