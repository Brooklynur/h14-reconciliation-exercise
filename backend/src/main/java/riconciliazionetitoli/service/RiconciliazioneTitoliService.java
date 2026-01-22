package riconciliazionetitoli.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import riconciliazionetitoli.exception.RiconciliazioneTitoliException;
import riconciliazionetitoli.mapper.ReconciliationMapper;
import riconciliazionetitoli.model.ReconciliationNotesModel;
import riconciliazionetitoli.request.RiconciliazioneManualeRequest;
import riconciliazionetitoli.response.ReconciliationResponse;

@Service
public class RiconciliazioneTitoliService {
	
	@Autowired
	private ReconciliationMapper reconciliationMapper;
	@Autowired
	private ReconciliationNotesService reconciliationNotesService;
	
	public List<ReconciliationResponse> getReconciliationReport() {
		return reconciliationMapper.getReconciliationReport();
	}
	
	@Transactional
	public void updateReconciliation(RiconciliazioneManualeRequest request) throws RiconciliazioneTitoliException {
		ReconciliationNotesModel reconciliationNotesByIsinAndPortfolioId = reconciliationNotesService.getReconciliationNotesByIsinAndPortfolioId(request.getIsin(), request.getPortfolioId());
		
		if(reconciliationNotesByIsinAndPortfolioId == null) {
			throw new RiconciliazioneTitoliException("Reconciliation note not found for ISIN: " + request.getIsin() + " and Portfolio ID: " + request.getPortfolioId());
		}
		
		reconciliationNotesByIsinAndPortfolioId.setNote(request.getNote());
		reconciliationNotesService.update(reconciliationNotesByIsinAndPortfolioId);
		
	}
	
}
