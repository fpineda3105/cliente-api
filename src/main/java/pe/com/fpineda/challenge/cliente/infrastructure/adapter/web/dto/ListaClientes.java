package pe.com.fpineda.challenge.cliente.infrastructure.adapter.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import pe.com.fpineda.challenge.cliente.core.model.Cliente;

import java.util.List;

/**
 * @author fpineda
 */
@AllArgsConstructor
@ToString
@Getter
public class ListaClientes {

    @JsonProperty("result")
    private List<Cliente> lista;
}
