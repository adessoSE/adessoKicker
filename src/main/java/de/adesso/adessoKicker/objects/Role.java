package de.adesso.adessoKicker.objects;

import javax.annotation.sql.DataSourceDefinition;
import javax.persistence.*;

@Entity
@Table(name = "role")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long roleId;

    private String role;

}
