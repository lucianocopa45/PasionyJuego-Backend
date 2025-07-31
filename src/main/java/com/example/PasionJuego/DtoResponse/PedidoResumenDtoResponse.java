package com.example.PasionJuego.DtoResponse;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PedidoResumenDtoResponse {

    private Long idPedidos;
    private Long idUsuario;
    private String nombreUsuario;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime fechaCreacion;
    private String estado;
    //private int cantidadProducto;
    private double total;

    private List<ProductoResumenDto> productos;

}
