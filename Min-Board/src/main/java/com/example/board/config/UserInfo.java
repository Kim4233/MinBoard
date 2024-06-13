package com.example.board.config;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.board.model.member.Member;

import lombok.Getter;

@Getter
public class UserInfo implements UserDetails {

	private Member member;
	
	public UserInfo(Member member) {
		this.member = member;
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		//ユーザーの権限を返す
		Collection<GrantedAuthority> collect = new ArrayList<>();

		collect.add(new SimpleGrantedAuthority(member.getRole().name()));
		
		return collect;
	}

	@Override
	public String getPassword() {
		return member.getPassword();
	}

	@Override
	public String getUsername() {
		return member.getMember_id();
	}

	@Override
	public boolean isAccountNonExpired() {
		// アカウントの期限が完了したかどうか
		  // false：いいえ完了しました。 -->ログインできません
		  // true: を完了できませんでした。 --->ログインになります
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// 金曜の夜に旅行
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// 最初にメールを送ってください
		return true;
	}

	@Override
	public boolean isEnabled() {
		// 教師が旅行に行く
		return true;
	}

}
