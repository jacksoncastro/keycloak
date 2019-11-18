package br.com.jackson.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.keycloak.representations.AccessToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TokenController {

	@Autowired
	private HttpServletRequest request;

	@Autowired
	private HttpServletResponse response;


	@RequestMapping("/")
	public String index(){
		AccessToken token = getToken();
		StringBuffer buffer = new StringBuffer();

		buffer.append("<p>Welcome Keycloak</p>")
			  .append("<ul><li><a href=\"/user-info\">Protected resource</a></li>");

		if (token != null) {
			buffer.append("<li><a href=\"/logout\">Logout</a></li>");
		}

		buffer.append("</ul>");

		return buffer.toString();
	}

	@RequestMapping("/user-info")
    public AccessToken userInfo(){
        return getToken();
    }

	@GetMapping(path = "/logout")
	public void logout() throws ServletException {
		request.logout();
		try {
			response.sendRedirect("/");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	private AccessToken getToken() {
		KeycloakAuthenticationToken token = (KeycloakAuthenticationToken) request.getUserPrincipal();
		if (token != null) {
			@SuppressWarnings("unchecked")
			KeycloakPrincipal<KeycloakSecurityContext> principal = (KeycloakPrincipal<KeycloakSecurityContext>) token.getPrincipal();
			KeycloakSecurityContext session = principal.getKeycloakSecurityContext();
			return session.getToken();
		}
		return null;
	}
}