package riconciliazionetitoli.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import riconciliazionetitoli.response.ReconciliationResponse;

@Mapper
public interface RiconciliationMapper {

	List<ReconciliationResponse> getReconciliationReport();
	
}
