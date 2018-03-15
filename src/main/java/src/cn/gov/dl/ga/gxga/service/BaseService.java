package cn.gov.dl.ga.gxga.service;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

import cn.gov.dl.ga.gxga.common.PagingList;

public class BaseService {
	public final Logger logger = LoggerFactory.getLogger(getClass());
	
	protected final String INDEX_LIST_LIMT=" limit 0,8";

	/* 设置JDBC Template */
	protected JdbcTemplate jt;

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jt = jdbcTemplate;
	}

	public PagingList getPagingList(String srcSql, HttpServletRequest request) {
		int pageIndex = 1;
		int pageSize = 10;
		pageIndex = Integer
				.parseInt((String) request.getAttribute("pageIndex"));
		pageSize = Integer.parseInt((String) request.getAttribute("pageSize"));
		return new PagingList(srcSql, pageIndex + 1, pageSize, jt);
	}

	public PagingList getPagingList(String srcSql, HttpServletRequest request,
			int pageSize) {
		int pageIndex = 1;

		String req_pageIndex = (String) request.getAttribute("pageIndex");
		String req_pageSize = (String) request.getAttribute("pageSize");

		if (StringUtils.isNotEmpty(req_pageIndex)) {
			pageIndex = Integer.parseInt(req_pageIndex);
		}

		if (StringUtils.isNotEmpty(req_pageSize)) {
			pageSize = Integer.parseInt(req_pageSize);
		}

		String paging = (String) request.getAttribute("paging");

		if ("front".equals(paging)) {
			return new PagingList(srcSql, pageIndex, pageSize, jt);
		} else {
			return new PagingList(srcSql, pageIndex + 1, pageSize, jt);
		}

	}

	public PagingList getPagingList(String srcSql, HttpServletRequest request,
			int pageSize, Object[] parameters) {
		int pageIndex = 1;

		String req_pageIndex = (String) request.getAttribute("pageIndex");
		String req_pageSize = (String) request.getAttribute("pageSize");

		if (StringUtils.isNotEmpty(req_pageIndex)) {
			pageIndex = Integer.parseInt(req_pageIndex);
		}

		if (StringUtils.isNotEmpty(req_pageSize)) {
			pageSize = Integer.parseInt(req_pageSize);
		}

		String paging = (String) request.getAttribute("paging");

		if ("front".equals(paging)) {
			return new PagingList(srcSql, pageIndex, pageSize, jt, parameters);
		} else {
			return new PagingList(srcSql, pageIndex + 1, pageSize, jt,
					parameters);
		}

	}

	public PagingList getPagingList(String srcSql, HttpServletRequest request,
			Object[] parameterObject) {
		int pageIndex = 1;
		int pageSize = 10;
		pageIndex = Integer
				.parseInt((String) request.getAttribute("pageIndex"));
		pageSize = Integer.parseInt((String) request.getAttribute("pageSize"));
		return new PagingList(srcSql, pageIndex + 1, pageSize, jt,
				parameterObject);
	}
}
