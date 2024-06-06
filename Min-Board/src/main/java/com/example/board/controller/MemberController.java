package com.example.board.controller;

import java.util.Date;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.board.model.member.LoginForm;
import com.example.board.model.member.Member;
import com.example.board.model.member.MemberJoinForm;
import com.example.board.repository.MemberMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("member")
@Controller
public class MemberController {
	

	//データベースアクセスのためのMemberMapperフィールド宣言
	private MemberMapper memberMapper;
	

	// MemberMapperフィールドオブジェクトの注入（setterを使用した注入）	@Autowired
	public void setMemberMapper(MemberMapper memberMapper) {
		this.memberMapper = memberMapper;
	}
	

	//会員登録ページを移動する
	@GetMapping("join")
	public String joinForm(Model model) {
		log.info("회원가입 페이지");

		model.addAttribute("joinForm", new MemberJoinForm());
		
		return "member/joinForm";
	}
	

	//会員登録
	@PostMapping("join")
	public String join(@Validated @ModelAttribute("joinForm") MemberJoinForm joinForm,
						BindingResult result) {
		
		log.info("joinForm:{}", joinForm);
		log.info("result:{}", result);
		
		// validation にエラーがあれば、加入せずに member/joinForm.html ページに戻る。
		if(result.hasErrors()) {
			return "/member/joinForm";
		}
		
		//メールアドレスに「@」文字が含まれていることを確認してください。
        if (!joinForm.getEmail().contains("@")) {
            result.reject("emailError", "이메일 형식이 잘못되었습니다.");
            return "member/joinForm";
        }
        Member member = memberMapper.findMember(joinForm.getMember_id());
        if (member != null) {
            log.info("이미 가입된 아이디 입니다.");
            result.reject("duplicate ID", "이미 가입된 아이디 입니다.");
            return "member/joinForm";
        }
        memberMapper.saveMember(MemberJoinForm.toMember(joinForm));

        return "redirect:/";
	}
	
	//ログインページの移動
    @GetMapping("login")
    public String loginForm(Model model) {
        log.info("로그인 실행");

        model.addAttribute("loginForm", new LoginForm());
        
        return "member/loginForm";
    }
	

 // ログイン処理
	@PostMapping("login")
	public String login(@Validated @ModelAttribute("loginForm") LoginForm loginForm,
						BindingResult result,
						HttpServletResponse response,
						HttpServletRequest request,
						@RequestParam(defaultValue="/") String redirectURL) {
		log.info("loginForm: {}", loginForm);
		log.info("redirectURL: {}", redirectURL);

		if(result.hasErrors()) {
			return "member/loginForm";
		}

		Member member = memberMapper.findMember(loginForm.getMember_id());

		if (member == null || !member.getPassword().equals(loginForm.getPassword())) {

        	result.reject("loginError", "아이디가 없거나 패스워드가 다릅니다.");
            
        	return "member/loginForm";
            
            
        }
        
		HttpSession session = request.getSession();

		session.setAttribute("loginMember", member);
		
		return "redirect:" + redirectURL;
	}
	
	@GetMapping("sessionInfo")
	public String sessionInfo(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if(session == null) {
			return "redirect:/member/login";
		}
		log.info("sessionId:{}", session.getId());
		log.info("maxInactiveInterval:{}", session.getMaxInactiveInterval());
		log.info("creationTime:{}", new Date(session.getCreationTime()));
		log.info("lastAccessedTime:{}", new Date(session.getLastAccessedTime()));
		return "redirect:/";
	}
	
	@GetMapping("logout")
	public String logout(HttpServletResponse response,
						  HttpServletRequest request) {

		
		
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        return "redirect:/";
	}
	
	
	
	
	
	
	
}
