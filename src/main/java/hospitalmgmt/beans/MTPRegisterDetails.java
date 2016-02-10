package hospitalmgmt.beans;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "mtp_register_details")
public class MTPRegisterDetails {
	private int id;
	private String pName;
	private String gender;
	private String pAddress;
	private int age;
	/*private String religion;
	private String indication;
	private String procedure;
	private String batchNo;
	private int durationOfPregnancy;
	private String alongWith;
	private int mChildrens;
	private int fChildrens;
	private Date opdDate;
	private String doneByDr;
	private String opinionGivenBy;*/
	private String remarks;
	private double fees;
	private MTPRegister mtpRegister;
	private Date createDate;
	private Date updateDate;

	@Id
	@Column(name = "id", unique = true, nullable = false)
	@GeneratedValue
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	/*@Column(name = "religion", nullable = false)
	public String getReligion() {
		return religion;
	}

	public void setReligion(String religion) {
		this.religion = religion;
	}

	@Column(name = "indication", nullable = true)
	public String getIndication() {
		return indication;
	}

	public void setIndication(String indication) {
		this.indication = indication;
	}

	@Column(name = "mtp_procedure", nullable = true)
	public String getProcedure() {
		return procedure;
	}
	
	public void setProcedure(String procedure) {
		this.procedure = procedure;
	}
	
	@Column(name = "batch_no", nullable = true)
	public String getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}

	@Column(name = "duration_of_pregnancy", nullable = false)
	public int getDurationOfPregnancy() {
		return durationOfPregnancy;
	}

	public void setDurationOfPregnancy(int durationOfPregnancy) {
		this.durationOfPregnancy = durationOfPregnancy;
	}

	@Column(name = "along_with", nullable = true)
	public String getAlongWith() {
		return alongWith;
	}

	public void setAlongWith(String alongWith) {
		this.alongWith = alongWith;
	}

	@Column(name = "along_with", nullable = true)
	public int getmChildrens() {
		return mChildrens;
	}

	public void setmChildrens(int mChildrens) {
		this.mChildrens = mChildrens;
	}

	@Column(name = "female_childrens", nullable = true)
	public int getfChildrens() {
		return fChildrens;
	}

	public void setfChildrens(int fChildrens) {
		this.fChildrens = fChildrens;
	}

	@Column(name = "opd_date", nullable = false)
	public Date getOpdDate() {
		return opdDate;
	}

	public void setOpdDate(Date opdDate) {
		this.opdDate = opdDate;
	}

	@Column(name = "done_by_dr", nullable = true)
	public String getDoneByDr() {
		return doneByDr;
	}

	public void setDoneByDr(String doneByDr) {
		this.doneByDr = doneByDr;
	}

	@Column(name = "opinion_given_by", nullable = true)
	public String getOpinionGivenBy() {
		return opinionGivenBy;
	}

	public void setOpinionGivenBy(String opinionGivenBy) {
		this.opinionGivenBy = opinionGivenBy;
	}*/
	
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
	@JoinColumn(name ="mtp_register_id")
	public MTPRegister getMtpRegister() {
		return mtpRegister;
	}

	public void setMtpRegister(MTPRegister mtpRegister) {
		this.mtpRegister = mtpRegister;
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
		return "MTPRegisterDetails [id=" + id + ", pName=" + pName
				+ ", gender=" + gender + ", pAddress=" + pAddress + ", age="
				+ age + ", remarks=" + remarks + ", fees=" + fees
				+ ", createDate=" + createDate + ", updateDate=" + updateDate
				+ "]";
	}

}
