package de.adesso.adessoKicker.objects;

import javax.annotation.sql.DataSourceDefinition;
import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "role")
public class Role implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long roleId;

    private String role;

}
