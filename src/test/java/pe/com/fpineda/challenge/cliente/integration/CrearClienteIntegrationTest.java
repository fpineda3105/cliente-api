package pe.com.fpineda.challenge.cliente.integration;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import pe.com.fpineda.challenge.cliente.api.CrearClienteApi;
import pe.com.fpineda.challenge.cliente.config.AppConfigTests;
import pe.com.fpineda.challenge.cliente.BaseTest;
import pe.com.fpineda.challenge.cliente.core.port.CrearClientePort;
import pe.com.fpineda.challenge.cliente.core.usecase.CrearClienteUseCase;
import pe.com.fpineda.challenge.cliente.infrastructure.adapter.persistence.ClienteJdbcAdapter;
import pe.com.fpineda.challenge.cliente.infrastructure.adapter.web.dto.CrearClienteDto;
import pe.com.fpineda.challenge.cliente.util.TestUtilsFactory;

import javax.sql.DataSource;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * @author fpineda
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
class CrearClienteIntegrationTest extends BaseTest {

    @LocalServerPort
    private int randomServerPort;

    @Autowired
    private TestRestTemplate restTemplate;

    @TestConfiguration
    @Import(AppConfigTests.class)
    @ComponentScan(basePackages = "pe.com.fpineda.challenge.cliente.infrastructure.adapter.web")
    static class Configuration {

        @Autowired
        public DataSource dataSource;

        @Bean
        public CrearClientePort crearClientePort(){
            return new ClienteJdbcAdapter(this.dataSource);
        }

        @Bean
        public CrearClienteUseCase crearClienteUseCase() {
            return new CrearClienteApi(crearClientePort());
        }
    }

    @Test
    @DisplayName("Post /creacliente - exitosamente")
    @Sql(scripts = "/schema.sql")
    void deberia_crear_cliente_exitosamente() throws URISyntaxException {
        // Prepare Data
        final String baseUrl = "http://localhost:" + randomServerPort + "/creacliente";
        URI uri = new URI(baseUrl);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        var crearClienteDto = TestUtilsFactory.buildCrearClienteDto();

        HttpEntity<CrearClienteDto> entity = new HttpEntity<>(crearClienteDto, headers);

        // Execution
        ResponseEntity<Void> result = restTemplate.postForEntity(uri,entity, Void.class);

        // Assertions
        Assertions.assertEquals(HttpStatus.CREATED, result.getStatusCode());
        Assertions.assertTrue(result.getHeaders().getLocation().getPath().contains("/consultacliente/"));

    }


}
