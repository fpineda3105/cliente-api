package pe.com.fpineda.challenge.cliente.infrastructure.adapter.web;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import pe.com.fpineda.challenge.cliente.TestBase;
import pe.com.fpineda.challenge.cliente.core.command.CrearClientCommand;
import pe.com.fpineda.challenge.cliente.core.usecase.CrearClienteUseCase;
import pe.com.fpineda.challenge.cliente.core.usecase.ObtenerClientesKPIUseCase;
import pe.com.fpineda.challenge.cliente.core.usecase.ObtenerListaClientesUseCase;
import pe.com.fpineda.challenge.cliente.infrastructure.adapter.web.controller.ClienteController;
import pe.com.fpineda.challenge.cliente.util.TestUtilsFactory;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author fpineda
 */

@WebMvcTest(value = ClienteController.class)
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {ClienteControllerTestBase.Configuration.class, ClienteController.class})
class ClienteControllerTestBase extends TestBase {

    @EnableAutoConfiguration(exclude = DataSourceAutoConfiguration.class)
    static class Configuration {

    }

    private static final String BASE_PATH_CREA_CLIENTE = "/creacliente";
    private static final String BASE_PATH_LISTA_CLIENTES = "/listclientes";
    private static final String BASE_PATH_CLIENTES_KPI = "/kpideclientes";

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private CrearClienteUseCase crearClienteApiMocked;

    @MockBean
    public ObtenerClientesKPIUseCase obtenerClientesKPIApiMocked;

    @MockBean
    public ObtenerListaClientesUseCase obtenerListaClientesApiMocked;

    @Test
    @DisplayName("Post /cracliente de manera exitosa con Mocks")
    void deberia_crear_cliente_exitosamente() throws Exception {
        // Prepare data and mocks
        var crearClienteDto = TestUtilsFactory.buildCrearClienteDto();
        var cliente = TestUtilsFactory.buildCliente();
        Mockito.doReturn(cliente).when(crearClienteApiMocked).create(Mockito.any(CrearClientCommand.class));
        var locationExpected = "/consultacliente" + "/" + cliente.getId();

        // Execution
        var content = asJsonString(crearClienteDto);
        var mvcResult = mockMvc.perform(post(BASE_PATH_CREA_CLIENTE).contentType(MediaType.APPLICATION_JSON).content(content)).andReturn();

        // Assertions
        var headerLocation = mvcResult.getResponse().getHeader("Location");

        assert headerLocation != null;
        verify(crearClienteApiMocked, times(1)).create(Mockito.any(CrearClientCommand.class));
        Assertions.assertTrue(headerLocation.contains(locationExpected));
    }

    @Test
    @DisplayName("Get /listaclientes de manera exitosa con Mocks")
    void deberia_listar_2_clientes_exisotamente() throws Exception {
        // Prepare data and mocks
        var listaclientes = TestUtilsFactory.buildListaClientes();
        Mockito.doReturn(listaclientes).when(obtenerListaClientesApiMocked).obtenerLista();

        // Execution and Assertions
        mockMvc.perform(get(BASE_PATH_LISTA_CLIENTES)
        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result", hasSize(2)));

    }

    @Test
    @DisplayName("Get /kpideclientes de manera exitosa 10.0 y 30.0 con Mocks")
    void deberia_obtener_clientes_kpi_10_30() throws Exception {
        // Prepare data and mocks
        var clientesKPI = TestUtilsFactory.buildClientesKPI();
        Mockito.doReturn(clientesKPI).when(obtenerClientesKPIApiMocked).obtenerClientesKPI();


        // Execution and Assertions
        mockMvc.perform(get(BASE_PATH_CLIENTES_KPI)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.promedioEdad", "30.0").exists())
                .andExpect(jsonPath("$.desviacionEstandar", "10.0").exists());

    }



}
