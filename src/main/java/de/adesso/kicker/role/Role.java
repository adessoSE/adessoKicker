package de.adesso.kicker.role;

import java.io.Serializable;
import javax.persistence.*;

@Entity
@Table(name = "role")
public class Role implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long roleId;

    private String role;
}
