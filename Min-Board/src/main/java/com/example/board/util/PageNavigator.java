package com.example.board.util;

import lombok.Data;

@Data
public class PageNavigator {
    
	private int countPerPage;		//ページマップ 設定
    private int pagePerGroup;		//ギャラリーページ 表示
    private int currentPage;		//ホテルページ (1 ブロック シングル)
    private int totalRecordsCount;	// 確認する
    private int totalPageCount;		// ジェチョン ページ 表示
    private int currentGroup;		// ホストグループ (アカウントが 0 登録されている場合)
    private int startPageGroup;		//ホテルグループのページ
    private int endPageGroup;		// ホストグループのマジマページ
    private int startRecord;		//レコード全体の現在のページの最初の投稿の位置（0から始まる）


    public PageNavigator(int countPerPage, int pagePerGroup, int currentPage, int totalRecordsCount) {
    	//ページあたりの投稿数、グループあたりのページ数、現在のページ、全文の数を受け取る
        this.countPerPage = countPerPage;
        this.pagePerGroup = pagePerGroup;
        this.totalRecordsCount = totalRecordsCount;

        //全ページ数
        totalPageCount = (totalRecordsCount + countPerPage - 1) / countPerPage;

        //配信された現在のページが1より小さい場合、現在のページを1ページに指定します
        if (currentPage < 1) currentPage = 1;
        //配信された現在のページが最後のページより大きい場合、現在のページを最後のページとして指定します
        if (currentPage > totalPageCount) currentPage = totalPageCount;

        this.currentPage = currentPage;

        //現在のグループ
        currentGroup = (currentPage - 1) / pagePerGroup;

        //現在のグループの最初のページ
        startPageGroup = currentGroup * pagePerGroup + 1;
        //現在のグループの最初のページが1より小さい場合は1として処理
        startPageGroup = startPageGroup < 1 ? 1 : startPageGroup;
        //現在のグループの最後のページ
        endPageGroup = startPageGroup + pagePerGroup - 1;
        //現在のグループの最後のページがページの総数より少ない場合は、ページの総数を最後にします。
        endPageGroup = endPageGroup < totalPageCount ? endPageGroup : totalPageCount;

        //レコード全体の現在のページの最初の投稿の場所
        startRecord = (currentPage - 1) * countPerPage;
    }
}
