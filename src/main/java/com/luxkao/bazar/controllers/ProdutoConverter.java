package com.luxkao.bazar.controllers;

import com.luxkao.bazar.model.entities.Produto;
import com.luxkao.bazar.model.repositories.RepositoryFacade;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import java.sql.SQLException;

@Component
public class ProdutoConverter implements Converter<String, Produto> {

    @Override
    public Produto convert(String source) {
        if (source == null || source.isEmpty()) {
            return null;
        }
        try {
            int codigo = Integer.parseInt(source);
            return RepositoryFacade.getCurrentInstance().readProduto(codigo);
        } catch (NumberFormatException | SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}