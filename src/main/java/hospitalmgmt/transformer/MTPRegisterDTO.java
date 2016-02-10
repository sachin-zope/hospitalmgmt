package hospitalmgmt.transformer;

import java.util.Date;

public class MTPRegisterDTO {

	private int id;
	private int mtpSerialNo;
	private int mtpMonthlySerialNo;
	private String gName;
	private String religion;
	private String married;
	private String mindication;
	private String procedure;
	private int durationOfPregnancy;
	private String alongWith;
	private int mChildrens;
	private int fChildrens;
	private String operationDate;
	private String doneByDr;
	private String opinionGivenBy;
	private Date createDate;
	private Date updateDate;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getMtpSerialNo() {
		return mtpSerialNo;
	}

	public void setMtpSerialNo(int mtpSerialNo) {
		this.mtpSerialNo = mtpSerialNo;
	}

	public int getMtpMonthlySerialNo() {
		return mtpMonthlySerialNo;
	}

	public void setMtpMonthlySerialNo(int mtpMonthlySerialNo) {
		this.mtpMonthlySerialNo = mtpMonthlySerialNo;
	}

	public String getgName() {
		return gName;
	}

	public void setgName(String gName) {
		this.gName = gName;
	}

	public String getReligion() {
		return religion;
	}

	public void setReligion(String religion) {
		this.religion = religion;
	}
	
	public String getMarried() {
		return married;
	}

	public void setMarried(String married) {
		this.married = married;
	}

	public String getMindication() {
		return mindication;
	}

	public void setMindication(String mindication) {
		this.mindication = mindication;
	}

	public String getProcedure() {
		return procedure;
	}

	public void setProcedure(String procedure) {
		this.procedure = procedure;
	}

	public int getDurationOfPregnancy() {
		return durationOfPregnancy;
	}

	public void setDurationOfPregnancy(int durationOfPregnancy) {
		this.durationOfPregnancy = durationOfPregnancy;
	}

	public String getAlongWith() {
		return alongWith;
	}

	public void setAlongWith(String alongWith) {
		this.alongWith = alongWith;
	}

	public int getmChildrens() {
		return mChildrens;
	}

	public void setmChildrens(int mChildrens) {
		this.mChildrens = mChildrens;
	}

	public int getfChildrens() {
		return fChildrens;
	}

	public void setfChildrens(int fChildrens) {
		this.fChildrens = fChildrens;
	}

	public String getOperationDate() {
		return operationDate;
	}

	public void setOperationDate(String operationDate) {
		this.operationDate = operationDate;
	}

	public String getDoneByDr() {
		return doneByDr;
	}

	public void setDoneByDr(String doneByDr) {
		this.doneByDr = doneByDr;
	}

	public String getOpinionGivenBy() {
		return opinionGivenBy;
	}

	public void setOpinionGivenBy(String opinionGivenBy) {
		this.opinionGivenBy = opinionGivenBy;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
}
