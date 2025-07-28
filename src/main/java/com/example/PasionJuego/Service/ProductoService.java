package com.example.PasionJuego.Service;

import com.example.PasionJuego.DtoRequest.ProductoDtoRequest;
import com.example.PasionJuego.DtoResponse.ProductoDtoResponse;
import com.example.PasionJuego.Entity.Categoria;
import com.example.PasionJuego.Entity.Producto;
import com.example.PasionJuego.Excepciones.CategoriaNotFoundException;
import com.example.PasionJuego.Repository.CategoriaRepository;
import com.example.PasionJuego.Repository.ProductoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductoService {

    private final ProductoRepository productoRepository;
    private final CategoriaRepository categoriaRepository;

    public List<ProductoDtoResponse> getAllProductos() {
        ProductoDtoResponse response = new ProductoDtoResponse();
        return productoRepository.findAll().stream().map(this::mapToDto).collect(Collectors.toList());
    }

    private ProductoDtoResponse mapToDto(Producto producto) {
        return new ProductoDtoResponse(
                producto.getId_producto(),
                producto.getDescripcion(),
                producto.getPrecio(),
                producto.getImagenUrl(),
                producto.getStock(),
                producto.getCategoria().getId_categoria()
        );
    }

    public ProductoDtoResponse createProducto(ProductoDtoRequest dto) {
        Categoria categoria = categoriaRepository.findById(dto.getCategoriaId())
                .orElseThrow(() -> new CategoriaNotFoundException(
                        "La categoría con el ID: " + dto.getCategoriaId() + " NO existe"
                ));

        Producto producto = Producto.builder()
                .descripcion(dto.getDescripcion())
                .precio(dto.getPrecio())
                .imagenUrl(dto.getImagenUrl())
                .stock(dto.getStock())
                .categoria(categoria)
                .build();

        productoRepository.save(producto);

        ProductoDtoResponse response = new ProductoDtoResponse();
        response.setId_producto(producto.getId_producto());
        response.setDescripcion(producto.getDescripcion());
        response.setPrecio(producto.getPrecio());
        response.setImagenUrl(producto.getImagenUrl());
        response.setStock(producto.getStock());
        response.setId_categoria(producto.getCategoria().getId_categoria());

        return response;
    }

    public ProductoDtoResponse getByIdProducto(Long idProducto) {
        try {
            Producto record = productoRepository.findById(idProducto).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "El producto con el ID " + idProducto + " no se ha encontrado"));

            ProductoDtoResponse response = new ProductoDtoResponse();
            response.setId_producto(record.getId_producto());
            response.setDescripcion(record.getDescripcion());
            response.setPrecio(record.getPrecio());
            response.setImagenUrl(record.getImagenUrl());
            response.setStock(record.getStock());
            response.setId_categoria(record.getCategoria().getId_categoria());

            return response;

        } catch (EntityNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    public ProductoDtoResponse updateProducto(Long idProducto, ProductoDtoRequest dto) {
        try {
            Producto record = productoRepository.findById(idProducto).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "El producto con el ID " + idProducto + " no se ha encontrado"));
            Categoria categoria = categoriaRepository.findById(dto.getCategoriaId())
                    .orElseThrow(() -> new CategoriaNotFoundException(
                            "La categoría con el ID: " + dto.getCategoriaId() + " NO existe"
                    ));
            //Producto response = new Producto();
            record.setDescripcion(dto.getDescripcion());
            record.setPrecio(dto.getPrecio());
            record.setImagenUrl(dto.getImagenUrl());
            record.setStock(dto.getStock());
            record.setCategoria(categoria);

            productoRepository.save(record);

            ProductoDtoResponse response = new ProductoDtoResponse();
            response.setId_producto(record.getId_producto());
            response.setDescripcion(record.getDescripcion());
            response.setPrecio(record.getPrecio());
            response.setImagenUrl(record.getImagenUrl());
            response.setStock(record.getStock());
            response.setId_categoria(categoria.getId_categoria());

            return response;
        } catch (Exception e){
            System.err.println("Error al actualizar producto: " + e.getMessage());
            e.printStackTrace();

            // Opcional: puedes lanzar una excepción personalizada o devolver null o un DTO con error
            throw new RuntimeException("Error interno al actualizar producto, detalle: " + e.getMessage());
        }
    }
}
