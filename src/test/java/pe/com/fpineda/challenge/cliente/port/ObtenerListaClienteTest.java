package pe.com.fpineda.challenge.cliente.port;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import pe.com.fpineda.challenge.cliente.config.AppConfigTests;
import pe.com.fpineda.challenge.cliente.core.port.ObtenerListaClientePort;
import pe.com.fpineda.challenge.cliente.infrastructure.adapter.persistence.ClienteJdbcAdapter;

import javax.sql.DataSource;

/**
 * @author fpineda
 */
@SpringBootTest
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@ContextConfiguration(classes = AppConfigTests.class)
public class ObtenerListaClienteTest {

    private ObtenerListaClientePort port;

    @Autowired
    private DataSource dataSource;

    @BeforeEach
    void setup() {
        port = new ClienteJdbcAdapter(dataSource);
    }

    @Test
    @Sql(scripts = "/schema.sql")
    void deberia_obtener_lista_clientes_vacia() {
        // Prepare data

        // Execution
        var result = this.port.obtenerLista();

        // Assertions
        Assertions.assertEquals(0, result.size());

    }

    @Test
    @Sql(scripts = {"/schema.sql", "/insert_3_rows.sql"})
    void deberia_obtener_lista_con_3_clientes() {
        // Prepare data

        // Execution
        var result = this.port.obtenerLista();

        // Assertions
        Assertions.assertEquals(3, result.size());

    }
}
