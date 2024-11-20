package com.wavebank.wave.user;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.NaturalId;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {
    @Id
    //automatically generate ID value
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    //@NaturalId gives unique value, mutable = true needed so email cant be changed
    @NaturalId(mutable = true)
    private String email;
    private String password;
    private String role;
    //user initially can't be enabled thus false
    private boolean isEnabled = false;

}
