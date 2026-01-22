package riconciliazionetitoli.model;

import java.util.Date;

import lombok.ToString;

@ToString
public class ReconciliationNotesModel {
	
	private Long id;
	private String isin;
	private String portfolioId;
	private Boolean isValidated;
	private String note;
	private Long validatedBy;
	private Date validationDate;
	
	//costruttore con altro oggetto come parametro
	public ReconciliationNotesModel(ReconciliationNotesModel other) {
		this.id = other.id;
		this.isin = other.isin;
		this.portfolioId = other.portfolioId;
		this.isValidated = other.isValidated;
		this.note = other.note;
		this.validatedBy = other.validatedBy;
		this.validationDate = other.validationDate;
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
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
	
	public String getNote() {
		return note;
	}
	
	public void setNote(String note) {
		this.note = note;
	}
	
	public Long getValidatedBy() {
		return validatedBy;
	}
	
	public void setValidatedBy(Long validatedBy) {
		this.validatedBy = validatedBy;
	}
	
	public Date getValidationDate() {
		return validationDate;
	}
	
	public void setValidationDate(Date validationDate) {
		this.validationDate = validationDate;
	}
	
	public Boolean getIsValidated() {
		return isValidated;
	}
	
	public void setIsValidated(Boolean isValidated) {
		this.isValidated = isValidated;
	}
}
