// src/main/java/br/com/xareu/lift/DTO/LoginResponseDTO.java
package br.com.xareu.lift.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponseDTO {
    private String token; // Aqui virá o token JWT após o login
}