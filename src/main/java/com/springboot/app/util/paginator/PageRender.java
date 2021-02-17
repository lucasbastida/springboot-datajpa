package com.springboot.app.util.paginator;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;

public class PageRender<T> {

	private String url;
	private Page<T> page;

	private int totalPages;
	private int numberOfElementsPerPage;
	private int currentPage;

	private List<PageItem> pages;

	public PageRender(String url, Page<T> page) {
		this.url = url;
		this.page = page;
		this.pages = new ArrayList<>();

		totalPages = page.getTotalPages();
		numberOfElementsPerPage = page.getSize();
		currentPage = page.getNumber() + 1;

		int from, to;

		if (totalPages <= numberOfElementsPerPage) {
			from = 1;
			to = totalPages;
		} else if (currentPage <= numberOfElementsPerPage) {
			from = 1;
			to = numberOfElementsPerPage;
		} else if (currentPage >= totalPages - numberOfElementsPerPage / 2) {
			from = totalPages - numberOfElementsPerPage + 1;
			to = numberOfElementsPerPage;
		} else {
			from = currentPage - numberOfElementsPerPage / 2;
			to = numberOfElementsPerPage;
		}

		for (int i = 0; i < to; i++) {
			pages.add(new PageItem(from + i, currentPage == from + i));
		}
	}

	public String getUrl() {
		return url;
	}

	public int getTotalPages() {
		return totalPages;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public List<PageItem> getPages() {
		return pages;
	}

	public boolean isFirst() {
		return page.isFirst();
	}

	public boolean isLast() {
		return page.isLast();
	}
	
	public boolean hasNext() {
		return page.hasNext();
	}

	public boolean hasPrevious() {
		return page.hasPrevious();
	}
}
