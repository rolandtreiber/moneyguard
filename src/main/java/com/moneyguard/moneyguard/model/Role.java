package com.moneyguard.moneyguard.model;
import org.hibernate.annotations.Type;
import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "roles")
public class Role {

    @Id
    @Type(type = "uuid-char")
    private final UUID id = UUID.randomUUID();
    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private ERole name;
    public Role() {
    }
    public Role(ERole name) {
        this.name = name;
    }

    public UUID getId() {
        return id;
    }

    public ERole getName() {
        return name;
    }
    public void setName(ERole name) {
        this.name = name;
    }

}