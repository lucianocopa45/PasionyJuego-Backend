package com.example.PasionJuego.DtoRequest;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LineaPedidoDtoRequest {

    @NotNull(message = "Debe cargar productos a la lista")
    @Positive(message = "El ID del producto debe ser un número positivo")
    @JsonProperty("idProducto")
    private Long productoId;

    @NotNull (message = "Debe agregar cantidad de productos")
    @Positive(message = "La cantidad debe ser mayor a cero")
    @Min(value = 1, message = "La cantidad mínima debe ser 1")
    private int cantidad;

}
