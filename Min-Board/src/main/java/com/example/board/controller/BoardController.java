package com.example.board.controller;

import com.example.board.model.board.Board;
import com.example.board.model.board.BoardUpdateForm;
import com.example.board.model.board.BoardWriteForm;
import com.example.board.model.member.Member;
import com.example.board.repository.BoardMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequestMapping("board")
@Controller
public class BoardController {

	//データベースアクセスのためのBoardMapperフィールド宣言
	private final BoardMapper boardMapper;


	// BoardMapper オブジェクトフィールド注入（プロデューサ注入方式）
    public BoardController(BoardMapper boardMapper) {
        this.boardMapper = boardMapper;
    }

    // 書き込みページの移動
    @GetMapping("write")
    public String writeForm(@SessionAttribute(value = "loginMember", required = false) Member loginMember,
                            Model model) {
    	// writeForm.htmlのフィールドを表示するために空のBoardWriteFormオブジェクトを作成してmodelに保存します
        model.addAttribute("writeForm", new BoardWriteForm());
       
        return "board/write";
    }

 // 投稿を書く
    @PostMapping("write")
    public String write(@SessionAttribute(value = "loginMember", required = false) Member loginMember,
                        @Validated @ModelAttribute("writeForm") BoardWriteForm boardWriteForm,
                        BindingResult result) {
       
        log.info("board: {}", boardWriteForm);
        
        
        if (result.hasErrors()) {
            return "board/write";
        }

      //パラメータで受け取ったBoardWriteFormオブジェクトをBoard型に変換します。
        Board board = BoardWriteForm.toBoard(boardWriteForm);

        board.setMember_id(loginMember.getMember_id());

        boardMapper.saveBoard(board);

        return "redirect:/board/list";
    }

  //全投稿を見る
    @GetMapping("list")
    public String list(@SessionAttribute(value = "loginMember", required = false) Member loginMember,
                       Model model) {
      

    	//データベースに保存されているすべてのBoardオブジェクトをリスト形式で受け取ります。
        List<Board> boards = boardMapper.findAllBoards();
      //ボードリストをモデルに保存します。
        model.addAttribute("boards", boards);
     // board/list.html を探して返します。
        return "board/list";
    }

    //// 投稿を読む
    @GetMapping("read")
    public String read(@SessionAttribute(value = "loginMember", required = false) Member loginMember,
                       @RequestParam Long board_id,
                       Model model) {
        
        log.info("id: {}", board_id);


     // board_idに対応する投稿をデータベースで検索します。
        Board board = boardMapper.findBoard(board_id);

     // board_idに対応する投稿がない場合はリストにリダイレクトさせる。
        if (board == null) {
            log.info("게시글 없음");
            return "redirect:/board/list";
        }


      //ヒット数1増加
        board.addHit();

        boardMapper.updateBoard(board);
        
        model.addAttribute("board", board);

        return "board/read";
    }


    // 投稿編集ページを移動
    @GetMapping("update")
    public String updateForm(@SessionAttribute(value = "loginMember", required = false) Member loginMember,
                             @RequestParam Long board_id,
                             Model model) {
        
        log.info("id: {}", board_id);

        Board board = boardMapper.findBoard(board_id);
        if (board == null || !board.getMember_id().equals(loginMember.getMember_id())) {
            log.info("수정 권한 없음");
            return "redirect:/board/list";
        }

        model.addAttribute("board", board);
       
        return "board/update";
    }

    
    //投稿を修正
    @PostMapping("update")
    public String update(@SessionAttribute(value = "loginMember", required = false) Member loginMember,
                         @RequestParam Long board_id,
                         @Validated @ModelAttribute("board") BoardUpdateForm updateBoard,
                         BindingResult result) {
       

        log.info("board: {}", updateBoard);

        if (result.hasErrors()) {
            return "board/update";
        }

        Board board = boardMapper.findBoard(board_id);

        if (board == null || !board.getMember_id().equals(loginMember.getMember_id())) {
            log.info("수정 권한 없음");
            return "redirect:/board/list";
        }

        board.setTitle(updateBoard.getTitle());

        board.setContents(updateBoard.getContents());

        boardMapper.updateBoard(board);

        return "redirect:/board/list";
    }

    //投稿を削除
    @GetMapping("delete")
    public String remove(@SessionAttribute(value = "loginMember", required = false) Member loginMember,
                         @RequestParam Long board_id) {
      

    	Board board = boardMapper.findBoard(board_id);

        if (board == null || !board.getMember_id().equals(loginMember.getMember_id())) {
            log.info("삭제 권한 없음");
            return "redirect:/board/list";
        }

        boardMapper.removeBoard(board_id);

        return "redirect:/board/list";
    }

}
