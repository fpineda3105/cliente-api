package pe.com.fpineda.challenge.cliente.infrastructure.adapter.web;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import pe.com.fpineda.challenge.cliente.BaseTest;
import pe.com.fpineda.challenge.cliente.core.command.CrearClientCommand;
import pe.com.fpineda.challenge.cliente.core.usecase.CrearClienteUseCase;
import pe.com.fpineda.challenge.cliente.infrastructure.adapter.web.controller.ClienteController;
import pe.com.fpineda.challenge.cliente.util.TestUtilsFactory;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

/**
 * @author fpineda
 */

@WebMvcTest(value = ClienteController.class)
@ExtendWith(SpringExtension.class)
class ClienteControllerTest extends BaseTest {

    private static final String BASE_PATH = "/creacliente";

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private CrearClienteUseCase crearClienteApiMocked;

    @TestConfiguration
    @ComponentScan(basePackages = "pe.com.fpineda.challenge.cliente.infrastructure.adapter.web")
    @EnableAutoConfiguration(exclude = DataSourceAutoConfiguration.class)
    static class Configuration {

    }

    @Test
    void should_crear_cliente_exitosamente() throws Exception {
        // Prepare data and mocks
        var crearClienteDto = TestUtilsFactory.buildCrearClienteDto();
        var cliente = TestUtilsFactory.buildCliente();
        Mockito.doReturn(cliente).when(crearClienteApiMocked).create(Mockito.any(CrearClientCommand.class));
        var locationExpected = "/consultacliente" + "/" + cliente.getId();

        // Execute
        var content = asJsonString(crearClienteDto);
        var mvcResult = mockMvc.perform(post(BASE_PATH).contentType(MediaType.APPLICATION_JSON).content(content)).andReturn();

        // Assertions
        var headerLocation = mvcResult.getResponse().getHeader("Location");

        assert headerLocation != null;
        verify(crearClienteApiMocked, times(1)).create(Mockito.any(CrearClientCommand.class));
        Assertions.assertTrue(headerLocation.contains(locationExpected));
    }


}
