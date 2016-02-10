package hospitalmgmt.transformer;

import hospitalmgmt.beans.DeliveryRegister;
import hospitalmgmt.beans.IndoorRegister;
import hospitalmgmt.beans.MTPRegister;
import hospitalmgmt.beans.MTPRegisterDetails;
import hospitalmgmt.beans.OTRegister;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class Transform {

	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	
	public void generateMonthlySerialNo(List<IndoorMTPDTO> indoorMTPDTOs) {
		int monthlySerialNo = 1;
		for (Iterator<IndoorMTPDTO> iterator = indoorMTPDTOs.iterator(); iterator.hasNext();) {
			IndoorMTPDTO indoorMTPDTO = iterator.next();
			indoorMTPDTO.setMtpMonthlySerialNo(monthlySerialNo++);
		}
	}
	
	public List<IndoorMTPDTO> transformMTPDetailsToMTP(List<MTPRegisterDetails> mtpRegisterDetails) {
		List<IndoorMTPDTO> indoorMTPDTOs = new ArrayList<IndoorMTPDTO>();
		for (Iterator<MTPRegisterDetails> iterator = mtpRegisterDetails.iterator(); iterator.hasNext();) {
			MTPRegisterDetails mtpDetails =  iterator.next();
			indoorMTPDTOs.add(transformMTPDetailsToMTP(mtpDetails));
		}
		return indoorMTPDTOs;		
	}
	
	public IndoorMTPDTO transformMTPDetailsToMTP(MTPRegisterDetails mtpRegisterDetails) {
		IndoorMTPDTO indoorMTPDTO = new IndoorMTPDTO();
		
		indoorMTPDTO.setpName(mtpRegisterDetails.getpName());
		indoorMTPDTO.setGender(mtpRegisterDetails.getGender()
				.equalsIgnoreCase("female") ? "F" : "M");
		indoorMTPDTO.setpAddress(mtpRegisterDetails.getpAddress());
		indoorMTPDTO.setAge(mtpRegisterDetails.getAge());
		
		MTPRegister mtpRegister = mtpRegisterDetails.getMtpRegister();
		indoorMTPDTO.setMtpSerialNo(mtpRegister.getMtpSerialNo());
		indoorMTPDTO.setReligion(mtpRegister.getReligion());
		indoorMTPDTO.setMarried(mtpRegister.getMarried());
		indoorMTPDTO.setMindication(mtpRegister.getMindication());
		indoorMTPDTO.setBatchNo(mtpRegister.getBatchNo());
		indoorMTPDTO.setProcedure(mtpRegister.getProcedure());
		indoorMTPDTO.setDurationOfPregnancy(mtpRegister
				.getDurationOfPregnancy());
		indoorMTPDTO.setAlongWith(mtpRegister.getAlongWith());
		indoorMTPDTO.setmChildrens(mtpRegister.getmChildrens());
		indoorMTPDTO.setfChildrens(mtpRegister.getfChildrens());
		indoorMTPDTO.setDtOpertation(mtpRegister.getOperationDate());
		indoorMTPDTO.setOperationDate(sdf.format(mtpRegister
				.getOperationDate()));
		indoorMTPDTO.setDoneByDr(mtpRegister.getDoneByDr());
		indoorMTPDTO.setOpinionGivenBy(mtpRegister.getOpinionGivenBy());
		
		return indoorMTPDTO;
	}
	
	public List<IndoorMTPDTO> transformIndoorToMTP(List<IndoorRegister> indoorRegisters) {
		List<IndoorMTPDTO> indoorMTPDTOs = new ArrayList<IndoorMTPDTO>();
		for (Iterator<IndoorRegister> iterator = indoorRegisters.iterator(); iterator.hasNext();) {
			IndoorRegister indoorRegister =  iterator.next();
			indoorMTPDTOs.add(transformIndoorToMTP(indoorRegister));
		}
		return indoorMTPDTOs;
	}
	
	public IndoorMTPDTO transformIndoorToMTP(IndoorRegister indoorRegister) {
		IndoorMTPDTO indoorMTPDTO = new IndoorMTPDTO();
		
		indoorMTPDTO.setpName(indoorRegister.getpName());
		indoorMTPDTO.setGender(indoorRegister.getGender()
				.equalsIgnoreCase("female") ? "F" : "M");
		indoorMTPDTO.setpAddress(indoorRegister.getpAddress());
		indoorMTPDTO.setAge(indoorRegister.getAge());
		indoorMTPDTO.setAdmitDate(sdf.format(indoorRegister.getAdmitDate()));
		if(indoorRegister.getDischargeDate() != null) {
			indoorMTPDTO.setDischargeDate(sdf.format(indoorRegister
				.getDischargeDate()));
		} else {
			indoorMTPDTO.setDischargeDate("");
		}
		
		MTPRegister mtpRegister = indoorRegister.getMtpRegister();
		indoorMTPDTO.setMtpSerialNo(mtpRegister.getMtpSerialNo());
		indoorMTPDTO.setReligion(mtpRegister.getReligion());
		indoorMTPDTO.setMarried(mtpRegister.getMarried());
		indoorMTPDTO.setMindication(mtpRegister.getMindication());
		indoorMTPDTO.setBatchNo(mtpRegister.getBatchNo());
		indoorMTPDTO.setProcedure(mtpRegister.getProcedure());
		indoorMTPDTO.setDurationOfPregnancy(mtpRegister
				.getDurationOfPregnancy());
		indoorMTPDTO.setAlongWith(mtpRegister.getAlongWith());
		indoorMTPDTO.setmChildrens(mtpRegister.getmChildrens());
		indoorMTPDTO.setfChildrens(mtpRegister.getfChildrens());
		indoorMTPDTO.setDtOpertation(mtpRegister.getOperationDate());
		indoorMTPDTO.setOperationDate(sdf.format(mtpRegister
				.getOperationDate()));
		indoorMTPDTO.setDoneByDr(mtpRegister.getDoneByDr());
		indoorMTPDTO.setOpinionGivenBy(mtpRegister.getOpinionGivenBy());
		
		return indoorMTPDTO;
	}

	public IndoorRegisterDTO transformIndoorRegister(
			IndoorRegister indoorRegister) {
		IndoorRegisterDTO indoorRegisterDTO = new IndoorRegisterDTO();
		indoorRegisterDTO.setId(indoorRegister.getId());
		indoorRegisterDTO.setIpdNo(indoorRegister.getIpdNo());
		indoorRegisterDTO.setSerialNo(indoorRegister.getSerialNo());
		indoorRegisterDTO
				.setAdmitDate(sdf.format(indoorRegister.getAdmitDate()));
		if(indoorRegister.getDischargeDate() != null) {
			indoorRegisterDTO.setDischargeDate(sdf.format(indoorRegister
				.getDischargeDate()));
		} else {
			indoorRegisterDTO.setDischargeDate("");
		}
		
		indoorRegisterDTO.setpName(indoorRegister.getpName());
		indoorRegisterDTO.setGender(indoorRegister.getGender()
				.equalsIgnoreCase("female") ? "F" : "M");
		indoorRegisterDTO.setpAddress(indoorRegister.getpAddress());
		indoorRegisterDTO.setAge(indoorRegister.getAge());
		indoorRegisterDTO.setDiagnosis(indoorRegister.getDiagnosis());
		indoorRegisterDTO.setTreatment(indoorRegister.getTreatment());
		indoorRegisterDTO.setRemarks(indoorRegister.getRemarks());
		indoorRegisterDTO.setFees(indoorRegister.getFees());
		indoorRegisterDTO.setDeliveryRegister(transformDeliveryRegister(indoorRegister
				.getDeliveryRegister()));
		indoorRegisterDTO.setOtRegister(transformOTRegister(indoorRegister.getOtRegister()));
		indoorRegisterDTO.setMtpRegister(transformMTPRegister(indoorRegister.getMtpRegister()));
		indoorRegisterDTO.setCreateDate(indoorRegister.getCreateDate());
		indoorRegisterDTO.setUpdateDate(indoorRegister.getUpdateDate());

		return indoorRegisterDTO;
	}

	public ArrayList<IndoorRegisterDTO> transformIndoorRegisters(
			List<IndoorRegister> indoorRegisters) {
		ArrayList<IndoorRegisterDTO> indoorRegisterDTOs = new ArrayList<IndoorRegisterDTO>();
		int monthlySerialNo = 1;
		
		for (Iterator<IndoorRegister> iterator = indoorRegisters.iterator(); iterator
				.hasNext();) {
			IndoorRegister indoorRegister = iterator.next();
			IndoorRegisterDTO dto = transformIndoorRegister(indoorRegister);
			if(dto.getMtpRegister() != null) {
				MTPRegisterDTO mtpRegisterDTO = dto.getMtpRegister();
				mtpRegisterDTO.setMtpMonthlySerialNo(monthlySerialNo++);
			}
			indoorRegisterDTOs.add(dto);
		}
		return indoorRegisterDTOs;
	}

	public DeliveryRegisterDTO transformDeliveryRegister(
			DeliveryRegister deliveryRegister) {
		DeliveryRegisterDTO deliveryRegisterDTO = new DeliveryRegisterDTO();
		
		if(deliveryRegister != null) {
			deliveryRegisterDTO.setId(deliveryRegister.getId());
			deliveryRegisterDTO.setSerialNo(deliveryRegister.getSerialNo());
			deliveryRegisterDTO.setDeliveryDate(sdf.format(deliveryRegister.getDeliveryDate()));
			deliveryRegisterDTO.setEpisiotomy(deliveryRegister.getEpisiotomy());
			deliveryRegisterDTO.setDeliveryType(deliveryRegister.getDeliveryType());
			deliveryRegisterDTO.setSexOfChild(deliveryRegister.getSexOfChild());
			deliveryRegisterDTO.setBirthWeight(deliveryRegister.getBirthWeight());
			String birthTime = deliveryRegister.getBirthTime();
			
		    Date dateObj = null;
			try {
				dateObj = new SimpleDateFormat("H:mm").parse(birthTime);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		    if(dateObj != null) {
		    	deliveryRegisterDTO.setBirthTime(new SimpleDateFormat("K:mm a").format(dateObj));
		    } else {
		    	deliveryRegisterDTO.setBirthTime(birthTime);
		    }
			deliveryRegisterDTO.setIndication(deliveryRegister.getIndication());
			deliveryRegisterDTO.setDeliveryRemarks(deliveryRegister.getDeliveryRemarks());
			deliveryRegisterDTO.setCreateDate(deliveryRegister.getCreateDate());
			deliveryRegisterDTO.setUpdateDate(deliveryRegister.getUpdateDate());
		}
		
		return deliveryRegisterDTO;
	}

	public List<DeliveryRegisterDTO> transformDeliveryRegisters(
			List<DeliveryRegister> deliveryRegisters) {
		List<DeliveryRegisterDTO> deliveryRegisterDTOs = new ArrayList<DeliveryRegisterDTO>();
		for(Iterator<DeliveryRegister> iterator = deliveryRegisters.iterator(); iterator.hasNext();) {
			DeliveryRegister deliveryRegister = iterator.next();
			deliveryRegisterDTOs.add(transformDeliveryRegister(deliveryRegister));
		}
		return deliveryRegisterDTOs;
	}

	public MTPRegisterDTO transformMTPRegister(MTPRegister mtpRegister) {
		MTPRegisterDTO mtpRegisterDTO = new MTPRegisterDTO();
		if(mtpRegister != null) {
			mtpRegisterDTO.setId(mtpRegister.getId());
			mtpRegisterDTO.setMtpSerialNo(mtpRegister.getMtpSerialNo());
			mtpRegisterDTO.setgName(mtpRegister.getgName());
			mtpRegisterDTO.setMarried(mtpRegister.getMarried());
			mtpRegisterDTO.setReligion(mtpRegister.getReligion());
			mtpRegisterDTO.setMindication(mtpRegister.getMindication());
			mtpRegisterDTO.setProcedure(mtpRegister.getProcedure());
			mtpRegisterDTO.setDurationOfPregnancy(mtpRegister
					.getDurationOfPregnancy());
			mtpRegisterDTO.setAlongWith(mtpRegister.getAlongWith());
			mtpRegisterDTO.setmChildrens(mtpRegister.getmChildrens());
			mtpRegisterDTO.setfChildrens(mtpRegister.getfChildrens());
			mtpRegisterDTO.setOperationDate(sdf.format(mtpRegister
					.getOperationDate()));
			mtpRegisterDTO.setDoneByDr(mtpRegister.getDoneByDr());
			mtpRegisterDTO.setOpinionGivenBy(mtpRegister.getOpinionGivenBy());
			mtpRegisterDTO.setCreateDate(mtpRegister.getCreateDate());
			mtpRegisterDTO.setUpdateDate(mtpRegister.getUpdateDate());
		}
		return mtpRegisterDTO;
	}

	public List<MTPRegisterDTO> transformMTPRegisters(
			List<MTPRegister> mtpRegisters) {
		List<MTPRegisterDTO> mtpRegisterDTOs = new ArrayList<MTPRegisterDTO>();
		int monthlySerialNo = 1;
		for (Iterator<MTPRegister> iterator = mtpRegisters.iterator(); iterator
				.hasNext();) {
			MTPRegister mtpRegister = iterator.next();
			MTPRegisterDTO mtpRegisterDTO = transformMTPRegister(mtpRegister);
			mtpRegisterDTO.setMtpMonthlySerialNo(monthlySerialNo++);
			mtpRegisterDTOs.add(mtpRegisterDTO);
		}
		return mtpRegisterDTOs;
	}

	public OTRegisterDTO transformOTRegister(OTRegister otRegister) {
		OTRegisterDTO otRegisterDTO = new OTRegisterDTO();
		if(otRegister != null) {
			otRegisterDTO.setId(otRegister.getId());
			otRegisterDTO.setSerialNo(otRegister.getSerialNo());
			otRegisterDTO.setNameOfSurgeon(otRegister.getNameOfSurgeon());
			otRegisterDTO.setAssistant(otRegister.getAssistant());
			otRegisterDTO.setAnaesthetist(otRegister.getAnaesthetist());
			otRegisterDTO.setOperationDate(sdf.format(otRegister.getOperationDate()));
			otRegisterDTO.setCreateDate(otRegister.getCreateDate());
			otRegisterDTO.setUpdateDate(otRegister.getUpdateDate());
		}	
		return otRegisterDTO;
	}

	public List<OTRegisterDTO> transformOTRegisters(List<OTRegister> otRegisters) {
		List<OTRegisterDTO> otRegisterDTOs = new ArrayList<OTRegisterDTO>();
		for(Iterator<OTRegister> iterator = otRegisters.iterator(); iterator.hasNext();) {
			OTRegister otRegister = iterator.next();
			otRegisterDTOs.add(transformOTRegister(otRegister));
		}
		return otRegisterDTOs;
	}
	
	public List<IndoorMTPDTO> mergeOrdered(final List<IndoorMTPDTO> list0,
			final List<IndoorMTPDTO> list1) {
		List<IndoorMTPDTO> result = new ArrayList<IndoorMTPDTO>();

		while (list0.size() > 0 && list1.size() > 0) {
			if (list0.get(0).compareTo(list1.get(0)) < 0) {
				result.add(list0.get(0));
				list0.remove(0);
			} else {
				result.add(list1.get(0));
				list1.remove(0);
			}
		}

		if (list0.size() > 0) {
			result.addAll(list0);
		} else if (list1.size() > 0) {
			result.addAll(list1);
		}

		return result;
	}

}
