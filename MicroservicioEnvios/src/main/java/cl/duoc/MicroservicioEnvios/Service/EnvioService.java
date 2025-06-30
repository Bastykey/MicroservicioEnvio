package cl.duoc.MicroservicioEnvios.Service;

import cl.duoc.MicroservicioEnvios.Model.Envio;
import cl.duoc.MicroservicioEnvios.Repository.EnvioRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class EnvioService {

    @Autowired
    private EnvioRepository envioRepository;

    public List<Envio> buscarTodos() {
        return envioRepository.findAll();
    }

    public Optional<Envio> buscarPorCodigo(String codigoPedido) {
        return envioRepository.findByCodigoPedido(codigoPedido);
    }

    public Envio guardar(Envio envio) {
        return envioRepository.save(envio);
    }

    public Envio actualizar(Long id, Envio datos) {
        Envio existente = envioRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Envio no encontrado con ID: " + id));
        existente.setCodigoPedido(datos.getCodigoPedido());
        existente.setDireccionDestino(datos.getDireccionDestino());
        existente.setEstado(datos.getEstado());
        existente.setFechaEnvio(datos.getFechaEnvio());
        return envioRepository.save(existente);
    }

    public void eliminarPorCodigo(String codigoPedido) {
        envioRepository.deleteByCodigoPedido(codigoPedido);
    }
}