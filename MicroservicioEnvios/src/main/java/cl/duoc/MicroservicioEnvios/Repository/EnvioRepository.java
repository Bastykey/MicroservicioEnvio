package cl.duoc.MicroservicioEnvios.Repository;

import cl.duoc.MicroservicioEnvios.Model.Envio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EnvioRepository extends JpaRepository<Envio, Long> {
    Optional<Envio> findByCodigoPedido(String codigoPedido);
    void deleteByCodigoPedido(String codigoPedido);
}

