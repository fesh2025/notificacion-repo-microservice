package com.h2.h2.api.controller;

import com.h2.h2.api.model.NotificacionModel;
import com.h2.h2.api.service.NotificacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Connection;
import java.sql.SQLException;

import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

@RestController
@RequestMapping("api/v1/notification")
public class NotificacionController {
    @Autowired
    private NotificacionService productService;

    @Autowired
    private DataSource dataSource;

    public void testConnection() throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            System.out.println("Connected to the database!");
        }
    }

    @GetMapping("prueba")
    public String prueba() {
        try {
            testConnection();
        } catch (SQLException e) {
            return "Error de conexión: " + e.getMessage();
        }
        return "Ok, se ha recibido tu solicitud";
    }
    @GetMapping("GetAll")
    public List<NotificacionModel> listar() {
        return productService.listados();
    }

    @PostMapping("/Create")
    public ResponseEntity<Map<String, Object>> create(@RequestBody NotificacionModel notificacionModel) {
        Map<String, Object> response = productService.crear(notificacionModel);
        return ResponseEntity.ok(response);
    }
    
    @PutMapping("{id}")
    public NotificacionModel update(@PathVariable Integer id, @RequestBody NotificacionModel faqModel) {
        faqModel.setId(id);
        return productService.update(faqModel);
    }
    

    @DeleteMapping("{id}")
    public void delete(@PathVariable Integer id) {
        productService.delete(id);
    }
}
