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
	private static String options= "connections:(id,first-name,last-name,maiden-name,formatted-name,"
				+ "phonetic-first-name,phonetic-last-name,formatted-phonetic-name,headline,location,industry,"
				+ "current-share,num-connections,num-connections-capped,summary,specialties,positions,picture-url,"
				+ "picture-urls::(original),site-standard-profile-request,api-standard-profile-request,public-profile-url)";
	
	private static final String PROTECTED_RESOURCE_URL = "https://api.linkedin.com/v1/people/~:(%s)";
	private static String apikey = "77afmdyf72tu3o";
	private static String apisecret = "I1HczwvphpPNBmjO";	
	
	public static void main(String[] args) throws IOException {
		final OAuth10aService service = new ServiceBuilder()
                .apiKey(apikey)
                .apiSecret(apisecret)
                .build(LinkedInApi.instance());
        final Scanner in = new Scanner(System.in);

        System.out.println("=== LinkedIn's OAuth Workflow ===");
        System.out.println();

        // Obtain the Request Token
        System.out.println("Fetching the Request Token...");
        final OAuth1RequestToken requestToken = service.getRequestToken();
        System.out.println("Got the Request Token!");
        System.out.println();

        System.out.println("Now go and authorize ScribeJava here:");
        System.out.println(service.getAuthorizationUrl(requestToken));
        System.out.println("And paste the verifier here");
        System.out.print(">>");
        final String oauthVerifier = in.nextLine();
        System.out.println();

        // Trade the Request Token and Verfier for the Access Token
        System.out.println("Trading the Request Token for an Access Token...");
        final OAuth1AccessToken accessToken = service.getAccessToken(requestToken, oauthVerifier);
        System.out.println("Got the Access Token!");
        System.out.println("(if your curious it looks like this: " + accessToken
                + ", 'rawResponse'='" + accessToken.getRawResponse() + "')");
        System.out.println();

//        // Now let's go and ask for a protected resource!
//        System.out.println("Now we're going to access a protected resource...");
//        final OAuthRequest request = new OAuthRequest(Verb.GET, PROTECTED_RESOURCE_URL, service);
//        service.signRequest(accessToken, request);
//        final Response response = request.send();
//        System.out.println("Got it! Lets see what we found...");
//        System.out.println();
//        System.out.println(response.getBody());
//
//        System.out.println();
//        System.out.println("Thats it man! Go and build something awesome with ScribeJava! :)");
        
        System.out.println("Now we're going to access a protected resource...");
        while (true) {
            System.out.println("Paste profile query for fetch (firstName, lastName, etc) or 'exit' to stop example");
            System.out.print(">>");
            final String query = in.nextLine();
            System.out.println();

            if ("exit".equals(query)) {
                break;
            }

            final OAuthRequest request = new OAuthRequest(Verb.GET, String.format(PROTECTED_RESOURCE_URL, query),
                    service);
            request.addHeader("x-li-format", "json");
            request.addHeader("Accept-Language", "ru-RU");
            service.signRequest(accessToken, request);
            final Response response = request.send();
            System.out.println();
            System.out.println(response.getCode());
            System.out.println(response.getBody());

            System.out.println();
        }
	}

}
