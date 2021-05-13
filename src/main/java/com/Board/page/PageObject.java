package com.Board.page;

import lombok.Data;

@Data
public class PageObject {
	private int nowPageNo;
	private int perPageCn;
	private int startPageNo;
	private int endPageNo;
	
	public PageObject() {
		this.startPageNo = 0;
		this.perPageCn = 3;
	}
	
}
