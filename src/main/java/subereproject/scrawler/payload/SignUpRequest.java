package subereproject.scrawler.payload;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SignUpRequest {
	@NotBlank
	private String userName; 
	
	@NotBlank
	private String name; 
	
	@NotBlank
	@Email
	private String email; 
	
	@NotBlank
	private String password;
}
