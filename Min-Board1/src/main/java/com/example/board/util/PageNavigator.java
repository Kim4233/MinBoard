package com.example.board.util;

import lombok.Data;

@Data
public class PageNavigator {
		private int countPage;	//페이지당 글목록수
		private int pagePerGroup;//그룹당 페이지 수
		private int currentpage;//현재 페이지(최근 글이 1부터시작)
		private int totalRecordCount;//전체 글 수
		private int totalPageCount;
		private int currentCroup;//전체 페이지 수
		private int startPageGroup;//현재 그룹(최근 그룹이 0부터 시작)
		private int endPageGroup;//현재 그룹의 첫 페이지
		private int startRecord;//현재 그룹의 마지막 페이지

		public PageNavigator(int countPerPage, int pagePageGroup, int currentPage, int total)
		//페이지당 글수, 그룹답페이지 수, 현재 페이지, 전체 글 수를 전달받음
		this.countPerPage = countPerPage;
		this.pagePerGroup = pagePerGroup;
		this.totalRecordCount = totalRecordsCount;
		
		//전체 페이지 수 계산
		totalPageCount = (totalRecoredsCount + countPerPage - 1)/ countPerPage;
		
		//현재 페이지 유효성 검사
		if(currentPage < 1) currentPage =1;
		if(currnetPage > totalPageCount)
}			
