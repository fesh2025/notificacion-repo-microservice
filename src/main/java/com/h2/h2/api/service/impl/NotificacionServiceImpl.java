package com.h2.h2.api.service.impl;

import com.h2.h2.api.model.NotificacionModel;
import com.h2.h2.api.respository.NotificacionRepository;
import com.h2.h2.api.service.NotificacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityNotFoundException;

@Service
public class NotificacionServiceImpl implements NotificacionService {

private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Autowired
    private NotificacionRepository notificacionRepository;

    @Override
    public List<NotificacionModel> listados() {
        return notificacionRepository.findAll();
    }

    @Override
    public Map<String, Object> crear(NotificacionModel notificacion) {

        // Validaciones de negocio
        if (notificacion.getTipo() == null || notificacion.getClienteId() == null ||
        notificacion.getTitulo() == null || notificacion.getMensaje() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "NOT003: Datos requeridos faltantes");
        }

//        if (!esTipoValido(notificacion.getTipo())) {
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "NOT001: Tipo de notificación inválido");
//        }

        // Simulación de verificación de cliente
        if (!clienteExiste(notificacion.getClienteId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "NOT002: Cliente no existe");
        }

        // Establecer estado inicial
        notificacion.setEstado("PENDIENTE");
        notificacion.setFechaEnvio(LocalDateTime.now());
        notificacion.setMensajeError(null);

        try {
            // Guardar en base de datos
            NotificacionModel notificacionGuardada = notificacionRepository.save(notificacion);
            return mapToResponse(notificacionGuardada);
        } catch (Exception e) {
            // Si ocurre un error, cambiar estado y mensaje de error
            notificacion.setEstado("ERROR");
            notificacion.setMensajeError("NOT500: Error en el backend - " + e.getMessage());

            try {
                NotificacionModel notificacionError = notificacionRepository.save(notificacion);
                return mapToResponse(notificacionError);
            } catch (Exception ex) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "NOT500: Error crítico en el backend");
            }
        }
    }


    private Map<String, Object> mapToResponse(NotificacionModel notificacion) {
        Map<String, Object> response = new HashMap<>();
        response.put("notificacionId", notificacion.getId());
        response.put("estado", notificacion.getEstado());
        response.put("fechaEnvio", notificacion.getFechaEnvio() != null ? notificacion.getFechaEnvio().format(FORMATTER) : null);
        response.put("mensajeError", notificacion.getMensajeError());   
        return response;
    }

//    private boolean esTipoValido(String tipo) {
//        return "FACTURACION".equalsIgnoreCase(tipo);
//    }

    private boolean clienteExiste(String clienteId) {
        return true;
    }

    @Override
    public NotificacionModel update(NotificacionModel notificacion) {
        NotificacionModel notificacionExistente = notificacionRepository.findById(notificacion.getId())
                .orElseThrow(() -> new EntityNotFoundException("Notificación con id " + notificacion.getId() + " no encontrada."));
    
        if (notificacion.getEstado() != null) {
            notificacionExistente.setEstado(notificacion.getEstado());
        }
        if (notificacion.getMensajeError() != null) {
            notificacionExistente.setMensajeError(notificacion.getMensajeError());
        }
    
        return notificacionRepository.save(notificacionExistente);
    }
    

    @Override
    public void delete(Integer id) {
        if (!notificacionRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "MANT404: Evento de mantenimiento no encontrado");
        }
        notificacionRepository.deleteById(id);
    }

}
