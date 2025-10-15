// src/main/java/br/com/xareu/lift/Controller/AuthenticationController.java
package br.com.xareu.lift.Controller;

import br.com.xareu.lift.DTO.LoginResponseDTO;
import br.com.xareu.lift.DTO.Usuario.UsuarioRequestAutenticarDTO;
import br.com.xareu.lift.Entity.Usuario;
import br.com.xareu.lift.Service.TokenService; // Importe o TokenService
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService; // Injeta o TokenService

    @Autowired
    public AuthenticationController(AuthenticationManager authenticationManager, TokenService tokenService) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService; // Atribui o TokenService
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid UsuarioRequestAutenticarDTO data) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.getNomeUsuarioEmail(), data.getSenha());
        var auth = this.authenticationManager.authenticate(usernamePassword);

        // Se a autenticação for bem-sucedida, gera o token JWT
        var token = tokenService.generateToken((Usuario) auth.getPrincipal());

        // Retorna o token no corpo da resposta
        return ResponseEntity.ok(new LoginResponseDTO(token));
    }
}