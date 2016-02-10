package hospitalmgmt.transformer;

import hospitalmgmt.beans.DeliveryRegister;
import hospitalmgmt.beans.MTPRegister;
import hospitalmgmt.beans.OTRegister;

import java.util.Date;

public class IndoorRegisterDTO {
	private int id;
	private Long ipdNo;
	private int serialNo;
	private String admitDate;
	private String dischargeDate;
	private String pName;
	private String gender;
	private String pAddress;
	private int age;
	private String diagnosis;
	private String treatment;
	private String remarks;
	private double fees;
	private DeliveryRegisterDTO deliveryRegister;
	private MTPRegisterDTO mtpRegister;
	private OTRegisterDTO otRegister;
	private Date createDate;
	private Date updateDate;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Long getIpdNo() {
		return ipdNo;
	}

	public void setIpdNo(Long ipdNo) {
		this.ipdNo = ipdNo;
	}

	public int getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(int serialNo) {
		this.serialNo = serialNo;
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

	public String getDiagnosis() {
		return diagnosis;
	}

	public void setDiagnosis(String diagnosis) {
		this.diagnosis = diagnosis;
	}

	public String getTreatment() {
		return treatment;
	}

	public void setTreatment(String treatment) {
		this.treatment = treatment;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public double getFees() {
		return fees;
	}

	public void setFees(double fees) {
		this.fees = fees;
	}

	public DeliveryRegisterDTO getDeliveryRegister() {
		return deliveryRegister;
	}

	public void setDeliveryRegister(DeliveryRegisterDTO deliveryRegister) {
		this.deliveryRegister = deliveryRegister;
	}

	public MTPRegisterDTO getMtpRegister() {
		return mtpRegister;
	}

	public void setMtpRegister(MTPRegisterDTO mtpRegister) {
		this.mtpRegister = mtpRegister;
	}

	public OTRegisterDTO getOtRegister() {
		return otRegister;
	}

	public void setOtRegister(OTRegisterDTO otRegister) {
		this.otRegister = otRegister;
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
