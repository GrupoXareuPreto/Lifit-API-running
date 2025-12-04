package br.com.xareu.lift.Service;

import br.com.xareu.lift.DTO.Usuario.*;
import br.com.xareu.lift.Entity.Evento;
import br.com.xareu.lift.Entity.Postagem;
import br.com.xareu.lift.Entity.Usuario;
import br.com.xareu.lift.Mapper.UsuarioMapper;
import br.com.xareu.lift.Repository.EventoRepository;
import br.com.xareu.lift.Repository.PostagemRepository;
import br.com.xareu.lift.Repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository repository;
    @Autowired
    private  PasswordEncoder passwordEncoder;
    @Autowired
    private  UsuarioMapper mapper;
    @Autowired
    private PostagemRepository postagemRepository;
    @Autowired
    private EventoRepository eventoRepository;


    public List<UsuarioResponsePerfilDTO> getAll() {
        List<Usuario> usuarios = repository.findAll();
        return mapper.toPerfilResponseList(usuarios);
    }

    public UsuarioResponsePerfilDTO buscarPorId(Long id){
        Usuario usuario = repository.findById(id).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        return mapper.toResponsePerfilDTO(usuario);
    }

    public UsuarioResponsePerfilDTO buscaPorNomeUsuario(String nomeUsuario){
        Usuario usuario = repository.findByNomeUsuario(nomeUsuario).orElseThrow(() -> new RuntimeException("Usuario nao encontrado"));
        return mapper.toResponsePerfilDTO(usuario);
    }

    public UsuarioResponsePerfilDTO buscaPorEmail(String email){
        Usuario usuario = repository.findByEmail(email).orElseThrow(() -> new RuntimeException("Usuario nao encontrado"));
        return mapper.toResponsePerfilDTO(usuario);
    }



    @Transactional
    public UsuarioResponsePerfilDTO criarUsuario(UsuarioRequestDTO usuarioDTO) {
        if (repository.findByEmail(usuarioDTO.email()).isPresent()) {
            throw new IllegalArgumentException("Email já existe");
        }
        if (repository.findByNomeUsuario(usuarioDTO.nomeUsuario()).isPresent()) {
            throw new IllegalArgumentException("Nome de Usuario já existe");
        }

        Usuario usuario = mapper.toEntity(usuarioDTO);
        usuario.setSenha(passwordEncoder.encode(usuarioDTO.senha()));
        Usuario usuarioSalvo = repository.save(usuario);
        return  mapper.toResponsePerfilDTO(usuarioSalvo);
    }



    @Transactional
    public UsuarioResponsePerfilDTO atualizarUsuarioLogado(UsuarioRequestAtualizarDTO usuarioAtualizadoDTO, Usuario usuarioLogado) {
        // O usuarioLogado já é a entidade que queremos atualizar.
        usuarioLogado.setNome(usuarioAtualizadoDTO.nome());
        usuarioLogado.setNomeUsuario(usuarioAtualizadoDTO.nomeUsuario());
        usuarioLogado.setBiografia(usuarioAtualizadoDTO.biografia());
        usuarioLogado.setEmail(usuarioAtualizadoDTO.email());
        usuarioLogado.setFotoPerfil(usuarioAtualizadoDTO.fotoPerfil());

        // A senha só deve ser atualizada se for explicitamente fornecida.
        if (usuarioAtualizadoDTO.senha() != null && !usuarioAtualizadoDTO.senha().isEmpty()) {
            usuarioLogado.setSenha(passwordEncoder.encode(usuarioAtualizadoDTO.senha()));
        }

        Usuario usuarioAtualizado = repository.save(usuarioLogado);
        return  mapper.toResponsePerfilDTO(usuarioAtualizado); // Supondo que você tenha um ` toResponsePerfilDTO`
    }

    @Transactional
    public void deletarUsuarioLogado(Usuario usuarioLogado) {
        // Simplesmente deleta o usuário que foi passado (que veio do token).
        repository.deleteById(usuarioLogado.getId());
    }

    public Optional<UsuarioResponsePerfilDTO> autenticarUsuario(UsuarioRequestAutenticarDTO credenciais){
        try{
            Optional<Usuario> usuarioOptional = repository.findByNomeUsuario(credenciais.nomeUsuarioEmail());
            if (usuarioOptional.isEmpty()){
                return Optional.empty();
            }
            Usuario usuario = usuarioOptional.get();
            if(passwordEncoder.matches(credenciais.senha(), usuario.getSenha())){
                return Optional.of( mapper.toResponsePerfilDTO(usuario));
            }
            return Optional.empty();

        }catch (Exception ex){
            System.out.println("ERRO"+ex.getMessage());
            return Optional.empty();
        }

    }
}
