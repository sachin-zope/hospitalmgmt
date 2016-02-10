package hospitalmgmt.beans;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "mtp_register")
public class MTPRegister {

	private int id;
	private int mtpSerialNo;
	private String gName;
	private String religion;
	private String married;
	private String mindication;
	private String procedure;
	private String batchNo;
	private int durationOfPregnancy;
	private String alongWith;
	private int mChildrens;
	private int fChildrens;
	private Date operationDate;
	private String doneByDr;
	private String opinionGivenBy;
	private Date createDate;
	private Date updateDate;
	
	@Id
	@Column(name = "mtp_register_id", unique = true, nullable = false)
	@GeneratedValue
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Column(name = "mtp_serial_no", nullable = false)
	public int getMtpSerialNo() {
		return mtpSerialNo;
	}

	public void setMtpSerialNo(int mtpSerialNo) {
		this.mtpSerialNo = mtpSerialNo;
	}

	@Column(name = "gaurdian_name", nullable = true)
	public String getgName() {
		return gName;
	}

	public void setgName(String gName) {
		this.gName = gName;
	}

	@Column(name = "religion", nullable = false)
	public String getReligion() {
		return religion;
	}

	public void setReligion(String religion) {
		this.religion = religion;
	}

	@Column(name = "married", nullable = true)
	public String getMarried() {
		return married;
	}

	public void setMarried(String married) {
		this.married = married;
	}

	@Column(name = "indication", nullable = true)
	public String getMindication() {
		return mindication;
	}

	public void setMindication(String mindication) {
		this.mindication = mindication;
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

	@Column(name = "male_childrens", nullable = true)
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
	
	@Column(name = "operation_date", nullable = false)
	public Date getOperationDate() {
		return operationDate;
	}

	public void setOperationDate(Date operationDate) {
		this.operationDate = operationDate;
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
		return "MTPRegister [id=" + id + ", mtpSerialNo=" + mtpSerialNo
				+ ", gName=" + gName + ", religion=" + religion
				+ ", mindication=" + mindication + ", procedure=" + procedure
				+ ", batchNo=" + batchNo + ", durationOfPregnancy="
				+ durationOfPregnancy + ", alongWith=" + alongWith
				+ ", mChildrens=" + mChildrens + ", fChildrens=" + fChildrens
				+ ", operationDate=" + operationDate + ", doneByDr=" + doneByDr
				+ ", opinionGivenBy=" + opinionGivenBy + ", createDate="
				+ createDate + ", updateDate=" + updateDate + "]";
	}
}