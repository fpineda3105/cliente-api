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
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import pe.com.fpineda.challenge.cliente.TestBase;
import pe.com.fpineda.challenge.cliente.api.ObtenerListaClienteApi;
import pe.com.fpineda.challenge.cliente.config.TestAppConfig;
import pe.com.fpineda.challenge.cliente.core.port.ObtenerListaClientePort;
import pe.com.fpineda.challenge.cliente.core.usecase.CrearClienteUseCase;
import pe.com.fpineda.challenge.cliente.core.usecase.ObtenerClientesKPIUseCase;
import pe.com.fpineda.challenge.cliente.core.usecase.ObtenerListaClientesUseCase;
import pe.com.fpineda.challenge.cliente.infrastructure.adapter.persistence.ClienteJdbcAdapter;
import pe.com.fpineda.challenge.cliente.infrastructure.adapter.web.controller.ClienteController;
import pe.com.fpineda.challenge.cliente.infrastructure.adapter.web.dto.ListaClientes;

import javax.sql.DataSource;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Objects;

/**
 * @author fpineda
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@ContextConfiguration(classes = {TestAppConfig.class, ObtenerListaClientesIntegrationTest.Configuration.class, ClienteController.class})
class ObtenerListaClientesIntegrationTest extends TestBase {

    static class Configuration {

        @Autowired
        public DataSource dataSource;

        @MockBean
        public CrearClienteUseCase crearClienteUseCase;

        @MockBean
        public ObtenerClientesKPIUseCase obtenerClientesKPIUseCase;

        @Bean
        public ObtenerListaClientePort obtenerListaClientePort(){
            return new ClienteJdbcAdapter(this.dataSource);
        }

        @Bean
        public ObtenerListaClientesUseCase obtenerListaClientesUseCase(){
            return new ObtenerListaClienteApi(obtenerListaClientePort());
        }

    }

    @LocalServerPort
    private int randomServerPort;

    @Autowired
    private TestRestTemplate restTemplate;


    @Test
    @DisplayName("GET /listclientes - vacia con 0 clientes")
    @Sql(scripts = "/schema.sql")
    void deberia_obtener_lista_clientes_vacia() throws URISyntaxException {
        // Prepare Data
        final String baseUrl = "http://localhost:" + randomServerPort + "/listclientes";
        URI uri = new URI(baseUrl);

        // Execution
        ResponseEntity<ListaClientes> result = restTemplate.getForEntity(uri, ListaClientes.class);

        // Assertions
        Assertions.assertEquals(HttpStatus.OK, result.getStatusCode());
        Assertions.assertEquals(0, Objects.requireNonNull(result.getBody()).getLista().size());

    }

    @Test
    @DisplayName("GET /listclientes - con 3 clientes")
    @Sql(scripts = {"/schema.sql", "/insert_3_rows.sql"})
    void deberia_obtener_lista_con_3_clientes() throws URISyntaxException {
        // Prepare Data
        final String baseUrl = "http://localhost:" + randomServerPort + "/listclientes";
        URI uri = new URI(baseUrl);

        // Execution
        ResponseEntity<ListaClientes> result = restTemplate.getForEntity(uri, ListaClientes.class);

        // Assertions
        Assertions.assertEquals(HttpStatus.OK, result.getStatusCode());
        Assertions.assertEquals(3, Objects.requireNonNull(result.getBody()).getLista().size());

    }

}
