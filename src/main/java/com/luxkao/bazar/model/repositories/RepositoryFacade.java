package com.luxkao.bazar.model.repositories;

import com.luxkao.bazar.model.entities.Lote;
import com.luxkao.bazar.model.entities.OrgaoDonatario;
import com.luxkao.bazar.model.entities.OrgaoFiscalizador;
import com.luxkao.bazar.model.entities.Produto;
import org.springframework.stereotype.Component;
import java.sql.SQLException;
import java.util.List;

@Component
public class RepositoryFacade {

    private static RepositoryFacade myself = null;

    private final OrgaoFiscalizadorRepository ofRepo;
    private final OrgaoDonatarioRepository odRepo;
    private final ProdutoRepository pRepo;
    private final LoteRepository loteRepo;

    private RepositoryFacade() {
        this.ofRepo = new OrgaoFiscalizadorRepository();
        this.odRepo = new OrgaoDonatarioRepository();
        this.pRepo = new ProdutoRepository();
        this.loteRepo = new LoteRepository();
    }

    public static RepositoryFacade getCurrentInstance() {
        if (myself == null) {
            myself = new RepositoryFacade();
        }
        return myself;
    }

    // Métodos para OrgaoFiscalizador
    public void createOrgaoFiscalizador(OrgaoFiscalizador of) throws SQLException {
        this.ofRepo.create(of);
    }
    public void updateOrgaoFiscalizador(OrgaoFiscalizador of) throws SQLException {
        this.ofRepo.update(of);
    }
    public OrgaoFiscalizador readOrgaoFiscalizador(int id) throws SQLException {
        return this.ofRepo.read(id);
    }
    public void deleteOrgaoFiscalizador(int id) throws SQLException {
        this.ofRepo.delete(id);
    }
    public List<OrgaoFiscalizador> readAllOrgaosFiscalizadores() throws SQLException {
        return this.ofRepo.readAll();
    }

    // Métodos para OrgaoDonatario
    public void createOrgaoDonatario(OrgaoDonatario od) throws SQLException {
        this.odRepo.create(od);
    }
    public void updateOrgaoDonatario(OrgaoDonatario od) throws SQLException {
        this.odRepo.update(od);
    }
    public OrgaoDonatario readOrgaoDonatario(int id) throws SQLException {
        return this.odRepo.read(id);
    }
    public void deleteOrgaoDonatario(int id) throws SQLException {
        this.odRepo.delete(id);
    }
    public List<OrgaoDonatario> readAllOrgaosDonatarios() throws SQLException {
        return this.odRepo.readAll();
    }

    // Métodos para Produto
    public void createProduto(Produto p) throws SQLException {
        this.pRepo.create(p);
    }
    public void updateProduto(Produto p) throws SQLException {
        this.pRepo.update(p);
    }
    public Produto readProduto(int codigo) throws SQLException {
        return this.pRepo.read(codigo);
    }
    public void deleteProduto(int codigo) throws SQLException {
        this.pRepo.delete(codigo);
    }
    public List<Produto> readAllProdutos() throws SQLException {
        return this.pRepo.readAll();
    }
    public List<Produto> readAllAvailableProdutos() throws SQLException {
        return this.pRepo.readAllAvailable();
    }

    // Métodos para Lote
    public void createLote(Lote lote) throws SQLException {
        this.loteRepo.create(lote);
    }
    public List<Lote> readAllLotes() throws SQLException {
        return this.loteRepo.readAll();
    }
}