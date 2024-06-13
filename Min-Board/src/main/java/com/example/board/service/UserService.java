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
