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
@Table(name = "delivery_register")
public class DeliveryRegister {
	
	private int id;
	private Long serialNo;
	private Date deliveryDate;
	private String episiotomy;
	private String deliveryType;
	private String sexOfChild;
	private double birthWeight;
	private String birthTime;
	private String indication;
	private String deliveryRemarks;
	private Date createDate;
	private Date updateDate;

	@Id
	@Column(name = "delivery_register_id", unique = true, nullable = false)
	@GeneratedValue
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	@Column(name ="serial_no", nullable = true)
	public Long getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(Long serialNo) {
		this.serialNo = serialNo;
	}

	@Column(name = "delivery_date", nullable = false)
	public Date getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(Date deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	@Column(name = "episiotomy", nullable = true)
	public String getEpisiotomy() {
		return episiotomy;
	}

	public void setEpisiotomy(String episiotomy) {
		this.episiotomy = episiotomy;
	}

	@Column(name = "delivery_type", nullable = false)
	public String getDeliveryType() {
		return deliveryType;
	}

	public void setDeliveryType(String deliveryType) {
		this.deliveryType = deliveryType;
	}

	@Column(name = "sex_of_child", nullable = false)
	public String getSexOfChild() {
		return sexOfChild;
	}

	public void setSexOfChild(String sexOfChild) {
		this.sexOfChild = sexOfChild;
	}

	@Column(name = "birth_weight", nullable = false)
	public double getBirthWeight() {
		return birthWeight;
	}

	public void setBirthWeight(double birthWeight) {
		this.birthWeight = birthWeight;
	}

	@Column(name = "birth_time", nullable = false)
	public String getBirthTime() {
		return birthTime;
	}

	public void setBirthTime(String birthTime) {
		this.birthTime = birthTime;
	}

	@Column(name = "indication", nullable = true)
	public String getIndication() {
		return indication;
	}

	public void setIndication(String indication) {
		this.indication = indication;
	}

	@Column(name = "remarks", nullable = true)
	public String getDeliveryRemarks() {
		return deliveryRemarks;
	}

	public void setDeliveryRemarks(String deliveryRemarks) {
		this.deliveryRemarks = deliveryRemarks;
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
		return "DeliveryRegister [id=" + id + ", deliveryDate=" + deliveryDate
				+ ", episiotomy=" + episiotomy + ", deliveryType="
				+ deliveryType + ", sexOfChild=" + sexOfChild
				+ ", birthWeight=" + birthWeight + ", birthTime=" + birthTime
				+ ", indication=" + indication + ", deliveryRemarks="
				+ deliveryRemarks + ", createDate=" + createDate
				+ ", updateDate=" + updateDate + "]";
	}
}
