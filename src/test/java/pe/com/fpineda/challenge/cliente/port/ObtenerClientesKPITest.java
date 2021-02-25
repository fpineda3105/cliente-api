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
import pe.com.fpineda.challenge.cliente.core.port.ObtenerClientesKPIPort;
import pe.com.fpineda.challenge.cliente.infrastructure.adapter.persistence.ClienteJdbcAdapter;

import javax.sql.DataSource;

/**
 * @author fpineda
 */
@SpringBootTest
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@ContextConfiguration(classes = AppConfigTests.class)
public class ObtenerClientesKPITest {

    private ObtenerClientesKPIPort port;

    @Autowired
    private DataSource dataSource;

    @BeforeEach
    void setup() {
        port = new ClienteJdbcAdapter(dataSource);
    }

    @Test
    @Sql(scripts = "/schema.sql")
    @DisplayName("0 Clientes - Obtener clientes KPI AVG = 0.0 y STDEV = 0.0")
    void deberia_obtener_clientes_kpi_0() {
        // Prepare data

        // Execution
        var result = this.port.obtenerClientesKPI();

        // Assertions
        Assertions.assertEquals(0.0, result.getDesviacionEstandar());
        Assertions.assertEquals(0.0, result.getPromedioEdad());

    }

    @Test
    @Sql(scripts = {"/schema.sql", "/insert_3_rows.sql"})
    @DisplayName("3 Clientes - Obtener clientes KPI AVG = 30.0 y STDEV = 10.0")
    void deberia_obtener_clientes_kpi_10_30() {
        // Prepare data

        // Execution
        var result = this.port.obtenerClientesKPI();

        // Assertions
        Assertions.assertEquals(10.0, result.getDesviacionEstandar());
        Assertions.assertEquals(30.0, result.getPromedioEdad());
    }

}
