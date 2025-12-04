package br.com.xareu.lift.Service;

import br.com.xareu.lift.Entity.Usuario;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.hibernate.annotations.SecondaryRow;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {


    // Injeta o valor da chave secreta do application.properties
    @Value("${api.security.token.secret}")
    private String secret;

    // Metodo para gerar um token JWT para um usuário
    public String generateToken(Usuario usuario) {
        try {
            // Define o algoritmo de assinatura usando a chave secreta
            Algorithm algorithm = Algorithm.HMAC256(secret);
            String token = JWT.create()
                    .withIssuer("lift-api") // Emissor do token
                    .withSubject(usuario.getEmail()) // Assunto do token (identificador do usuário)
                    .withExpiresAt(genExpirationDate()) // Define a data de expiração
                    .sign(algorithm); // Assina o token
            return token;
        } catch (JWTCreationException exception) {
            throw new RuntimeException("Erro ao gerar o token JWT", exception);
        }
    }

    // Metodo para validar um token JWT e retornar o identificador do usuário
    public String validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            String subject = JWT.require(algorithm)
                    .withIssuer("lift-api") // Verifica se o emissor é o mesmo
                    .build()
                    .verify(token) // Verifica a assinatura e a validade do token
                    .getSubject(); // Retorna o assunto (email do usuário)
            
            System.out.println("TokenService - Token validado com sucesso. Email: " + subject);
            return subject;
        } catch (JWTVerificationException exception) {
            System.out.println("TokenService - ERRO ao validar token: " + exception.getMessage());
            exception.printStackTrace();
            return ""; // Retorna vazio se o token for inválido
        }
    }

    // Metodo para gerar a data de expiração do token (ex: 2 horas a partir de agora)
    private Instant genExpirationDate() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
}