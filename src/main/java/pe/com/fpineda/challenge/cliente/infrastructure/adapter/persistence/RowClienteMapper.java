package pe.com.fpineda.challenge.cliente.infrastructure.adapter.persistence;

import org.springframework.jdbc.core.RowMapper;
import pe.com.fpineda.challenge.cliente.core.model.Cliente;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;

/**
 * @author fpineda
 */
public class RowClienteMapper implements RowMapper<Cliente> {
    @Override
    public Cliente mapRow(ResultSet resultSet, int i) throws SQLException {

        LocalDate fechaNacimiento = resultSet.getDate("fecha_nacimiento").toLocalDate();
        LocalDate fechaPosibleMuerte = resultSet.getDate("fecha_posible_muerte").toLocalDate();

        return Cliente.builder()
                .id(resultSet.getLong("id"))
                .nombre(resultSet.getString("nombre"))
                .apellido(resultSet.getString("apellido"))
                .edad(resultSet.getInt("edad"))
                .fechaNacimiento(fechaNacimiento)
                .fechaMuertePosible(fechaPosibleMuerte).build();
    }
}
