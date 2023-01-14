package tn.ipsas.reglementservice.event;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import tn.ipsas.coremodels.events.FactureAddedEvent;
import tn.ipsas.coremodels.models.facture.Facture;
import tn.ipsas.reglementservice.service.ReglementService;

@Component
public class FactureAddedEventListener implements ApplicationListener<FactureAddedEvent> {
    private final ReglementService reglementService;

    public FactureAddedEventListener(ReglementService reglementService) {
        this.reglementService = reglementService;
    }

    @Override
    public void onApplicationEvent(FactureAddedEvent event) {
        reglementService.addFActure((Facture) event.getSource());
    }
}
