package br.uel.trabalho;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

import net.minidev.json.JSONObject;

import org.springframework.web.bind.annotation.GetMapping;

@RestController
@SpringBootApplication
public class TrabalhoApplication {

	public static void main(String[] args) {
		SpringApplication.run(TrabalhoApplication.class, args);
	}

	@GetMapping("/server")
	public JSONObject server() {
		JSONObject response = new JSONObject();
		response.put("status", "👨‍💻 Server Running");
		return response;
	}
	
}
