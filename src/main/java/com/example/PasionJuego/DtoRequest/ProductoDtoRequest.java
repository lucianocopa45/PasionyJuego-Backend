package com.example.PasionJuego.DtoRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.*;


@Data
@NoArgsConstructor
public class ProductoDtoRequest {
    @Schema(description = "Descripción del producto", example = "Notebook Dell Inspiron")
    @NotBlank(message = "La descripción no puede estar vacía")
    @NotNull(message = "La descripción no puede estar vacía")
    @Size(min = 1, message = "La descripción no puede estar vacía")
    private String descripcion;

    @Schema(description = "Precio del producto", example = "59999.99")
    @Positive(message = "El precio debe ser mayor que 0")
    @NotNull(message = "El precio es obligatorio")
    @Digits(integer = 10, fraction = 2, message = "El precio debe tener hasta 2 decimales")
    private Double precio;

    @Schema(description = "URL de la imagen del producto", example = "https://example.com/img.jpg")
    private String imagenUrl;

    @Schema(description = "Stock disponible del producto", example = "20")
    @NotNull(message = "El stock es obligatorio")
    @Min(value = 0, message = "El stock no puede ser negativo")
    @PositiveOrZero(message = "El stock no puede ser negativo")
    private int stock;

    @Schema(description = "ID de la categoría a la que pertenece el producto", example = "3")
    @NotNull(message = "El ID de la categoría es obligatorio")
    @Positive(message = "El ID de la categoría debe ser un número positivo")
    private Long categoriaId;
}
