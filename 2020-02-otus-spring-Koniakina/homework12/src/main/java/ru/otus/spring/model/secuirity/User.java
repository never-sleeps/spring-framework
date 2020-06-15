package ru.otus.spring.model.secuirity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import ru.otus.spring.security.Role;

import javax.validation.constraints.NotBlank;
import java.util.Set;

@Data
@NoArgsConstructor
@Document(collection = "user")
public class User {

    @Id
    private String id;

    @Field
    @NotBlank
    private String username;

    @Field
    @NotBlank
    private String password;

    @Field
    @NotBlank
    private Set<Role> roles;

    public User(@NotBlank String username, @NotBlank String password, @NotBlank Set<Role> roles) {
        this.username = username;
        this.password = password;
        this.roles = roles;
    }
}
