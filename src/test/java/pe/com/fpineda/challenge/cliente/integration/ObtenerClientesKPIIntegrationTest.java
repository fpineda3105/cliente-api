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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import pe.com.fpineda.challenge.cliente.BaseTest;
import pe.com.fpineda.challenge.cliente.api.ObtenerClientesKPIApi;
import pe.com.fpineda.challenge.cliente.api.ObtenerListaClienteApi;
import pe.com.fpineda.challenge.cliente.config.AppConfigTests;
import pe.com.fpineda.challenge.cliente.core.model.ClientesKPI;
import pe.com.fpineda.challenge.cliente.core.port.ObtenerClientesKPIPort;
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
@ContextConfiguration(classes = {AppConfigTests.class, ObtenerClientesKPIIntegrationTest.Configuration.class, ClienteController.class})
class ObtenerClientesKPIIntegrationTest extends BaseTest {

    static class Configuration {

        @Autowired
        public DataSource dataSource;

        @MockBean
        public CrearClienteUseCase crearClienteUseCase;

        @MockBean
        public ObtenerListaClientesUseCase obtenerListaClientesUseCase;

        @Bean
        public ObtenerClientesKPIPort obtenerClientesKPIPort(){
            return new ClienteJdbcAdapter(this.dataSource);
        }

        @Bean
        public ObtenerClientesKPIUseCase obtenerClientesKPIUseCase(){
            return new ObtenerClientesKPIApi(obtenerClientesKPIPort());
        }

    }

    @LocalServerPort
    private int randomServerPort;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    @DisplayName("GET /kpideclientes vacia - con 0.0 promedio edad y 0.0 desviacion estandar")
    @Sql(scripts = "/schema.sql")
    void deberia_obtener_kpi_de_clientes_vacia() throws URISyntaxException {
        // Prepare Data
        final String baseUrl = "http://localhost:" + randomServerPort + "/kpideclientes";
        URI uri = new URI(baseUrl);

        // Execution
        ResponseEntity<ClientesKPI> result = restTemplate.getForEntity(uri, ClientesKPI.class);

        // Assertions
        Assertions.assertEquals(HttpStatus.OK, result.getStatusCode());
        Assertions.assertEquals(0.0, Objects.requireNonNull(result.getBody()).getPromedioEdad());
        Assertions.assertEquals(0.0, Objects.requireNonNull(result.getBody()).getDesviacionEstandar());

    }

    @Test
    @DisplayName("GET /kpideclientes 3 clientes - con 30.0 promedio edad y 10.0 desviacion estandar")
    @Sql(scripts = {"/schema.sql", "/insert_3_rows.sql"})
    void deberia_obtener_kpi_de_3_clientes() throws URISyntaxException {
        // Prepare Data
        final String baseUrl = "http://localhost:" + randomServerPort + "/kpideclientes";
        URI uri = new URI(baseUrl);

        // Execution
        ResponseEntity<ClientesKPI> result = restTemplate.getForEntity(uri, ClientesKPI.class);

        // Assertions
        Assertions.assertEquals(HttpStatus.OK, result.getStatusCode());
        Assertions.assertEquals(30.0, Objects.requireNonNull(result.getBody()).getPromedioEdad());
        Assertions.assertEquals(10.0, Objects.requireNonNull(result.getBody()).getDesviacionEstandar());

    }

}
