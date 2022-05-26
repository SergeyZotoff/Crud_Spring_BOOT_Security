package SpringBoot.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "roles")
public class Role implements GrantedAuthority {
    @Id
    @GeneratedValue
    private long id;

    @Column
    private String role;

    @Override
    public String getAuthority() {
        return getRole();
    }

    public Role(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return  role;
    }
}