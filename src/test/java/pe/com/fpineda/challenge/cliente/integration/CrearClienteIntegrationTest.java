package pe.com.fpineda.challenge.cliente.integration;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import pe.com.fpineda.challenge.cliente.BaseTest;
import pe.com.fpineda.challenge.cliente.api.CrearClienteApi;
import pe.com.fpineda.challenge.cliente.config.AppConfigTests;
import pe.com.fpineda.challenge.cliente.core.port.CrearClientePort;
import pe.com.fpineda.challenge.cliente.core.usecase.CrearClienteUseCase;
import pe.com.fpineda.challenge.cliente.core.usecase.ObtenerClientesKPIUseCase;
import pe.com.fpineda.challenge.cliente.core.usecase.ObtenerListaClientesUseCase;
import pe.com.fpineda.challenge.cliente.infrastructure.adapter.persistence.ClienteJdbcAdapter;
import pe.com.fpineda.challenge.cliente.infrastructure.adapter.web.controller.ClienteController;
import pe.com.fpineda.challenge.cliente.infrastructure.adapter.web.controller.ClienteExceptionController;
import pe.com.fpineda.challenge.cliente.infrastructure.adapter.web.dto.CrearClienteDto;
import pe.com.fpineda.challenge.cliente.util.TestUtilsFactory;

import javax.sql.DataSource;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Objects;

import static org.mockito.Mockito.reset;

/**
 * @author fpineda
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@ContextConfiguration(classes = {CrearClienteIntegrationTest.Configuration.class, AppConfigTests.class, ClienteController.class, ClienteExceptionController.class})
class CrearClienteIntegrationTest extends BaseTest {

    static class Configuration {

        @Autowired
        public DataSource dataSource;

        @MockBean
        public ObtenerClientesKPIUseCase obtenerClientesKPIUseCase;

        @MockBean
        public ObtenerListaClientesUseCase obtenerListaClientesUseCase;

        @Bean
        public CrearClientePort crearClientePort() {
            return new ClienteJdbcAdapter(this.dataSource);
        }

        @Bean
        public CrearClienteUseCase crearClienteUseCase() {
            return new CrearClienteApi(crearClientePort());
        }

    }

    @LocalServerPort
    private int randomServerPort;
    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private ObtenerClientesKPIUseCase obtenerClientesKPIUseCase;
    @Autowired
    private ObtenerListaClientesUseCase obtenerListaClientesUseCase;

    @Test
    @DisplayName("POST /creacliente - exitosamente")
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
        ResponseEntity<Void> result = restTemplate.postForEntity(uri, entity, Void.class);

        // Assertions
        Assertions.assertEquals(HttpStatus.CREATED, result.getStatusCode());
        Assertions.assertTrue(Objects.requireNonNull(result.getHeaders().getLocation()).getPath().contains("/consultacliente/"));
    }

    @Test
    @DisplayName("POST /creacliente - No Crea Cliente por ser menor de edad")
    void deberia_obtener_bad_request_por_edad() throws URISyntaxException {
        // Prepare Data
        final String baseUrl = "http://localhost:" + randomServerPort + "/creacliente";
        URI uri = new URI(baseUrl);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        var crearClienteDto = TestUtilsFactory.buildCrearClienteDto();
        crearClienteDto.setEdad(12);

        HttpEntity<CrearClienteDto> entity = new HttpEntity<>(crearClienteDto, headers);
        reset(obtenerClientesKPIUseCase, obtenerListaClientesUseCase);
        // Execution
        ResponseEntity<Object> result = restTemplate.postForEntity(uri, entity, Object.class);

        // Assertions
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
        Assertions.assertTrue(Objects.requireNonNull(result.getBody()).toString().contains("cliente debe ser mayor de edad"));
    }

    @Test
    @DisplayName("POST /creacliente - No Crea Cliente por ser menor de edad y nombre invalido")
    void deberia_obtener_bad_request_por_edad_y_nombre() throws URISyntaxException {
        // Prepare Data
        final String baseUrl = "http://localhost:" + randomServerPort + "/creacliente";
        URI uri = new URI(baseUrl);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        var crearClienteDto = TestUtilsFactory.buildCrearClienteDto();
        crearClienteDto.setEdad(12);
        crearClienteDto.setNombre("213123");

        HttpEntity<CrearClienteDto> entity = new HttpEntity<>(crearClienteDto, headers);
        // Execution
        ResponseEntity<Object> result = restTemplate.postForEntity(uri, entity, Object.class);

        // Assertions
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
        var body = result.getBody();
        Assertions.assertTrue(Objects.requireNonNull(body).toString().contains("cliente debe ser mayor de edad"));
        Assertions.assertTrue(Objects.requireNonNull(body).toString().contains("nombre invalido"));
    }

    @Test
    @DisplayName("POST /creacliente - No Crea Cliente por ser menor de edad")
    void deberia_obtener_bad_request_por_fechanacimiento_invalida() throws URISyntaxException {
        // Prepare Data
        final String baseUrl = "http://localhost:" + randomServerPort + "/creacliente";
        URI uri = new URI(baseUrl);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        var crearClienteDto = TestUtilsFactory.buildCrearClienteDto();
        var crearClienteDtoStr = asJsonString(crearClienteDto).replace("06", "16");

        HttpEntity<String> entity = new HttpEntity<>(crearClienteDtoStr, headers);
        reset(obtenerClientesKPIUseCase, obtenerListaClientesUseCase);
        // Execution
        ResponseEntity<Object> result = restTemplate.postForEntity(uri, entity, Object.class);

        // Assertions
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
        Assertions.assertTrue(Objects.requireNonNull(result.getBody()).toString().contains("Invalid value for MonthOfYear (valid values 1 - 12): 16"));

    }



}
