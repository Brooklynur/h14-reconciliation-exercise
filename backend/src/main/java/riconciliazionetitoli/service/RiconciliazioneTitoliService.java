package riconciliazionetitoli.service;

import java.util.Date;
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
	public void validateReconciliation(RiconciliazioneManualeRequest request) throws RiconciliazioneTitoliException {
		ReconciliationNotesModel model = new ReconciliationNotesModel();
		model.setIsin(request.getIsin());
		model.setPortfolioId(request.getPortfolioId());
		model.setNote(request.getNote());
		model.setIsValidated(Boolean.TRUE);
		model.setValidationDate(new Date());
		model.setValidatedBy(9999999L);
		
		reconciliationNotesService.insert(model);
	}
	
}
