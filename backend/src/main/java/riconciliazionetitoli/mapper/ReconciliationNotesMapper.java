package riconciliazionetitoli.mapper;

import org.apache.ibatis.annotations.Mapper;

import riconciliazionetitoli.model.ReconciliationNotesModel;

@Mapper
public interface ReconciliationNotesMapper {
	
	ReconciliationNotesModel selectByIsinAndPortfolioId(String isin, String portfolioId);
	
	void update(ReconciliationNotesModel model);
	
}
