package com.luxkao.bazar.model.repositories;

import com.luxkao.bazar.model.entities.OrgaoDonatario;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrgaoDonatarioRepository implements Repository<Integer, OrgaoDonatario> {

    protected OrgaoDonatarioRepository(){}

    @Override
    public void create(OrgaoDonatario entity) throws SQLException {
        String sql = "INSERT INTO orgao_donatario (nome, endereco, telefone, horarioFuncionamento, descricao) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pstm = ConnectionManager.getCurrentConnection().prepareStatement(sql)) {
            pstm.setString(1, entity.getNome());
            pstm.setString(2, entity.getEndereco());
            pstm.setString(3, entity.getTelefone());
            pstm.setString(4, entity.getHorarioFuncionamento());
            pstm.setString(5, entity.getDescricao());
            pstm.execute();
        }
    }

    @Override
    public void update(OrgaoDonatario entity) throws SQLException {
        String sql = "UPDATE orgao_donatario SET nome=?, endereco=?, telefone=?, horarioFuncionamento=?, descricao=? WHERE id=?";
        try (PreparedStatement pstm = ConnectionManager.getCurrentConnection().prepareStatement(sql)) {
            pstm.setString(1, entity.getNome());
            pstm.setString(2, entity.getEndereco());
            pstm.setString(3, entity.getTelefone());
            pstm.setString(4, entity.getHorarioFuncionamento());
            pstm.setString(5, entity.getDescricao());
            pstm.setInt(6, entity.getId());
            pstm.execute();
        }
    }

    @Override
    public OrgaoDonatario read(Integer key) throws SQLException {
        String sql = "SELECT * FROM orgao_donatario WHERE id = ?";
        try (PreparedStatement pstm = ConnectionManager.getCurrentConnection().prepareStatement(sql)) {
            pstm.setInt(1, key);
            try (ResultSet rs = pstm.executeQuery()) {
                if (rs.next()) {
                    return buildOrgaoDonatario(rs);
                }
            }
        }
        return null;
    }

    @Override
    public void delete(Integer key) throws SQLException {
        String sql = "DELETE FROM orgao_donatario WHERE id = ?";
        try (PreparedStatement pstm = ConnectionManager.getCurrentConnection().prepareStatement(sql)) {
            pstm.setInt(1, key);
            pstm.execute();
        }
    }

    @Override
    public List<OrgaoDonatario> readAll() throws SQLException {
        String sql = "SELECT * FROM orgao_donatario";
        List<OrgaoDonatario> orgaos = new ArrayList<>();
        try (PreparedStatement pstm = ConnectionManager.getCurrentConnection().prepareStatement(sql);
             ResultSet rs = pstm.executeQuery()) {
            while (rs.next()) {
                orgaos.add(buildOrgaoDonatario(rs));
            }
        }
        return orgaos;
    }

    private OrgaoDonatario buildOrgaoDonatario(ResultSet rs) throws SQLException {
        OrgaoDonatario od = new OrgaoDonatario();
        od.setId(rs.getInt("id"));
        od.setNome(rs.getString("nome"));
        od.setEndereco(rs.getString("endereco"));
        od.setTelefone(rs.getString("telefone"));
        od.setHorarioFuncionamento(rs.getString("horarioFuncionamento"));
        od.setDescricao(rs.getString("descricao"));
        return od;
    }
}