package subereproject.scrawler.payload;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class LoginRequest {
	@NotBlank
	private String userNameOrEmail; 
	
	@NotBlank
	private String password; 
}
