package com.luxkao.bazar.controllers;

import com.luxkao.bazar.model.entities.OrgaoDonatario;
import com.luxkao.bazar.model.repositories.RepositoryFacade;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import java.sql.SQLException;

@Component
public class OrgaoDonatarioConverter implements Converter<String, OrgaoDonatario> {

    @Override
    public OrgaoDonatario convert(String idStr) {
        if (idStr == null || idStr.isEmpty()) {
            return null;
        }
        try {
            int id = Integer.parseInt(idStr);
            return RepositoryFacade.getCurrentInstance().readOrgaoDonatario(id);
        } catch (NumberFormatException | SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}