package pe.com.fpineda.challenge.cliente.util;

import pe.com.fpineda.challenge.cliente.core.model.Cliente;
import pe.com.fpineda.challenge.cliente.infrastructure.adapter.web.dto.CrearClienteDto;

import java.time.LocalDate;

/**
 * @author fpineda
 */
public class TestUtilsFactory {

    public static CrearClienteDto buildCrearClienteDto() {

        return CrearClienteDto.builder().nombre("Andres").apellido("Gutierrez").edad(32).fechaNacimiento(LocalDate.of(1985, 6, 20)).build();
    }

    public static Cliente buildCliente() {
        return Cliente.builder().nombre("Fernando").apellido("Pineda").edad(32).fechaNacimiento(LocalDate.of(1985, 6, 20))
                .id(1L).fechaMuertePosible(LocalDate.of(2060, 3, 2)).build();
    }

}
