package hospitalmgmt.transformer;

import java.util.Date;


public class IndoorMTPDTO implements Comparable<IndoorMTPDTO> {

	private String pName;
	private String gender;
	private String pAddress;
	private int age;

	private String admitDate;
	private String dischargeDate;
	private int mtpSerialNo;
	private int mtpMonthlySerialNo;
	private String religion;
	private String married;
	private String mindication;
	private String procedure;
	private String batchNo;
	private int durationOfPregnancy;
	private String alongWith;
	private int mChildrens;
	private int fChildrens;
	private Date dtOpertation;
	private String operationDate;
	private String doneByDr;
	private String opinionGivenBy;

	public String getpName() {
		return pName;
	}

	public void setpName(String pName) {
		this.pName = pName;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getpAddress() {
		return pAddress;
	}

	public void setpAddress(String pAddress) {
		this.pAddress = pAddress;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}
	
	public String getAdmitDate() {
		return admitDate;
	}

	public void setAdmitDate(String admitDate) {
		this.admitDate = admitDate;
	}

	public String getDischargeDate() {
		return dischargeDate;
	}

	public void setDischargeDate(String dischargeDate) {
		this.dischargeDate = dischargeDate;
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

	public String getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
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
	
	public Date getDtOpertation() {
		return dtOpertation;
	}

	public void setDtOpertation(Date dtOpertation) {
		this.dtOpertation = dtOpertation;
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

	public int compareTo(IndoorMTPDTO o) {
		int res = 0;
		if(this.mtpSerialNo < o.mtpSerialNo) {
			res = -1;
		} else if (this.mtpSerialNo > o.mtpSerialNo) {
			res = 1;
		}
		return res;
	}
}
