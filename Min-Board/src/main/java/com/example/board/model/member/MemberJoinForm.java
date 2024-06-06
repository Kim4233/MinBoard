package com.example.board.model.member;

import java.time.LocalDate;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;


@Data
public class MemberJoinForm {
	@Size(min = 4, max = 20, message = "아이디는 4~20사이로 입력해주세요.")
	private String member_id;
	@Size(min = 4, max = 20)
	private String password;
	@NotEmpty
	private String name;
	@NotNull
	private GenderType gender;
	@Past
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate birth;
	private String email;
	
	public static Member toMember(MemberJoinForm memberJoinForm) {
		Member member = new Member();
		member.setMember_id(memberJoinForm.getMember_id());
		member.setPassword(memberJoinForm.getPassword());
		member.setName(memberJoinForm.getName());
		member.setGender(memberJoinForm.getGender());
		member.setBirth(memberJoinForm.getBirth());
		member.setEmail(memberJoinForm.getEmail());
		return member;
	}
}
