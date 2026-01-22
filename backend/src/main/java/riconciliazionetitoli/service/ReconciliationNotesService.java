package riconciliazionetitoli.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import riconciliazionetitoli.mapper.ReconciliationNotesMapper;
import riconciliazionetitoli.model.ReconciliationNotesModel;

@Service
public class ReconciliationNotesService {
	
	@Autowired
	private ReconciliationNotesMapper reconciliationNotesMapper;
	
	public ReconciliationNotesModel getReconciliationNotesByIsinAndPortfolioId(String isin, String portfolioId) {
		ReconciliationNotesModel selectByIsinAndPortfolioId = reconciliationNotesMapper.selectByIsinAndPortfolioId(isin, portfolioId);
		return selectByIsinAndPortfolioId != null ? new ReconciliationNotesModel(selectByIsinAndPortfolioId) : null;
	}
	
	@Transactional
	public void update(ReconciliationNotesModel model) {
		reconciliationNotesMapper.update(model);
	}
	
}
