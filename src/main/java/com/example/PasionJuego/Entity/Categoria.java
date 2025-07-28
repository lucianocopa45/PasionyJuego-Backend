package com.example.PasionJuego.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Categoria{

    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    private Long id_categoria;

    private String nombre;
    private String descripcion;

}
