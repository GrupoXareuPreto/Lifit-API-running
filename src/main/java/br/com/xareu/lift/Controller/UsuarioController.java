package br.com.xareu.lift.Controller;

import br.com.xareu.lift.DTO.Normais.UsuarioDTO;
import br.com.xareu.lift.Entity.Usuario;
import br.com.xareu.lift.Service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuario")
public class    UsuarioController {

    @Autowired
    private UsuarioService service;

    @GetMapping
    public List<Usuario> listarTodos(){
        return service.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id){
         Usuario usuario = service.buscarPorId(id);

         if(usuario != null){
            return  ResponseEntity.ok(usuario);
         }
         else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario não encontrado");
         }
    }

    @GetMapping("/{email}/{senha}")
    public ResponseEntity<?> buscarPorEmailSenha(@PathVariable("email") String email, @PathVariable("senha") String senha){
        Usuario usuario = service.buscarPorEmailSenha(email,senha);

        if(usuario != null){
            return  ResponseEntity.ok(usuario);
        }
        else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario não encontrado");
        }
    }

    @PostMapping
    public Usuario criarUsuario(@RequestBody Usuario usuario){
        return service.criarUsuario(usuario);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizarUsuario(@RequestBody Usuario usuario,  @PathVariable Long id){
        return service.atualizarUsuario(usuario, id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletarUsuario(@PathVariable Long id){
        boolean deletado = service.deletarUsuario(id);

        if(deletado){
            return ResponseEntity.ok().body("Usuario deletado com sucesso");
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }
}
