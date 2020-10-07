package subereproject.scrawler.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("test")
public class TestRestApi {

	@GetMapping
	public String test() {
		return "success";
	}
}
