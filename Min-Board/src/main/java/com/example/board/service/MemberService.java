package com.example.board.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.board.model.member.Member;
import com.example.board.model.member.RoleType;
import com.example.board.repository.MemberMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {
	private final MemberMapper memberMapper;
	private final PasswordEncoder passwordEncoder;
	
	
	public Member findMember(String member_id) {
		return memberMapper.findMember(member_id);
	}
	
	@Transactional
	public void saveMember(Member member) {
		
		String rawPassword = member.getPassword();

		memberMapper.saveMember(member);

		//비크립트 방식으로 암호화
		String encPassword = passwordEncoder.encode(rawPassword);		
		
		member.setPassword(encPassword);

		log.info("encPassword: {}:", encPassword);
		
		//기본 ROLE 부여
		member.setRole(RoleType.ROLE_USER);
		
		
//		memberMapper.saveMember(member);
	}
	
	
}