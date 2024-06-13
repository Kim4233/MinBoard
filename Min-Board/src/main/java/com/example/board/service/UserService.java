package com.example.board.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.board.config.UserInfo;
import com.example.board.model.member.Member;
import com.example.board.repository.MemberMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
/*
 * 로그인 폼에서 아이디아 패스워드를 입력하고 로그인 요청을 하면 UserDetaisServicedhl
 * loaduserByUserName 메서드를 자동을 호출한다.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService{

	private final MemberMapper memberMapper;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		log.info("loadUserByUsername:{}", username);
		
		Member member = memberMapper.findMember(username);
		
		
		if(member != null) {
			return new UserInfo(member);
		}
		return null;
		
	}

}
