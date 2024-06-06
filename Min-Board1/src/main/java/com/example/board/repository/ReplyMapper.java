package com.example.board.repository;

import org.apache.ibatis.annotations.Mapper;

import com.example.board.model.board.Reply;

@Mapper
public interface ReplyMapper {
	//리플등록
	void saveReply(Reply reply);
	//리플읽기
	
	//리플목록
	//리플수정
	//리플삭제
}
