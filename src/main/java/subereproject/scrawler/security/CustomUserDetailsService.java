package subereproject.scrawler.security;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import subereproject.scrawler.exception.ResourceNotFoundException;
import subereproject.scrawler.models.User;
import subereproject.scrawler.services.UserService;

@Service
@Transactional
public class CustomUserDetailsService implements UserDetailsService{
	@Autowired
	private UserService userService;
	
	@Override
	public UserDetails loadUserByUsername(String userNameOrEmail) throws UsernameNotFoundException {
		User user = userService.findByUserNameOrEmail(userNameOrEmail, userNameOrEmail)
				.orElseThrow(() -> new UsernameNotFoundException("User not found with username or email: " + userNameOrEmail)); 
		System.out.println(user.toString() + "heheheehe");
		return UserPrincipal.create(user);
	}
	
	public UserDetails loadUserById(long id) {
		User user = userService.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
		
		return UserPrincipal.create(user);
	}
}
