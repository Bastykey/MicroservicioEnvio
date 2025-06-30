package cl.duoc.MicroservicioEnvios.Assembler;

import cl.duoc.MicroservicioEnvios.Controller.EnvioController;
import cl.duoc.MicroservicioEnvios.DTO.EnvioDTO;
import cl.duoc.MicroservicioEnvios.Model.Envio;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

@Component
public class EnvioAssembler implements RepresentationModelAssembler<Envio, EntityModel<EnvioDTO>> {

    @Override
    public EntityModel<EnvioDTO> toModel(Envio envio) {
        EnvioDTO dto = new EnvioDTO(
                envio.getCodigoPedido(),
                envio.getDireccionDestino(),
                envio.getEstado(),
                envio.getFechaEnvio()
        );

        return EntityModel.of(dto,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(EnvioController.class)
                        .buscarPorCodigo(envio.getCodigoPedido())).withSelfRel(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(EnvioController.class)
                        .obtenerTodos()).withRel("envios"));
    }
}