package cl.duoc.MicroservicioEnvios.Controller;

import cl.duoc.MicroservicioEnvios.Assembler.EnvioAssembler;
import cl.duoc.MicroservicioEnvios.DTO.EnvioDTO;
import cl.duoc.MicroservicioEnvios.Model.Envio;
import cl.duoc.MicroservicioEnvios.Service.EnvioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/envios")
@Tag(name = "Envios", description = "Operaciones CRUD sobre el recurso Envio")
public class EnvioController {

    @Autowired
    private EnvioService envioService;

    @Autowired
    private EnvioAssembler envioAssembler;

    @Operation(summary = "Obtener todos los envíos")
    @GetMapping
    public CollectionModel<EntityModel<EnvioDTO>> obtenerTodos() {
        var envios = envioService.buscarTodos().stream()
                .map(envioAssembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(envios);
    }

    @Operation(summary = "Buscar envío por código de pedido")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Envío encontrado",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = EnvioDTO.class),
                examples = @ExampleObject(value = "{\"codigoPedido\":\"PED123\",\"direccionDestino\":\"Av. Siempre Viva 742\",\"estado\":\"en tránsito\",\"fechaEnvio\":\"2025-06-29T14:00:00\"}"))),
        @ApiResponse(responseCode = "404", description = "Envío no encontrado")
    })
    @GetMapping("/codigo/{codigo}")
    public ResponseEntity<EntityModel<EnvioDTO>> buscarPorCodigo(@PathVariable String codigo) {
        Optional<Envio> envioOpt = envioService.buscarPorCodigo(codigo);
        return envioOpt.map(e -> ResponseEntity.ok(envioAssembler.toModel(e)))
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Crear un nuevo envío")
    @PostMapping
    public ResponseEntity<EntityModel<EnvioDTO>> crear(@RequestBody Envio envio) {
        Envio nuevo = envioService.guardar(envio);
        return ResponseEntity.status(201).body(envioAssembler.toModel(nuevo));
    }

    @Operation(summary = "Actualizar un envío por ID")
    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<EnvioDTO>> actualizar(@PathVariable Long id, @RequestBody Envio envio) {
        Envio actualizado = envioService.actualizar(id, envio);
        return ResponseEntity.ok(envioAssembler.toModel(actualizado));
    }

}