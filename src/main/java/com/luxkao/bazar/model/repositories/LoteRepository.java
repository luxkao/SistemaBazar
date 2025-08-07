package com.luxkao.bazar.model.repositories;

import com.luxkao.bazar.model.entities.Lote;
import com.luxkao.bazar.model.entities.OrgaoDonatario;
import com.luxkao.bazar.model.entities.OrgaoFiscalizador;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LoteRepository {

    protected LoteRepository(){}

    public void create(Lote lote) throws SQLException {
        Connection conn = ConnectionManager.getCurrentConnection();
        boolean originalAutoCommit = conn.getAutoCommit();

        try {
            conn.setAutoCommit(false);

            String sqlLote = "INSERT INTO lote (data_entrega, observacao, id_orgao_fiscalizador, id_orgao_donatario) VALUES (?, ?, ?, ?)";

            try (PreparedStatement pstm = conn.prepareStatement(sqlLote, Statement.RETURN_GENERATED_KEYS)) {

                pstm.setDate(1, new java.sql.Date(lote.getDataEntrega().getTime()));
                pstm.setString(2, lote.getObservacao());
                pstm.setInt(3, lote.getOrgaoFiscalizador().getId());
                pstm.setInt(4, lote.getOrgaoDonatario().getId());
                pstm.executeUpdate();

                try (ResultSet generatedKeys = pstm.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int idLote = generatedKeys.getInt(1);
                        String sqlUpdateProduto = "UPDATE produto SET id_lote = ? WHERE codigo = ?";

                        try (PreparedStatement pstmUpdate = conn.prepareStatement(sqlUpdateProduto)) {
                            lote.getProdutos().forEach(produto -> {
                                try {
                                    pstmUpdate.setInt(1, idLote);
                                    pstmUpdate.setInt(2, produto.getCodigo());
                                    pstmUpdate.addBatch();
                                } catch (SQLException e) {
                                    throw new RuntimeException(e);
                                }
                            });
                            pstmUpdate.executeBatch();
                        }
                    }
                }
            }

            conn.commit();

        } catch (SQLException | RuntimeException e) {
            conn.rollback();
            throw new SQLException("Erro ao criar o lote e associar produtos.", e);
        } finally {
            conn.setAutoCommit(originalAutoCommit);
        }
    }

    public List<Lote> readAll(Integer fiscalizadorId, Integer donatarioId) throws SQLException {
        String sql = "SELECT l.id as lote_id, l.data_entrega, l.observacao, " +
                "ofisc.id as of_id, ofisc.nome as of_nome, " +
                "odon.id as od_id, odon.nome as od_nome " +
                "FROM lote l " +
                "JOIN orgao_fiscalizador ofisc ON l.id_orgao_fiscalizador = ofisc.id " +
                "JOIN orgao_donatario odon ON l.id_orgao_donatario = odon.id";

        List<String> conditions = new ArrayList<>();
        if (fiscalizadorId != null && fiscalizadorId > 0) {
            conditions.add("l.id_orgao_fiscalizador = ?");
        }
        if (donatarioId != null && donatarioId > 0) {
            conditions.add("l.id_orgao_donatario = ?");
        }

        if (!conditions.isEmpty()) {
            sql += " WHERE " + String.join(" AND ", conditions);
        }

        List<Lote> lotes = new ArrayList<>();

        try (PreparedStatement pstm = ConnectionManager.getCurrentConnection().prepareStatement(sql)) {
            int paramIndex = 1;
            if (fiscalizadorId != null && fiscalizadorId > 0) {
                pstm.setInt(paramIndex++, fiscalizadorId);
            }
            if (donatarioId != null && donatarioId > 0) {
                pstm.setInt(paramIndex, donatarioId);
            }

            try (ResultSet rs = pstm.executeQuery()) {
                while (rs.next()) {
                    Lote lote = new Lote();
                    lote.setId(rs.getInt("lote_id"));
                    lote.setDataEntrega(rs.getDate("data_entrega"));
                    lote.setObservacao(rs.getString("observacao"));

                    OrgaoFiscalizador of = new OrgaoFiscalizador();
                    of.setId(rs.getInt("of_id"));
                    of.setNome(rs.getString("of_nome"));
                    lote.setOrgaoFiscalizador(of);

                    OrgaoDonatario od = new OrgaoDonatario();
                    od.setId(rs.getInt("od_id"));
                    od.setNome(rs.getString("od_nome"));
                    lote.setOrgaoDonatario(od);

                    lotes.add(lote);
                }
            }
        }
        return lotes;
    }

    public Lote read(int id) throws SQLException {
        String sql = "SELECT l.id as lote_id, l.data_entrega, l.observacao, " +
                "ofisc.id as of_id, ofisc.nome as of_nome, " +
                "odon.id as od_id, odon.nome as od_nome " +
                "FROM lote l " +
                "JOIN orgao_fiscalizador ofisc ON l.id_orgao_fiscalizador = ofisc.id " +
                "JOIN orgao_donatario odon ON l.id_orgao_donatario = odon.id " +
                "WHERE l.id = ?";

        try (PreparedStatement pstm = ConnectionManager.getCurrentConnection().prepareStatement(sql)) {
            pstm.setInt(1, id);
            try (ResultSet rs = pstm.executeQuery()) {
                if (rs.next()) {
                    Lote lote = new Lote();
                    lote.setId(rs.getInt("lote_id"));
                    lote.setDataEntrega(rs.getDate("data_entrega"));
                    lote.setObservacao(rs.getString("observacao"));

                    OrgaoFiscalizador of = new OrgaoFiscalizador();
                    of.setId(rs.getInt("of_id"));
                    of.setNome(rs.getString("of_nome"));
                    lote.setOrgaoFiscalizador(of);

                    OrgaoDonatario od = new OrgaoDonatario();
                    od.setId(rs.getInt("od_id"));
                    od.setNome(rs.getString("od_nome"));
                    lote.setOrgaoDonatario(od);

                    String sqlProdutos = "SELECT * FROM produto WHERE id_lote = ?";
                    try (PreparedStatement pstmProdutos = ConnectionManager.getCurrentConnection().prepareStatement(sqlProdutos)) {
                        pstmProdutos.setInt(1, id);
                        try (ResultSet rsProdutos = pstmProdutos.executeQuery()) {
                            ProdutoRepository pRepo = new ProdutoRepository();
                            while(rsProdutos.next()){
                                lote.getProdutos().add(pRepo.buildProduto(rsProdutos));
                            }
                        }
                    }

                    return lote;
                }
            }
        }
        return null;
    }
}