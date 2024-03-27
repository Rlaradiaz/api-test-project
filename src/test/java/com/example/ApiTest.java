package com.example;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Random;

public class ApiTest {

    @Test
    public void Apitesting() {
        
        int userId = obtenerUsuarioAleatorioId();

        System.out.println("La dirección de correo electrónico del usuario aleatorio es: " + obtenerCorreoElectronicoUsuario(userId));

        mostrarPublicacionesUsuario(userId);
      
        realizarPublicacion(userId);
    }

    // Método para obtener el ID de usuario aleatorio
    private int obtenerUsuarioAleatorioId() {
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";

        Response response = RestAssured.given()
                .get("/users");

        response.then().statusCode(200);

        
        List<Integer> userIdList = response.jsonPath().getList("id");
        int randomIndex = new Random().nextInt(userIdList.size());
        return userIdList.get(randomIndex);
    }

    // Método para obtener el correo electrónico del usuario
    private String obtenerCorreoElectronicoUsuario(int userId) {
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";
        
        Response response = RestAssured.given()
                .pathParam("userId", userId)
                .get("/users/{userId}");
        
        response.then().statusCode(200);
        return response.jsonPath().getString("email");
    }

    // Método para mostrar las publicaciones del usuario
    private void mostrarPublicacionesUsuario(int userId) {
        
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";
        
        Response response = RestAssured.given()
                .queryParam("userId", userId)
                .get("/posts");
        
        response.then().statusCode(200);
        
        List<String> titles = response.jsonPath().getList("title");
        
        System.out.println("Publicaciones del usuario:");
        for (String title : titles) {
            System.out.println("- " + title);
        }
    }

    //// Método para Realizar nueva Publicacion y
    private void realizarPublicacion(int userId) {
        
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";

    
        String title = "Título de la publicación";
        String body = "Cuerpo de la publicación";
        
        Response response = RestAssured.given()
                .contentType("application/json")
                .body("{\"userId\": " + userId + ", \"title\": \"" + title + "\", \"body\": \"" + body + "\"}")
                .post("/posts");

        System.out.println("Respuesta de la API al realizar la publicación:");
        System.out.println(response.asString());

        System.out.println("Código de respuesta de la API: " + response.getStatusCode());

        response.then().statusCode(201);


    }
    }