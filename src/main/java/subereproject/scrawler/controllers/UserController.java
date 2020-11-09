package subereproject.scrawler.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import subereproject.scrawler.exception.ResourceNotFoundException;
import subereproject.scrawler.models.User;
import subereproject.scrawler.security.CurrentUser;
import subereproject.scrawler.security.UserPrincipal;
import subereproject.scrawler.services.UserService;

@RestController
public class UserController {
	@Autowired
	private UserService userService; 
	
	@GetMapping("/user/me")
	@PreAuthorize("hasRole('USER')")
	public User getCurrentUser(@CurrentUser UserPrincipal userPrincipal) {
		return userService.findById(userPrincipal.getId())
				.orElseThrow(() -> new ResourceNotFoundException("User", "id", userPrincipal.getId()));
	}
}
