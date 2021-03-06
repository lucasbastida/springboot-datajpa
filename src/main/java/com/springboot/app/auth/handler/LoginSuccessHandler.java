package com.springboot.app.auth.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.FlashMapManager;
import org.springframework.web.servlet.support.SessionFlashMapManager;

@Component
public class LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler{

	//you can use authentication in a controller method to access user data by argument or static security context holder
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		
		FlashMapManager flashMapManager = new SessionFlashMapManager();
		
		FlashMap flashMap = new FlashMap();
		
		flashMap.put("success", authentication.getName() + " logged in successfully.");
		
		logger.info(authentication.getName() + " has logged in.");
		
		flashMapManager.saveOutputFlashMap(flashMap, request, response);
		
		super.onAuthenticationSuccess(request, response, authentication);
	}
	
		

}
