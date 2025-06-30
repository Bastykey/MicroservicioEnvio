package cl.duoc.MicroservicioEnvios.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EnvioDTO {
    private String codigoPedido;
    private String direccionDestino;
    private String estado;
    private LocalDateTime fechaEnvio;
}