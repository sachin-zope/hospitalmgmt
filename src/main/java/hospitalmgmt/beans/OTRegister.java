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
@Table(name = "ot_register")
public class OTRegister {

	private int id;
	private Long serialNo;
	private String nameOfSurgeon;
	private String assistant;
	private String anaesthetist;
	private Date operationDate;
	private Date createDate;
	private Date updateDate;

	@Id
	@Column(name = "ot_register_id", nullable = false)
	@GeneratedValue
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	@Column(name = "serial_no", nullable = false)
	public Long getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(Long serialNo) {
		this.serialNo = serialNo;
	}

	@Column(name = "name_of_surgeon", nullable = false)
	public String getNameOfSurgeon() {
		return nameOfSurgeon;
	}
	
	public void setNameOfSurgeon(String nameOfSurgeon) {
		this.nameOfSurgeon = nameOfSurgeon;
	}
	
	@Column(name = "assistant", nullable = true)
	public String getAssistant() {
		return assistant;
	}
	
	public void setAssistant(String assistant) {
		this.assistant = assistant;
	}
	
	@Column(name = "anaesthetist", nullable = true)
	public String getAnaesthetist() {
		return anaesthetist;
	}
	
	public void setAnaesthetist(String anaesthetist) {
		this.anaesthetist = anaesthetist;
	}
	
	@Column(name = "operation_date", nullable = false)
	public Date getOperationDate() {
		return operationDate;
	}

	public void setOperationDate(Date operationDate) {
		this.operationDate = operationDate;
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
		return "OTRegister [id=" + id + ", serialNo= " + serialNo + ", nameOfSurgeon=" + nameOfSurgeon
				+ ", assistant=" + assistant + ", anaesthetist=" + anaesthetist
				+ ", createDate=" + createDate + ", updateDate=" + updateDate
				+ "]";
	}
}
