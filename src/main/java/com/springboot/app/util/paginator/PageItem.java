package com.springboot.app.util.paginator;

public class PageItem {

	private int number;
	private boolean current;

	public PageItem(int number, boolean current) {
		super();
		this.number = number;
		this.current = current;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public boolean isCurrent() {
		return current;
	}

	public void setCurrent(boolean current) {
		this.current = current;
	}

}
