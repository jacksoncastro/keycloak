package br.com.jackson.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.jackson.util.JWTUtil;

@RestController
public class TokenController {


	@RequestMapping("/auth")
	public String index(){
		return JWTUtil.getJWTToken();
	}

	@RequestMapping("/token")
    public String getToken(){
        return JWTUtil.getJWTToken();
    }
}