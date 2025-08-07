package com.luxkao.bazar.model.repositories;

import com.luxkao.bazar.model.entities.OrgaoFiscalizador;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;

@Repository
public class RepositoryFacade {

    private static RepositoryFacade myself = null;

    private final OrgaoFiscalizadorRepository ofRepo;

    private RepositoryFacade() {
        this.ofRepo = new OrgaoFiscalizadorRepository();
    }

    public static RepositoryFacade getCurrentInstance() {
        if (myself == null) {
            myself = new RepositoryFacade();
        }
        return myself;
    }

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
}