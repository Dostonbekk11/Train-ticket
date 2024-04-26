package org.example.railwayticketbooking.dto;

import lombok.*;
import org.example.railwayticketbooking.model.Role;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SignupDto {
    private String username;
    private String email;
    private String password;
    private List<Role> roles;

}
