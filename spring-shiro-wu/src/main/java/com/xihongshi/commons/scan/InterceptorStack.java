package com.wangzhixuan.commons.scan;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.wangzhixuan.commons.annotation.Before;
import com.wangzhixuan.commons.interceptor.Interceptor;

/**
 * 拦截器栈，用于简化spring mvc的拦截器处理
 * 
 * 未完~考虑中~~~
 * 
 * @author L.cm
 * 
 */
public class InterceptorStack extends HandlerInterceptorAdapter {
	private ThreadLocal<Queue<Interceptor>> threadLocal = new ThreadLocal<Queue<Interceptor>>() {
		@Override
		protected Queue<Interceptor> initialValue() {
			return new ConcurrentLinkedQueue<Interceptor>();
		}
	};

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		if (!(handler instanceof HandlerMethod)) {
			return true;
		}
		HandlerMethod handlerMethod = (HandlerMethod) handler;
		Before before = handlerMethod.getMethodAnnotation(Before.class);
		if (before == null) {
			before = handlerMethod.getBeanType().getAnnotation(Before.class);
		}
		if (before == null) {
			return true;
		}
		Class<? extends Interceptor>[] clazzs = before.value();
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		Queue<Interceptor> interceptor = threadLocal.get();
		if (interceptor.isEmpty()) {
			return;
		}
		// 考虑 postHandle 不一定会执行
		threadLocal.remove();
	}
	
	
}
