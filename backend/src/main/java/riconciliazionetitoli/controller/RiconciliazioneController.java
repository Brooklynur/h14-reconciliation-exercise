package riconciliazionetitoli.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import riconciliazionetitoli.exception.RiconciliazioneTitoliException;
import riconciliazionetitoli.request.RiconciliazioneManualeRequest;
import riconciliazionetitoli.response.ReconciliationResponse;
import riconciliazionetitoli.service.RiconciliazioneTitoliService;

@RestController
@RequestMapping("/api/reconciliation")
public class RiconciliazioneController {
	
	@Autowired
	private RiconciliazioneTitoliService riconciliazioneService;
	
	@GetMapping("/report")
	public List<ReconciliationResponse> getData() {
		return riconciliazioneService.getReconciliationReport();
	}
	

	@PostMapping("/validate")
	public void validatePosition(@RequestBody RiconciliazioneManualeRequest request) throws RiconciliazioneTitoliException {
		riconciliazioneService.validateReconciliation(request);
	}
}
