package pe.com.fpineda.challenge.cliente.infrastructure.adapter.persistence;

import org.springframework.jdbc.core.RowMapper;
import pe.com.fpineda.challenge.cliente.core.model.ClientesKPI;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author fpineda
 */
public class ClientesKPIMapper implements RowMapper<ClientesKPI> {

    @Override
    public ClientesKPI mapRow(ResultSet resultSet, int i) throws SQLException {
        return ClientesKPI.builder()
                .desviacionEstandar(resultSet.getDouble("desviacionEstandar"))
                .promedioEdad(resultSet.getDouble("promedioEdad"))
                .build();
    }
}
