package com.common.interceptor.spring;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.common.interceptor.WebConstants;
public class SpringMVCInterceptor extends HandlerInterceptorAdapter {
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {

		// 如果session中没有user对象
		if (null == request.getSession()
				.getAttribute(WebConstants.CURRENT_USER)) {
			String requestedWith = request.getHeader("x-requested-with");
			// ajax请求
			if (requestedWith != null && "XMLHttpRequest".equals(requestedWith)) {
				response.setHeader("session-status", "timeout");
				response.getWriter().print(WebConstants.TIME_OUT);
			} else {
				// 普通页面请求
				response.sendRedirect(request.getContextPath() + "/");
			}
			return false;
		}
		return true;

	}
}
