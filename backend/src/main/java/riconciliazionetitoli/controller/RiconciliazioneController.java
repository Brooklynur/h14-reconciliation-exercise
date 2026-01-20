package riconciliazionetitoli.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import riconciliazionetitoli.mapper.ReconciliationMapper;
import riconciliazionetitoli.response.ReconciliationResponse;

@RestController
@RequestMapping("/api/reconciliation")
public class RiconciliazioneController {
	
	@Autowired
	private ReconciliationMapper mapper;
	
	@GetMapping("/report")
	public List<ReconciliationResponse> getData() {
		return mapper.getReconciliationReport();
	}
	
}
