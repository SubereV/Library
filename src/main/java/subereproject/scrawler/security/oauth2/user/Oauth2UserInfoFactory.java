package subereproject.scrawler.security.oauth2.user;

import java.util.Map;

import subereproject.scrawler.exception.OAuth2AuthenticationProcessingException;
import subereproject.scrawler.models.AuthProvider;

public class Oauth2UserInfoFactory {
	public static Oauth2UserInfo getOauth2UserInfo(String registrationId, Map<String, Object> attributes) {
		if (registrationId.equalsIgnoreCase(AuthProvider.google.toString())) {
			return new GoogleOAuth2UserInfo(attributes); 
		}else if (registrationId.equalsIgnoreCase(AuthProvider.facebook.toString())) {
			return new FacebookOAuth2UserInfo(attributes); 
		}else if (registrationId.equalsIgnoreCase(AuthProvider.github.toString())) {
			return new GithubOAuth2UserInfo(attributes); 
		}else {
			throw new OAuth2AuthenticationProcessingException("Sorry! Login with " + registrationId + " is not supported yet.");
		}
	}
}
