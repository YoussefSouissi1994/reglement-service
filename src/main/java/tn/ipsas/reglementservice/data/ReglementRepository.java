package tn.ipsas.reglementservice.data;

import org.springframework.data.mongodb.repository.MongoRepository;
import tn.ipsas.coremodels.models.reglement.Reglement;

import java.util.List;

public interface ReglementRepository extends MongoRepository<Reglement, String> {
    List<Reglement> findAllByItemsFactureId(String factureId);
    List<Reglement> findAllByClientId(String clientId);
}
