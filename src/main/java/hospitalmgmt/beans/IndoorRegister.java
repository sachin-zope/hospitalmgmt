package hospitalmgmt.beans;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "indoor_register")
public class IndoorRegister {

	private int id;
	private Long ipdNo;
	private int serialNo;
	private Date admitDate;
	private Date dischargeDate;
	private String pName;
	private String gender;
	private String pAddress;
	private int age;
	private String diagnosis;
	private String treatment;
	private String remarks;
	private double fees;
	private DeliveryRegister deliveryRegister;
	private MTPRegister mtpRegister;
	private OTRegister otRegister;
	private Date createDate;
	private Date updateDate;

	public IndoorRegister() {
	}
	
	public IndoorRegister(int id, String pName) {
		super();
		this.id = id;
		this.pName = pName;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	@Column(name = "ipd_no", nullable = false)
	public Long getIpdNo() {
		return ipdNo;
	}

	public void setIpdNo(Long ipdNo) {
		this.ipdNo = ipdNo;
	}
	
	@Column(name = "serial_no", nullable = true)
	public int getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(int serialNo) {
		this.serialNo = serialNo;
	}

	@Column(name = "admit_date", nullable = false)
	public Date getAdmitDate() {
		return admitDate;
	}

	public void setAdmitDate(Date admitDate) {
		this.admitDate = admitDate;
	}

	@Column(name = "discharge_date", nullable = true)
	public Date getDischargeDate() {
		return dischargeDate;
	}

	public void setDischargeDate(Date dischargeDate) {
		this.dischargeDate = dischargeDate;
	}

	@Column(name = "patient_name", nullable = false)
	public String getpName() {
		return pName;
	}

	public void setpName(String pName) {
		this.pName = pName;
	}

	@Column(name = "gender", nullable = false)
	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	@Column(name = "address", nullable = true)
	public String getpAddress() {
		return pAddress;
	}

	public void setpAddress(String pAddress) {
		this.pAddress = pAddress;
	}
	
	@Column(name = "age", nullable = false)
	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	@Column(name = "diagnosis", nullable = false)
	public String getDiagnosis() {
		return diagnosis;
	}

	public void setDiagnosis(String diagnosis) {
		this.diagnosis = diagnosis;
	}

	@Column(name = "treatment", nullable = false)
	public String getTreatment() {
		return treatment;
	}

	public void setTreatment(String treatment) {
		this.treatment = treatment;
	}

	@Column(name = "remarks", nullable = true)
	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	@Column(name = "fees", nullable = true)
	public double getFees() {
		return fees;
	}

	public void setFees(double fees) {
		this.fees = fees;
	}
	
	@OneToOne(cascade = CascadeType.ALL, fetch=FetchType.EAGER)
	@JoinColumn(name ="delivery_register_id")
	public DeliveryRegister getDeliveryRegister() {
		return deliveryRegister;
	}

	public void setDeliveryRegister(DeliveryRegister deliveryRegister) {
		this.deliveryRegister = deliveryRegister;
	}

	@OneToOne(cascade = CascadeType.ALL, fetch=FetchType.EAGER)
	@JoinColumn(name ="mtp_register_id")
	public MTPRegister getMtpRegister() {
		return mtpRegister;
	}

	public void setMtpRegister(MTPRegister mtpRegister) {
		this.mtpRegister = mtpRegister;
	}
	
	@OneToOne(cascade = CascadeType.ALL, fetch=FetchType.EAGER)
	@JoinColumn(name ="ot_register_id")
	public OTRegister getOtRegister() {
		return otRegister;
	}

	public void setOtRegister(OTRegister otRegister) {
		this.otRegister = otRegister;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_date", nullable = false, insertable = false, updatable = false)
	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "update_date", nullable = false, insertable = false, updatable = false)
	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	@Override
	public String toString() {
		return "IndoorRegister [id=" + id + ", ipdno= " + ipdNo + ", admitDate=" + admitDate
				+ ", dischargeDate=" + dischargeDate + ", pName=" + pName
				+ ", gender=" + gender + ", pAddress=" + pAddress
				+ ", diagnosis=" + diagnosis + ", treatment=" + treatment + ", remarks="
				+ remarks + ", fees=" + fees
				+ "]";
	}
}
