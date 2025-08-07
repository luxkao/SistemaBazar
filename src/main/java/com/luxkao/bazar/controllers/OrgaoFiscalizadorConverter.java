package com.luxkao.bazar.controllers;

import com.luxkao.bazar.model.entities.OrgaoFiscalizador;
import com.luxkao.bazar.model.repositories.RepositoryFacade;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import java.sql.SQLException;

@Component
public class OrgaoFiscalizadorConverter implements Converter<String, OrgaoFiscalizador> {

    @Override
    public OrgaoFiscalizador convert(String idStr) {
        if (idStr == null || idStr.isEmpty()) {
            return null;
        }
        try {
            int id = Integer.parseInt(idStr);
            return RepositoryFacade.getCurrentInstance().readOrgaoFiscalizador(id);
        } catch (NumberFormatException | SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}