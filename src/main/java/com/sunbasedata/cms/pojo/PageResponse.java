package com.sunbasedata.cms.pojo;

import java.util.List;

import lombok.Data;

@Data
public class PageResponse<T> {

	private List<T> contents;
	private Integer pageNumber;
	private Integer pageSize;
	private Long totalElement;
	private Integer totalPages;
	private boolean isLast;

}
