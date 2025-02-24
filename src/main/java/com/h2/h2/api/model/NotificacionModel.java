package com.h2.h2.api.model;

import lombok.Data;
import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "notificaciones")
@Data
public class NotificacionModel {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String tipo;
    private String clienteId;
    private String titulo;
    private String mensaje;
    private String estado;
    
    private LocalDateTime fechaEnvio;
    private String mensajeError;
}
