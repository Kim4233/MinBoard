package com.example.board.model.board;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class Reply {
	private Long reply_id;		// リフエイド(インレボリューション)
	private Long board_id;		// 投票する
	private String member_id;	// 作成者: エイデル
	private String content;		// リフナウ
	private LocalDateTime created_time;	// デモ
	
	public static ReplyDto toDto(Reply reply) {
        ReplyDto replyDto = new ReplyDto();
        replyDto.setReply_id(reply.getReply_id());
        replyDto.setBoard_id(reply.getBoard_id());
        replyDto.setMember_id(reply.getMember_id());
        replyDto.setContent(reply.getContent());
        replyDto.setCreated_time(reply.getCreated_time());

        return replyDto;
    }
}	
