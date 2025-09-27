package br.com.xareu.lift.Service;

import br.com.xareu.lift.DTO.Usuario.UsuarioResponseCardConversaDTO;
import br.com.xareu.lift.DTO.Usuario.UsuarioRequestDTO;
import br.com.xareu.lift.DTO.Usuario.UsuarioResponseDTO;
import br.com.xareu.lift.DTO.Usuario.UsuarioResponseCardPostagemDTO;
import br.com.xareu.lift.Entity.Usuario;
import br.com.xareu.lift.Repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UsuarioService {

    private final UsuarioRepository repository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.repository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }
    /*--------------------------------------------------------------------------------------------------------------------*/
    /*Parte de DTOs*/
    private UsuarioResponseDTO toResponseDTO(Usuario usuario){
        if(usuario == null){
            return null;
        }
        return new UsuarioResponseDTO(
                usuario.getNome(),
                usuario.getBiografia(),
                usuario.getEmail(),
                usuario.getNomeUsuario()
        );
    }

    public UsuarioResponseCardPostagemDTO toUsuarioCardPostagemDTO(Usuario usuario){
        if(usuario == null){
            return null;
        }
        else {
            return new UsuarioResponseCardPostagemDTO(
                    usuario.getFotoPerfil(),
                    usuario.getNome(),
                    usuario.getNomeUsuario()
            );
        }
    }

    public UsuarioResponseCardConversaDTO toUsuarioCardConversaDTO(Usuario usuario){
        if(usuario == null){
            return null;
        }
        else {
            return new UsuarioResponseCardConversaDTO(
                    usuario.getFotoPerfil(),
                    usuario.getNome()
            );
        }
    }

    private Usuario toEntity(UsuarioRequestDTO dto){
        Usuario usuario = new Usuario();
        usuario.setNome(dto.getNome());
        usuario.setBiografia(dto.getBiografia());
        usuario.setEmail(dto.getEmail());
        usuario.setNomeUsuario(dto.getNomeUsuario());
        usuario.setSenha(passwordEncoder.encode(dto.getSenha()));
        return usuario;
    }
/*--------------------------------------------------------------------------------------------------------------------*/

    /*crud*/
    public List<UsuarioResponseDTO> getAll() {
        return repository.findAll().stream().map(this::toResponseDTO).collect(Collectors.toList());
    }

    public UsuarioResponseDTO criarUsuario(UsuarioRequestDTO usuarioDTO){
        if (repository.findByEmail(usuarioDTO.getEmail()).isPresent()){
            throw  new IllegalArgumentException("Email já existe");
        }
        if(repository.findByNomeUsuario(usuarioDTO.getNomeUsuario()).isPresent()){
            throw new IllegalArgumentException("Nome de Usuario já existe");
        }

        Usuario usuario = toEntity(usuarioDTO);
        Usuario usuarioSalvo = repository.save(usuario);
        return toResponseDTO(usuarioSalvo);
    }

    public UsuarioResponseDTO buscarPorId(Long id){
         return repository.findById(id).map(this :: toResponseDTO).orElse(null);
    }

    public Optional<UsuarioResponseDTO> atualizarUsuario(UsuarioRequestDTO usuarioatualizadoDTO, Long id){
        repository.findById(id).map(usarioExistente -> {
            usarioExistente.setNome(usuarioatualizadoDTO.getNome());
            usarioExistente.setNomeUsuario(usuarioatualizadoDTO.getNomeUsuario());
            usarioExistente.setBiografia(usuarioatualizadoDTO.getBiografia());
            usarioExistente.setEmail(usuarioatualizadoDTO.getEmail());

            if(usuarioatualizadoDTO.getSenha() != null && !usuarioatualizadoDTO.getSenha().isEmpty()){
                usarioExistente.setSenha(passwordEncoder.encode(usuarioatualizadoDTO.getSenha()));
            }

            Usuario usuarioupdated = repository.save(usarioExistente);
            return toResponseDTO(usuarioupdated);
        });
        return Optional.empty();

    }

    public boolean deletarUsuario(Long id){
        if(repository.existsById(id)){
            repository.deleteById(id);
            return true;
        }
        return false;
    }
}
