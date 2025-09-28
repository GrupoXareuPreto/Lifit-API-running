package br.com.xareu.lift.Controller;

import br.com.xareu.lift.DTO.Usuario.UsuarioRequestAutenticarDTO;
import br.com.xareu.lift.DTO.Usuario.UsuarioRequestDTO;
import br.com.xareu.lift.DTO.Usuario.UsuarioResponseDTO;
import br.com.xareu.lift.Service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

@RestController
@RequestMapping("/usuario")
public class    UsuarioController {

    private final UsuarioService service;

    @Autowired
    public UsuarioController(UsuarioService service){
        this.service = service;
    }



    @GetMapping
    public ResponseEntity<List<UsuarioResponseDTO>> listarTodos() {
        List<UsuarioResponseDTO> usuarios = service.getAll();
        return ResponseEntity.ok(usuarios);

    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> buscarPorId(@PathVariable Long id){
         UsuarioResponseDTO usuario = service.buscarPorId(id);
         if (usuario != null){
             return ResponseEntity.ok(usuario);
         }
         else {
             return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
         }
    }


    @PostMapping
    public ResponseEntity<UsuarioResponseDTO> criarUsuario(@Valid @RequestBody UsuarioRequestDTO usuarioDTO){
        try{
            UsuarioResponseDTO novoUsuario = service.criarUsuario(usuarioDTO);
            return new ResponseEntity<>(novoUsuario, HttpStatus.CREATED);
        }
        catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> atualizarUsuario(@Valid @RequestBody UsuarioRequestDTO usuarioDTO,  @PathVariable Long id){
        return service.atualizarUsuario(usuarioDTO, id).map(ResponseEntity :: ok).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarUsuario(@PathVariable Long id){
        boolean deletado = service.deletarUsuario(id);

        if(deletado){
            return ResponseEntity.noContent().build();
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/autenticar")
    public ResponseEntity<Void> autenticarUsuario(@Valid @RequestBody UsuarioRequestAutenticarDTO credenciais){
        if (service.autenticarUsuario(credenciais)){
            return ResponseEntity.ok().build();
        }else {
            return ResponseEntity.badRequest().build();
        }
    }


}
