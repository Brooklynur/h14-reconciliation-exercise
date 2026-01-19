package riconciliazionetitoli.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object per rappresentare una riga della dashboard.
 * Unisce i dati di Swarm, delle banche e dello stato di riconciliazione.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReconciliationResponse {

    // Anagrafica Titolo
    private String isin;
    private String securityName;
    private String portfolioId;

    // Dati Interni (Swarm)
    private Double internalQty;
    private Double internalPrice;

    // Dati Bancari (Aggregati)
    private Double bankQty;

    // Calcoli (Derivati dalla query o calcolati nel Service)
    private Double diffQty;

    // Stato Riconciliazione (Step C)
    private boolean manuallyValidated;
    private String notes;

    /**
     * Calcola lo stato per il frontend.
     * Logica: Se la differenza è 0 -> MATCH
     * Se è validato manualmente -> MANUAL_OK
     * Altrimenti -> DISCREPANCY
     */
    public String getStatus() {
        if (manuallyValidated) return "MANUAL_OK";
        if (diffQty == null || Math.abs(diffQty) < 0.0001) return "MATCH";
        return "DISCREPANCY";
    }
}