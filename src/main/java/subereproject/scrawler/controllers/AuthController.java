package subereproject.scrawler.controllers;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import subereproject.scrawler.exception.BadRequestException;
import subereproject.scrawler.models.AuthProvider;
import subereproject.scrawler.models.User;
import subereproject.scrawler.payload.ApiResponse;
import subereproject.scrawler.payload.AuthResponse;
import subereproject.scrawler.payload.LoginRequest;
import subereproject.scrawler.payload.SignUpRequest;
import subereproject.scrawler.security.JwtTokenProvider;
import subereproject.scrawler.services.UserService;

@RestController
@RequestMapping("/auth")
public class AuthController {
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private UserService userService; 
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private JwtTokenProvider jwtTokenProvider; 
	
	@PostMapping("/login")
	public ResponseEntity<?> authenticationUser(@Validated @RequestBody LoginRequest loginRequest) {
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(
						loginRequest.getUserNameOrEmail(), 
						loginRequest.getPassword()
				) 
		);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		String token = jwtTokenProvider.generateToken(authentication); 
		return ResponseEntity.ok(new AuthResponse(token)); 
	}
	
	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Validated @RequestBody SignUpRequest signUpRequest) {
		if (userService.existByUserName(signUpRequest.getUserName())) {
			throw new BadRequestException("Username is already taken!"); 
		}
		
		if (userService.existsByEmail(signUpRequest.getEmail())) {
			throw new BadRequestException("Email address already in use!"); 
		}
		
		User user = new User(); 
		user.setName(signUpRequest.getName());
		user.setUserName(signUpRequest.getUserName());
		user.setEmail(signUpRequest.getEmail());
		user.setPassword(signUpRequest.getPassword());
		user.setProvider(AuthProvider.local);
		
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		
		User result = userService.save(user); 
		URI location = ServletUriComponentsBuilder
						.fromCurrentContextPath().path("/user/me")
						.buildAndExpand(result.getId()).toUri(); 
		
		return ResponseEntity.created(location)
				.body(new ApiResponse("User registed successfully@", true)); 
	}
}
