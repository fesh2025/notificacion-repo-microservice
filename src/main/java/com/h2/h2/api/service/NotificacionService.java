package com.h2.h2.api.service;

import com.h2.h2.api.model.NotificacionModel;

import java.util.List;
import java.util.Map;

public interface NotificacionService {
    List<NotificacionModel> listados();

    Map<String, Object> crear(NotificacionModel notificacion);

    NotificacionModel update(NotificacionModel notificacion);

    void delete (Integer id);

}
