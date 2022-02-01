package pl.pwr.dissertation.persistance.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Role implements GrantedAuthority {

    public static final String STUDENT = "STUDENT";
    public static final String LECTURER = "LECTURER";
    public static final String PROGRAMME_BOARD = "PROGRAMME_BOARD";

    private String authority;
}
