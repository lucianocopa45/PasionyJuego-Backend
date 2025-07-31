package com.example.PasionJuego.DtoRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PedidoDtoRequest {

    @Schema(description = "ID del usuario que realiza el pedido", example = "10", required = true)
    @NotNull(message = "EL id del usuario debe ser valido")
    @Positive(message = "El ID del usuario debe ser un número positivo")
    private Long idUsuario;

    @Schema(description = "Lista de productos del pedido", required = true)
    @Size(min = 1, message = "Debe haber al menos un producto en la lista")
    @NotNull (message = "Deben haber productos cargados")
    @Valid
    private List<LineaPedidoDtoRequest> lineas;

    @Schema(description = "Estado del pedido: pendiente, pagado o cancelado", example = "pendiente", required = true)
    @NotBlank(message = "El estado del pedido no puede estar vacío")
    @Pattern(regexp = "^(pendiente|pagado|cancelado)$", message = "El estado debe ser pendiente, pagado o cancelado")
    @NotNull (message = "EL estado del usuario debe ser valido")
    private String estado;

    @Schema(description = "Método de pago: Efectivo, Tarjeta o Mp", example = "Mp", required = true)
    @NotBlank(message = "El método de pago no puede estar vacío")
    @Pattern(regexp = "^(Efectivo|Tarjeta|Mp)$", message = "El método de pago debe ser Efectivo, Tarjeta o Mp")
    @NotNull (message = "EL metodo de pago del usuario debe ser valido")
    private String metodoPago;

}
