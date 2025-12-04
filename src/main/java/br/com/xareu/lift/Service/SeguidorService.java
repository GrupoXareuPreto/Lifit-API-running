package br.com.xareu.lift.Service;

import br.com.xareu.lift.Entity.Seguidor;
import br.com.xareu.lift.Entity.Usuario;
import br.com.xareu.lift.Repository.SeguidorRepository;
import br.com.xareu.lift.Repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SeguidorService {

    private final SeguidorRepository seguidorRepository;
    private final UsuarioRepository usuarioRepository;

    @Autowired
    public SeguidorService(SeguidorRepository seguidorRepository, UsuarioRepository usuarioRepository) {
        this.seguidorRepository = seguidorRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @Transactional
    public void seguir(Long seguidoId, Usuario seguidor) {
        Usuario seguido = usuarioRepository.findById(seguidoId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        if (seguidor.getId().equals(seguidoId)) {
            throw new IllegalArgumentException("Você não pode seguir a si mesmo");
        }

        if (seguidorRepository.existsBySeguidorAndSeguido(seguidor, seguido)) {
            throw new IllegalStateException("Você já segue este usuário");
        }

        Seguidor novoSeguidor = new Seguidor();
        novoSeguidor.setSeguidor(seguidor);
        novoSeguidor.setSeguido(seguido);

        seguidorRepository.save(novoSeguidor);
    }

    @Transactional
    public void deixarDeSeguir(Long seguidoId, Usuario seguidor) {
        Usuario seguido = usuarioRepository.findById(seguidoId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        Seguidor relacao = seguidorRepository.findBySeguidorAndSeguido(seguidor, seguido)
                .orElseThrow(() -> new RuntimeException("Você não segue este usuário"));

        seguidorRepository.delete(relacao);
    }

    public long contarSeguidores(Long usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        return seguidorRepository.countBySeguido(usuario);
    }

    public long contarSeguindo(Long usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        return seguidorRepository.countBySeguidor(usuario);
    }

    public boolean verificarSeSegue(Usuario seguidor, Long seguidoId) {
        Usuario seguido = usuarioRepository.findById(seguidoId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        return seguidorRepository.existsBySeguidorAndSeguido(seguidor, seguido);
    }
}
