package br.com.xareu.lift.Service;

import br.com.xareu.lift.DTO.Resumos.*;
import br.com.xareu.lift.DTO.Normais.UsuarioDTO;
import br.com.xareu.lift.Entity.Usuario;
import br.com.xareu.lift.Repository.UsuarioRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    private UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    /*crud*/
    public List<Usuario> getAll() {
        return usuarioRepository.findAll();
    }

    //Esse metodo ta dando findAll, talvez não seja o mais performático
    public Usuario criarUsuario(Usuario usuario){
        List<Usuario> allUsers = usuarioRepository.findAll();
        if(allUsers.contains(usuario))
            return null;
        return usuarioRepository.save(usuario);
    }

    public Usuario buscarPorId(Long id){
            return usuarioRepository.findById(id).orElse(null);
    }

    public Usuario buscarPorEmailSenha(String email,String senha){
        return usuarioRepository.findByEmailContainingIgnoreCase(email);
    }

    public Optional<Usuario> atualizarUsuario(Usuario usuarioatualizado, Long id){
        return usuarioRepository.findById(id).map(usuario -> {
            usuario.setNome(usuarioatualizado.getNome());
            usuario.setEmail(usuarioatualizado.getEmail());
            usuario.setSenha(usuarioatualizado.getSenha());
            usuario.setNomeUsuario(usuarioatualizado.getNomeUsuario());
            usuario.setBiografia(usuarioatualizado.getBiografia());

            return usuarioRepository.save(usuario);
        });
    }

    public boolean deletarUsuario(Long id){
        if(usuarioRepository.existsById(id)){
            usuarioRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
