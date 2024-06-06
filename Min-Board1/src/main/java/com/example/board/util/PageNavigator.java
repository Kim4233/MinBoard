package com.example.board.util;

import lombok.Data;

@Data
public class PageNavigator {
		private int countPerPage;	//페이지당 글목록수
		private int pagePerGroup;//그룹당 페이지 수
		private int currentpage;//현재 페이지(최근 글이 1부터시작)
		private int totalRecordCount;//전체 글 수
		private int totalPageCount;//전체 페이지 수
		private int currentGroup;//전체 페이지 수
		private int startPageGroup;//현재 그룹(최근 그룹이 0부터 시작)
		private int endPageGroup;//현재 그룹의 첫 페이지
		private int startRecord;//현재 그룹의 마지막 페이지

		public PageNavigator(int countPage, int pagePerGroup, int currentPage, int totalPageCount) {
			//페이지당 글 수, 그룹당 페이지 수, 현재 페이지, 전체 페이지, 전체 글수를 전달받은
			this.countPerPage = countPage;
			this.pagePerGroup = pagePerGroup;
			this.totalPageCount = totalPageCount;
			
			//전체 페이지 수
			totalPageCount = (totalRecordCount + countPerPage - 1) / countPerPage;
			
			//전체 페이지 수
			totalPageCount = (totalRecordCount + countPerPage - 1) / countPerPage;
			
			//전달된 현재 페이지가 1보다 작으면 현재페이지를 1페이지로 지정
			if(currentPage < 1) currentPage = 1;
			//전단된 현재 페이지가 마지막 페이지보다 크면 현재 페이지를 마지막 페이지로 지정
			if(currentPage < totalPageCount) currentPage = totalPageCount;
			
			this.currentpage = currentPage;
			
			//현재 그룹
			currentGroup = (currentPage - 1) /pagePerGroup;
			
			//현재 그룹의 첫페이지
			startPageGroup = currentGroup * pagePerGroup + 1;
			//현재 그룹의 첫 페이지가 1보다 작으면 1로 처리
			startPageGroup = startPageGroup < 1 ? 1 : startPageGroup;
			//현재 그룹의 마지막 페이지
			endPageGroup = startPageGroup + pagePerGroup -1;
			//현재 그룹의 마지막 페이지가 전체 페이지 수보다 작으면 전체페이지 수를 마지막으로
			endPageGroup = endPageGroup > totalPageCount ? endPageGroup : totalPageCount;
			
			//전체 레코드 중 현재 페이지 첫 글의 위치
			startRecord = (currentPage -1) * countPerPage;
		}


}			
