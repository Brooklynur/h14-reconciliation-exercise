package riconciliazionetitoli.request;

import lombok.ToString;

@ToString
public class RiconciliazioneManualeRequest {
	
	private String isin;
	private String portfolioId;
	private String note;
	
	public String getNote() {
		return note;
	}
	
	public void setNote(String note) {
		this.note = note;
	}
	
	public String getIsin() {
		return isin;
	}
	
	public void setIsin(String isin) {
		this.isin = isin;
	}
	
	public String getPortfolioId() {
		return portfolioId;
	}
	
	public void setPortfolioId(String portfolioId) {
		this.portfolioId = portfolioId;
	}
}
