package com.example.PasionJuego.DtoResponse;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductoDtoResponse {

    private Long id_producto;
    private String descripcion;
    private Double precio;
    private String imagenUrl;
    private int stock;
    private Long id_categoria;

}
