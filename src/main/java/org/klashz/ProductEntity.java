package org.klashz;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
public class ProductEntity extends PanacheEntityBase {
    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    public UUID id;
    @NotBlank (message = "El nombre no puede estar vacio")
    public String name;
    @NotBlank (message = "La descripcion no puede ser vacia")
    public String description;
    @NotBlank (message = "El precio no puede ser nulo")
    public BigDecimal price;
    public int stock;
}
