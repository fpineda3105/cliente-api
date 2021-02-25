package pe.com.fpineda.challenge.cliente.port;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import pe.com.fpineda.challenge.cliente.config.AppConfigTests;
import pe.com.fpineda.challenge.cliente.core.port.CrearClientePort;
import pe.com.fpineda.challenge.cliente.infrastructure.adapter.persistence.ClienteJdbcAdapter;
import pe.com.fpineda.challenge.cliente.util.TestUtilsFactory;

import javax.sql.DataSource;

/**
 * @author fpineda
 */
@SpringBootTest
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@ContextConfiguration(classes = AppConfigTests.class)
class CrearClienteTest {

    private CrearClientePort port;

    @Autowired
    private DataSource dataSource;

    @BeforeEach
    void setup() {
        port = new ClienteJdbcAdapter(dataSource);
    }

    @Test
    @Sql(scripts = "/schema.sql")
    @DisplayName("Creacion de cliente a traves de Port")
    void deberia_crear_cliente_exitosamente() {
        // Prepare data
        var clienteParaCrear = TestUtilsFactory.buildCrearClienteDto();

        // Execution
        var result = this.port.create(clienteParaCrear.toCommand());

        // Assertions
        Assertions.assertEquals(1L,result.getId());

    }

}
