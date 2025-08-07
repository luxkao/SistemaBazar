package com.luxkao.bazar.model.repositories;

import com.luxkao.bazar.model.entities.OrgaoFiscalizador;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrgaoFiscalizadorRepository implements Repository<Integer, OrgaoFiscalizador> {

    protected OrgaoFiscalizadorRepository() {}

    @Override
    public void create(OrgaoFiscalizador entity) throws SQLException {
        String sql = "INSERT INTO orgao_fiscalizador (nome, descricao) VALUES (?, ?)";

        try (PreparedStatement pstm = ConnectionManager.getCurrentConnection().prepareStatement(sql)) {
            pstm.setString(1, entity.getNome());
            pstm.setString(2, entity.getDescricao());
            pstm.execute();
        }
    }

    @Override
    public void update(OrgaoFiscalizador entity) throws SQLException {
        String sql = "UPDATE orgao_fiscalizador SET nome = ?, descricao = ? WHERE id = ?";

        try (PreparedStatement pstm = ConnectionManager.getCurrentConnection().prepareStatement(sql)) {
            pstm.setString(1, entity.getNome());
            pstm.setString(2, entity.getDescricao());
            pstm.setInt(3, entity.getId());
            pstm.execute();
        }
    }

    @Override
    public OrgaoFiscalizador read(Integer key) throws SQLException {
        String sql = "SELECT * FROM orgao_fiscalizador WHERE id = ?";

        try (PreparedStatement pstm = ConnectionManager.getCurrentConnection().prepareStatement(sql)) {
            pstm.setInt(1, key);

            try (ResultSet rs = pstm.executeQuery()) {
                if (rs.next()) {
                    OrgaoFiscalizador of = new OrgaoFiscalizador();
                    of.setId(rs.getInt("id"));
                    of.setNome(rs.getString("nome"));
                    of.setDescricao(rs.getString("descricao"));
                    return of;
                }
            }
        }
        return null;
    }

    @Override
    public void delete(Integer key) throws SQLException {
        String sql = "DELETE FROM orgao_fiscalizador WHERE id = ?";

        try (PreparedStatement pstm = ConnectionManager.getCurrentConnection().prepareStatement(sql)) {
            pstm.setInt(1, key);
            pstm.execute();
        }
    }

    @Override
    public List<OrgaoFiscalizador> readAll() throws SQLException {
        String sql = "SELECT * FROM orgao_fiscalizador";
        List<OrgaoFiscalizador> orgaos = new ArrayList<>();

        try (PreparedStatement pstm = ConnectionManager.getCurrentConnection().prepareStatement(sql);
             ResultSet rs = pstm.executeQuery()) {

            while (rs.next()) {
                OrgaoFiscalizador of = new OrgaoFiscalizador();
                of.setId(rs.getInt("id"));
                of.setNome(rs.getString("nome"));
                of.setDescricao(rs.getString("descricao"));
                orgaos.add(of);
            }
        }
        return orgaos;
    }
}