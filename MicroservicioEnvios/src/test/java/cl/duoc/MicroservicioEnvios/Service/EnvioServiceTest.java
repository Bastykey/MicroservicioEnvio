package cl.duoc.MicroservicioEnvios.Service;

import cl.duoc.MicroservicioEnvios.Model.Envio;
import cl.duoc.MicroservicioEnvios.Repository.EnvioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EnvioServiceTest {

    private EnvioRepository envioRepository;
    private EnvioService envioService;

    @BeforeEach
    void setUp() {
        envioRepository = mock(EnvioRepository.class);
        envioService = new EnvioService();
        envioService = Mockito.spy(envioService);
        try {
            var field = EnvioService.class.getDeclaredField("envioRepository");
            field.setAccessible(true);
            field.set(envioService, envioRepository);
        } catch (Exception e) {
            fail("Error: No se pudo configurar el repositorio mock: " + e.getMessage());
        }
    }

    @Test
    void testGuardarEnvio() {
        Envio envio = new Envio(null, "Pedido1", "Av. Siempre Viva 123", "pendiente", LocalDateTime.now());
        Envio guardado = new Envio(1L, "Pedido1", "Av. Siempre Viva 123", "pendiente", LocalDateTime.now());

        when(envioRepository.save(envio)).thenReturn(guardado);

        Envio resultado = envioService.guardar(envio);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("Pedido1", resultado.getCodigoPedido());
        verify(envioRepository, times(1)).save(envio);
    }
}