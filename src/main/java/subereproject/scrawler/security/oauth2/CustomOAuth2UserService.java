package subereproject.scrawler.security.oauth2;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import subereproject.scrawler.exception.OAuth2AuthenticationProcessingException;
import subereproject.scrawler.models.AuthProvider;
import subereproject.scrawler.models.User;
import subereproject.scrawler.security.UserPrincipal;
import subereproject.scrawler.security.oauth2.user.Oauth2UserInfo;
import subereproject.scrawler.security.oauth2.user.Oauth2UserInfoFactory;
import subereproject.scrawler.services.UserService;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService{
	@Autowired
	private UserService userService;

	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		OAuth2User oauth2User = super.loadUser(userRequest); 
		
		try {
			return processOAuth2User(userRequest, oauth2User); 
		} catch (AuthenticationException e) {
			throw e; 
		} catch (Exception e) {
			throw new InternalAuthenticationServiceException(e.getMessage(), e.getCause()); 
		}
	} 
	
	private OAuth2User processOAuth2User(OAuth2UserRequest oAuth2UserRequest, OAuth2User oAuth2User) {
		Oauth2UserInfo oauth2UserInfo = Oauth2UserInfoFactory.getOauth2UserInfo(oAuth2UserRequest.getClientRegistration().getRegistrationId(), oAuth2User.getAttributes()); 
		if (StringUtils.isEmpty(oauth2UserInfo.getEmail())) {
			throw new OAuth2AuthenticationProcessingException("Email not found from OAuth2 provider"); 
		}
		
		Optional<User> userOptional = userService.findByEmail(oauth2UserInfo.getEmail()); 
		User user; 
		
		if (userOptional.isPresent()) {
			user = userOptional.get(); 
			if (!user.getProvider().equals(AuthProvider.valueOf(oAuth2UserRequest.getClientRegistration().getRegistrationId()))) {
				throw new OAuth2AuthenticationProcessingException("Looks like you're signed up with " + user.getProvider() + " account. Please use your " + user.getProvider() + " account to login.");
			}
			user = updateExistingUser(user, oauth2UserInfo); 
		}else {
			user = registerNewUser(oAuth2UserRequest, oauth2UserInfo); 
		}
		return UserPrincipal.create(user, oAuth2User.getAttributes()); 
	}
	
	private User registerNewUser(OAuth2UserRequest oAuth2UserRequest, Oauth2UserInfo oauth2UserInfo) {
		User user = new User(); 
		
		user.setProvider(AuthProvider.valueOf(oAuth2UserRequest.getClientRegistration().getRegistrationId()));
		user.setProviderId(oauth2UserInfo.getId());
		user.setName(oauth2UserInfo.getName());
		user.setEmail(oauth2UserInfo.getEmail());
		user.setImageUrl(oauth2UserInfo.getImageUrl());
		return userService.save(user); 
	}
	
	private User updateExistingUser(User existingUser, Oauth2UserInfo oauth2UserInfo) {
		existingUser.setName(oauth2UserInfo.getName());
		existingUser.setImageUrl(oauth2UserInfo.getImageUrl());
		return userService.save(existingUser);
	}
}
