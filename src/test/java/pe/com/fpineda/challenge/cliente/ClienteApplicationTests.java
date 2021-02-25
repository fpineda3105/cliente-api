package pe.com.fpineda.challenge.cliente;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class ClienteApplicationTests {

	@Test
	void contextLoads() {
	}

	@Configuration
	static class ClienteApplicationTestsConfig {

	}

}
