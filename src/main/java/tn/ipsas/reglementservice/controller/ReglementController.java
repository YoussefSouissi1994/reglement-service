package tn.ipsas.reglementservice.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import tn.ipsas.coremodels.models.facture.Facture;
import tn.ipsas.coremodels.models.reglement.Reglement;
import tn.ipsas.coremodels.models.reglement.ReglementItem;
import tn.ipsas.reglementservice.service.ReglementService;

import java.util.List;

@RestController
@RequestMapping
public class ReglementController {
    private final ReglementService service;

    public ReglementController(ReglementService service) {
        this.service = service;
    }
    @GetMapping
    public Page<Reglement> page(Pageable pageable) {
        return service.getAll(pageable);
    }
    @GetMapping("{id}")
    public Reglement byId(@PathVariable("id") String id) {
        return service.getById(id);
    }
    @GetMapping("solde/{clientId}")
    public double solde(@PathVariable("clientId") String clientId) {
        return service.solde(clientId);
    }
    @GetMapping("byFacture/{factureId}")
    public List<ReglementItem> byFacture(@PathVariable("factureId") String factureId) {
        return service.byFacture(factureId);
    }
    @PutMapping
    public Reglement add(@RequestBody Reglement reglement) {
        reglement.setId(null);
        return service.save(reglement);
    }
    @PutMapping("{id}")
    public Reglement update(@PathVariable("id") String id, @RequestBody Reglement reglement) {
        if (!service.exists(id)) {
            throw new IllegalArgumentException();
        }
        reglement.setId(id);
        return service.save(reglement);
    }
    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") String id) {
        if (!service.exists(id)) {
            throw new IllegalArgumentException();
        }
        service.delete(id);
    }

}
