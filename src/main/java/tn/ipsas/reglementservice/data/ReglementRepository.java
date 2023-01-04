package tn.ipsas.reglementservice.data;

import org.springframework.data.mongodb.repository.MongoRepository;
import tn.ipsas.coremodels.models.reglement.Reglement;

public interface ReglementRepository extends MongoRepository<Reglement, String> {
}
