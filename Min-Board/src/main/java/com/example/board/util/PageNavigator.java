package com.example.board.util;

import lombok.Data;

@Data
public class PageNavigator {
    
    private int countPerPage;		//ページあたりの文字数
    private int pagePerGroup;		//グループ単位のページ数
    private int currentPage;		//現在のページ（最近の投稿は1から始まります）
    private int totalRecordsCount;	//全文数
    private int totalPageCount;		//全ページ数
    private int currentGroup;		//現在のグループ（最近のグループは0から始まります）
    private int startPageGroup;		//現在のグループの最初のページ
    private int endPageGroup;		//現在のグループの最後のページ
    private int startRecord;		//レコード全体の中の現在のページの最初の投稿の位置（0から始まる）

    
    public PageNavigator(int countPerPage, int pagePerGroup, int currentPage, int totalRecordsCount) {
    	//ページあたりの投稿数、グループあたりのページ数、現在のページ、全文の数を受け取る
        this.countPerPage = countPerPage;
        this.pagePerGroup = pagePerGroup;
        this.totalRecordsCount = totalRecordsCount;


      //全ページ数
        totalPageCount = (totalRecordsCount + countPerPage - 1) / countPerPage;

      //配信された現在のページが1未満の場合、現在のページを1ページに指定します
        if (currentPage < 1) currentPage = 1;
      //配信された現在のページが最後のページより大きい場合、現在のページを最後のページとして指定します
        if (currentPage > totalPageCount) currentPage = totalPageCount;

        this.currentPage = currentPage;

      //現在のグループ
        currentGroup = (currentPage - 1) / pagePerGroup;

        ////現在のグループの最初のページ
        startPageGroup = currentGroup * pagePerGroup + 1;
        
        //現在のグループの最初のページが 1 未満の場合は 1 として処理
        startPageGroup = startPageGroup < 1 ? 1 : startPageGroup;
      //現在のグループの最後のページ
        endPageGroup = startPageGroup + pagePerGroup - 1;

      //現在のグループの最後のページがページの総数より少ない場合は、ページの総数を最後にします。
        endPageGroup = endPageGroup < totalPageCount ? endPageGroup : totalPageCount;

      //レコード全体の現在のページの最初の投稿の場所
        startRecord = (currentPage - 1) * countPerPage;
    }
}
