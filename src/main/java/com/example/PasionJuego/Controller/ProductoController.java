package com.example.PasionJuego.Controller;

import com.example.PasionJuego.DtoRequest.ProductoDtoRequest;
import com.example.PasionJuego.DtoResponse.ProductoDtoResponse;
import com.example.PasionJuego.Service.ProductoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/producto")
public class ProductoController {
    @Autowired
    private ProductoService productoService;

    @Operation(summary = "Crear producto", description = "Crea un nuevo producto en el sistema")
    @ApiResponse(responseCode = "200", description = "Producto creado correctamente")
    @PostMapping(path = "/crearProducto")
    public ResponseEntity<ProductoDtoResponse> createProducto(@RequestBody ProductoDtoRequest dto) {
        ProductoDtoResponse response = productoService.createProducto(dto);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Obtener todos los productos", description = "Lista todos los productos disponibles")
    @GetMapping(path = "/productosAll")
    public ResponseEntity<List<ProductoDtoResponse>> getAllProductos() {
        List<ProductoDtoResponse> response = productoService.getAllProductos();
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Obtener producto por ID", description = "Devuelve los detalles de un producto específico")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Producto encontrado"),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    })
    @GetMapping(path = "/{idProducto}")
    public ResponseEntity<?> getByIdProducto(@PathVariable Long idProducto) {
        return ResponseEntity.ok(productoService.getByIdProducto(idProducto));
    }

        @Operation(summary = "Actualizar producto", description = "Modifica los datos de un producto existente")
    @PutMapping (path = "/{idProducto}")
    public ResponseEntity<ProductoDtoResponse> updateProducto(@PathVariable Long idProducto, @RequestBody ProductoDtoRequest dto){
        System.out.println("ID de categoría recibido: " + dto.getCategoriaId());
        System.out.println("DTO recibido: " + dto);
        ProductoDtoResponse response = productoService.updateProducto(idProducto, dto);
        return ResponseEntity.ok(response);

    }
    @PutMapping("/prueba/{idProducto}")
    public ResponseEntity<?> testJsonRaw(
            @PathVariable Long idProducto,
            HttpServletRequest request
    ) throws IOException {
        String body = new BufferedReader(new InputStreamReader(request.getInputStream()))
                .lines().collect(Collectors.joining("\n"));
        System.out.println("Body recibido: " + body);
        return ResponseEntity.ok("Revisado");
    }
}
