package com.luxkao.bazar.model.repositories;

import com.luxkao.bazar.model.entities.Produto;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProdutoRepository implements Repository<Integer, Produto> {

    protected ProdutoRepository() {}

    @Override
    public void create(Produto entity) throws SQLException {
        String sql = "INSERT INTO produto (nome, descricao) VALUES (?, ?)";
        try (PreparedStatement pstm = ConnectionManager.getCurrentConnection().prepareStatement(sql)) {
            pstm.setString(1, entity.getNome());
            pstm.setString(2, entity.getDescricao());
            pstm.execute();
        }
    }

    @Override
    public void update(Produto entity) throws SQLException {
        String sql = "UPDATE produto SET nome=?, descricao=? WHERE codigo=?";
        try (PreparedStatement pstm = ConnectionManager.getCurrentConnection().prepareStatement(sql)) {
            pstm.setString(1, entity.getNome());
            pstm.setString(2, entity.getDescricao());
            pstm.setInt(3, entity.getCodigo());
            pstm.execute();
        }
    }

    @Override
    public Produto read(Integer key) throws SQLException {
        String sql = "SELECT * FROM produto WHERE codigo = ?";
        try (PreparedStatement pstm = ConnectionManager.getCurrentConnection().prepareStatement(sql)) {
            pstm.setInt(1, key);
            try (ResultSet rs = pstm.executeQuery()) {
                if (rs.next()) {
                    return buildProduto(rs);
                }
            }
        }
        return null;
    }

    @Override
    public void delete(Integer key) throws SQLException {
        String sql = "DELETE FROM produto WHERE codigo = ?";
        try (PreparedStatement pstm = ConnectionManager.getCurrentConnection().prepareStatement(sql)) {
            pstm.setInt(1, key);
            pstm.execute();
        }
    }

    @Override
    public List<Produto> readAll() throws SQLException {
        String sql = "SELECT * FROM produto";
        List<Produto> produtos = new ArrayList<>();
        try (PreparedStatement pstm = ConnectionManager.getCurrentConnection().prepareStatement(sql);
             ResultSet rs = pstm.executeQuery()) {
            while (rs.next()) {
                produtos.add(buildProduto(rs));
            }
        }
        return produtos;
    }

    public Produto buildProduto(ResultSet rs) throws SQLException {
        Produto p = new Produto();
        p.setCodigo(rs.getInt("codigo"));
        p.setNome(rs.getString("nome"));
        p.setDescricao(rs.getString("descricao"));
        return p;
    }

    public List<Produto> readAllAvailable() throws SQLException {
        String sql = "SELECT * FROM produto WHERE id_lote IS NULL";
        List<Produto> produtos = new ArrayList<>();
        try (PreparedStatement pstm = ConnectionManager.getCurrentConnection().prepareStatement(sql);
             ResultSet rs = pstm.executeQuery()) {
            while (rs.next()) {
                produtos.add(buildProduto(rs));
            }
        }
        return produtos;
    }
}