package com.linkedin.linkstats;

import java.util.Scanner;
import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.apis.LinkedInApi;
import com.github.scribejava.core.model.OAuth1AccessToken;
import com.github.scribejava.core.model.OAuth1RequestToken;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Response;
import com.github.scribejava.core.model.Verb;
import com.github.scribejava.core.oauth.OAuth10aService;
import java.io.IOException;

public class Main {
	private static final String PROTECTED_RESOURCE_URL 
		= "http://api.linkedin.com/v1/people/~/connections:(id,last-name)";
	
	public static void main(String[] args) {
		
     
	}

}
