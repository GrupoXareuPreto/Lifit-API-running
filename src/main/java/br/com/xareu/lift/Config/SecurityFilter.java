// src/main/java/br/com/xareu/lift/Config/SecurityFilter.java (ou em um pacote de segurança)
package br.com.xareu.lift.Config;

import br.com.xareu.lift.Repository.UsuarioRepository;
import br.com.xareu.lift.Service.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component // Marca esta classe como um componente gerenciado pelo Spring
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // Tenta recuperar o token do cabeçalho da requisição
        var token = this.recoverToken(request);

        if (token != null) {
            // Valida o token e obtém o email do usuário
            var email = tokenService.validateToken(token);
            System.out.println("SecurityFilter - Email do token: " + email);
            
            // Busca o usuário no banco de dados pelo email
            UserDetails user = (UserDetails) usuarioRepository.findByEmail(email).orElse(null);
            System.out.println("SecurityFilter - Usuário encontrado: " + (user != null ? "SIM" : "NÃO"));

            if (user != null) {
                // Se o usuário for encontrado, cria um objeto de autenticação
                var authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                // Define o usuário como autenticado no contexto de segurança do Spring
                SecurityContextHolder.getContext().setAuthentication(authentication);
                System.out.println("SecurityFilter - Usuário autenticado com sucesso");
            } else {
                System.out.println("SecurityFilter - ERRO: Usuário não encontrado no banco para email: " + email);
            }
        }
        // Continua a cadeia de filtros
        filterChain.doFilter(request, response);
    }

    // Metodo auxiliar para extrair o token do cabeçalho "Authorization"
    private String recoverToken(HttpServletRequest request) {
        var authHeader = request.getHeader("Authorization");
        if (authHeader == null) return null;
        // O token JWT vem após "Bearer "
        return authHeader.replace("Bearer ", "");
    }
}