package com.example.board.controller;


import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.example.board.model.board.Reply;
import com.example.board.model.member.Member;
import com.example.board.repository.ReplyMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("Reply")
@RequiredArgsConstructor
@RestController
public class ReplyRestController {
	
	private final ReplyMapper replyMapper;
	
	//리플 등록
	@PostMapping("{board_id}")
	public ResponseEntity<String> writeReplyu(@ModelAttribute Reply reply,
											  @SessionAttribute("loginMebmer") Member loginMebmer,
											  @PathVariable Long board_id ){
		log.info("reply:{}", reply);
		
		reply.setMember_id(loginMebmer.getMember_id());
		reply.setBoard_id(board_id);
		
		replyMapper.saveReply(reply);
		
		
		return ResponseEntity.ok("등록 성공");
	}
	//리플 읽기
	@GetMapping("{board_id}/{reply_id}") 
	public ResponseEntity<Reply> findReply(@PathVariable Long board_id,
										   @PathVariable Long reply_id) {
		Reply reply = null;
		return ResponseEntity.ok(reply);
	}
	
	//리플목록
	@GetMapping("{board_id}")
	public ResponseEntity<List<Reply>> findReplies(@PathVariable Long board_id) {
		
		List<Reply> replies = null;
		return ResponseEntity.ok(replies);
		
		
	
	//리플수정
	@PostMapping("{board_id}/{reply_id}")
	public ResponseEntity<Reply> updateReply(@SessionAttribute("loginMebmer") Member loginMebmer,
											 @PathVariable Long board_id,
											 @PathVariable Long  reply_id,
											 @ModelAttribute Reply reply){
		
		
		
		//수정권환이 있는지 체크
		
		//리플 수정
		
		return ResponseEntity.ok(reply);
		
		
	}
	
		//리플 
	
}