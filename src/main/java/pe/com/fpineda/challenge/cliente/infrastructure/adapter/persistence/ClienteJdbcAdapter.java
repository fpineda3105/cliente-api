package pe.com.fpineda.challenge.cliente.infrastructure.adapter.persistence;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import pe.com.fpineda.challenge.cliente.core.command.CrearClientCommand;
import pe.com.fpineda.challenge.cliente.core.model.Cliente;
import pe.com.fpineda.challenge.cliente.core.model.ClientesKPI;
import pe.com.fpineda.challenge.cliente.core.port.CrearClientePort;
import pe.com.fpineda.challenge.cliente.core.port.ObtenerClientesKPIPort;
import pe.com.fpineda.challenge.cliente.core.port.ObtenerListaClientePort;
import pe.com.fpineda.challenge.cliente.util.FechaUtils;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author fpineda
 */
public class ClienteJdbcAdapter implements CrearClientePort, ObtenerListaClientePort, ObtenerClientesKPIPort {

    private static final String SELECCIONA_LISTA_CLIENTES = "SELECT * FROM CLIENTE";
    private static final String SELECIONA_CLIENTES_KPI = "SELECT STDDEV_SAMP(edad) as desviacionEstandar, AVG(edad) as promedioEdad FROM CLIENTE";


    private final SimpleJdbcInsert simpleJdbcInsert;
    private final JdbcTemplate jdbcTemplate;

    public ClienteJdbcAdapter(final DataSource dataSource) {
        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource).withTableName("cliente").usingGeneratedKeyColumns("id");
        this.jdbcTemplate = new JdbcTemplate(dataSource);

    }

    @Override
    public Cliente create(final CrearClientCommand command) {
        var cliente = command.toCliente();
        cliente.setFechaMuertePosible(FechaUtils.generarPosibleFechaMuerte(cliente.getEdad()));

        Map<String, Object> parameters = new HashMap<>(4);
        parameters.put("nombre", cliente.getNombre());
        parameters.put("apellido", cliente.getApellido());
        parameters.put("edad", cliente.getEdad());
        parameters.put("fecha_nacimiento", cliente.getFechaNacimiento());
        parameters.put("fecha_posible_muerte", cliente.getFechaMuertePosible());

        var id = simpleJdbcInsert.executeAndReturnKey(parameters).longValue();
        cliente.setId(id);

        return cliente;
    }

    @Override
    public List<Cliente> obtenerListaClientes() {
        return this.jdbcTemplate.query(SELECCIONA_LISTA_CLIENTES, new RowClienteMapper());
    }


    @Override
    public ClientesKPI obtenerClientesKPI() {
        return this.jdbcTemplate.queryForObject(SELECIONA_CLIENTES_KPI, new ClientesKPIMapper());
    }
}
