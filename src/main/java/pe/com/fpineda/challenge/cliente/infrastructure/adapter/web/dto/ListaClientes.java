package pe.com.fpineda.challenge.cliente.infrastructure.adapter.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

/**
 * @author fpineda
 */
@AllArgsConstructor
@ToString
@Getter
@Builder
public class ListaClientes {

    @JsonProperty("result")
    private List<ClienteDto> lista;

}
