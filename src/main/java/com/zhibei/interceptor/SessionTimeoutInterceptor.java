package com.zhibei.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.zhibei.constant.SiteConstant;
import com.zhibei.entity.SysUserModel;

/**
 * @description: 处理session超时的拦截器 会话控制拦截器
 * 
 */
public class SessionTimeoutInterceptor implements HandlerInterceptor{
	//还没发现可以直接配置不拦截的资源，所以在代码里面来排除
    public String[] allowUrls;
    
    public void setAllowUrls(String[] allowUrls) {
        this.allowUrls = allowUrls;
    }
        
    /**
     * 在业务处理器处理请求之前被调用
     * 如果返回false 则退出本拦截器，本拦截器后面的postHandle与afterCompletion不再执行
     */ 	
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
            Object arg2) throws Exception {   	
    	//对一些路径进行放行
        String requestUrl = request.getRequestURI().replace(request.getContextPath(), "");  
        if(null != allowUrls && allowUrls.length>=1)
            for(String url : allowUrls) {  
                if(requestUrl.contains(url)) {
                    return true;  
                }  
            }
        
        //获取session中的值
        SysUserModel user = (SysUserModel) request.getSession().getAttribute(SiteConstant.AUTHENTICATION_KEY);
        if(user != null) {         	
            return true;//返回true，则这个方面调用后会接着调用postHandle(),  afterCompletion()
        }else{
        	//跳转到登录页面
            String page = request.getContextPath() + "/login.html";
            response.sendRedirect(page);
            return false;          
        }
    }
    
    
    /**
     * 在DispatcherServlet完全处理完请求后被调用
     * 当拦截器抛出异常时,依然会从当前拦截器往回执行所的拦截器的afterCompletion()
     */
    public void afterCompletion(HttpServletRequest arg0,
            HttpServletResponse arg1, Object arg2, Exception arg3)
            throws Exception {
    }
    
    /**
     * 在业务处理器处理请求执行完成后,生成视图之前执行的动作
     */
    public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1,
            Object arg2, ModelAndView arg3) throws Exception {
    }

}