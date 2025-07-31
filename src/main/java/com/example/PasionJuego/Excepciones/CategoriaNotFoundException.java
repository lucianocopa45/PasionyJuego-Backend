package com.example.PasionJuego.Excepciones;

public class CategoriaNotFoundException extends RuntimeException {
    public CategoriaNotFoundException(String message){
        super(message);
    }
}
