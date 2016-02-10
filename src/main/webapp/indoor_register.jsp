<!DOCTYPE html>
<%@page import="hospitalmgmt.beans.IndoorRegister"%>
<html lang="en">
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1">

	<!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
	<title>Navjeevan Hospital</title>
	
	<script	src="js/jquery.min.js"></script>
	<script src="js/bootstrap.min.js"></script>
	<script src="http://ajax.aspnetcdn.com/ajax/jquery.validate/1.14.0/jquery.validate.min.js"></script>
	
	<link href="css/bootstrap.min.css" rel="stylesheet">
<style>
	#diagnosisOther {
		display: none;
		padding-top:10px;
	}
	
	#treatmentOther {
		display: none;
		padding-top:10px;
	}
	
	#treatmentMTP {
		display: none;
		padding-top: 10px;
	}
	
	#treatmentDelivery {
		display: none;
		padding-top: 10px;
	}
	
	#treatmentOperation {
		display: none;
		padding-top: 10px;
	}
	
	#treatmentLSCS {
		display: none;
		padding-top: 10px;
	}
	
	#indicationOther {
		display: none;
		padding-top: 10px;
	}
	
	#indicationOtherForLSCS {
		display: none;
		padding-top: 10px;
	}
	
	#treatmentMTPTubectomy {
		display: none;
		padding-top: 10px;
	}
	
	.form-horizontal .radio-inline {
		padding-top: 0px;
	}
</style>

<script type="text/javascript">

function validate() {
	if(!$("#admitDate").val()) {
		alert("Please select admit date");
		return false;
	}
	
	if(!$("#pName").val()) {
		alert("Please enter patient name");
		return false;
	}
	
	if(!$("#pAddress").val()) {
		alert("Please enter patient address");
		return false;
	}
	
	if(!$("#age").val()) {
		alert("Please enter age");
		return false;
	}
	
	if($('#diagnosis').val() == "-1") {
		alert("Please select Diagnosis");
		return false;
	}
	
	if($('#treatment').val() == "-1") {
		alert("Please select Treatment");
		return false;
	}
	
	$('#indoorForm').submit();
};


$(document).ready(function () {
	
	$('input[name=pName]').on('keyup', function(){
		var $this = $(this), value = $this.val(); 
        $this.val( value.toUpperCase() );
	});
	
	$('input[name=pAddress]').on('keyup', function(){
		var $this = $(this), value = $this.val(); 
        $this.val( value.toUpperCase() );
	});
	
	$('input[name="admitDate"]').change(function(){
        var doa = new Date(this.value);
        
        if($("#dischargeDate").val()) {
        	var dod =  new Date($("#dischargeDate").val());
        	if(doa.getTime() > dod.getTime()) {
    			alert("Admit date should be equal to or earlier than Discharge Date");
    			this.value = "";
    		}
        }
        
        if($("#mtpOperationDate").val()) {
        	var dop =  new Date($("#mtpOperationDate").val());
        	if(doa.getTime() > dop.getTime()) {
    			alert("Admit date should be equal to or earlier than Operation Date");
    			this.value = "";
    		}
        }
    });
	
	$('input[name="dischargeDate"]').change(function(){
        var dod = new Date(this.value);
        
        if($("#admitDate").val()) {
        	var doa = new Date($("#admitDate").val());
        	if(doa.getTime() > dod.getTime()) {
    			alert("Discharge date should be equal to or later than Admit Date");
    			this.value = "";
    		}
        }
        
        if($("#mtpOperationDate").val()) {
        	var dop =  new Date($("#mtpOperationDate").val());
        	if(dod.getTime() < dop.getTime()) {
    			alert("Discharge date should be equal to or later than Operation Date");
    			this.value = "";
    		}
        }
    });
	
	$('input[name="mtpOperationDate"]').change(function(){
        var dop = new Date(this.value);
        
        if($("#admitDate").val() && $("#dischargeDate").val()) {
        	var doa = new Date($("#admitDate").val());
        	var dod =  new Date($("#dischargeDate").val());
        	
        	if(dop.getTime() < doa.getTime() || dop.getTime() > dod.getTime()) {
        		alert("Operation Date should be equal to or in between of Admit Date and Discharge Date");
        		this.value = "";
        	}
        } else if($("#admitDate").val()) {
        	var doa = new Date($("#admitDate").val());
        	
        	if(dop.getTime() < doa.getTime()) {
        		alert("Operation Date should be equal to or later than Admit Date");
        		this.value = "";
        	}
		} else {
        	alert("Please select Admit Date first and then Operation Date");
        	this.value = "";
        }
        
        
        var doa = new Date($("#admitDate").val());
        
        if(doa.getTime() > dod.getTime()) {
			alert("Discharge date should be later than Admit Date");
			this.value = "";
		}
    });
	
	
    $("#reponseAlert").fadeTo(2000, 500).slideUp(500, function(){
    });
    
	$('select[name=diagnosis]').change(function(e){
	  if ($(this).val() == 'other'){
	    $('#diagnosisOther').show();
	  }else{
	    $('#diagnosisOther').hide();
	  }
	});
    

	$('select[name=treatment]').change(function(){
	  if ($(this).val() == 'Other'){
	    $('#treatmentOther').show();
	    $('#treatmentMTP').hide();
	    $('#treatmentDelivery').hide();
	    $('#treatmentOperation').show();
	    $('#treatmentLSCS').hide();
	    $('#treatmentMTPTubectomy').hide();
	  } else if($(this).val() == 'MTP') {
		$('#treatmentMTP').show();
		$('#treatmentOther').hide();
		$('#treatmentDelivery').hide();
		$('#treatmentOperation').hide();
		$('#treatmentLSCS').hide();
		$('#treatmentMTPTubectomy').hide();
	  } else if($(this).val() == 'MTP + Abdominal Tubectomy' || $(this).val() == 'MTP + Laparoscopic Tubectomy') {
		$('#treatmentMTPTubectomy').show();
		$('#treatmentMTP').hide();
		$('#treatmentOther').hide();
		$('#treatmentDelivery').hide();
		$('#treatmentOperation').hide();
		$('#treatmentLSCS').hide();
	  } else if($(this).val() == 'Delivery') { 
	    $('#treatmentDelivery').show();
	    $('#treatmentOther').hide();
	    $('#treatmentMTP').hide();
	    $('#treatmentOperation').hide();
	    $('#treatmentLSCS').hide();
	    $('#treatmentMTPTubectomy').hide();
	    	
    	$('#indication').attr('disabled', false);
	    
	    $('select[name=indication]').change(function(e){
	  	  if ($(this).val() == 'other'){
	  	    $('#indicationOther').show();
	  	  }else{
	  	    $('#indicationOther').hide();
	  	  }
	  	});
	  } else if($(this).val() == 'LSCS' || $(this).val() == 'LSCS with Tubectomy') { 
		  $('#treatmentOther').hide();
	  	  $('#treatmentLSCS').show();
		  $('#treatmentMTP').hide();
		  $('#treatmentDelivery').hide();
		  $('#treatmentOperation').hide();
		  $('#treatmentMTPTubectomy').hide();
		  $('#deliveryTypeForLSCS option[value="LSCS"]').attr('selected', true);
	      $('#indicationForLSCS').attr('disabled', false);
		  
	      $('select[name=indicationForLSCS]').change(function(e){
		  	  if ($(this).val() == 'other'){
		  	    $('#indicationOtherForLSCS').show();
		  	  } else { 
		  	    $('#indicationOtherForLSCS').hide();
		  	  }
		  });
	  } else {
	    $('#treatmentOther').hide();
	    $('#treatmentLSCS').hide();
	    $('#treatmentMTP').hide();
	    $('#treatmentDelivery').hide();
	    $('#treatmentOperation').show();
	    $('#treatmentMTPTubectomy').hide();
	  }
	});
});
</script>

</head>
<body>
	<jsp:include page="nav.html"></jsp:include>

	<% 
		if(request.getAttribute("RESP") != null) {
			String resp = request.getAttribute("RESP").toString();
			if(resp.equalsIgnoreCase("success")) {
	%>
				<div class="alert alert-success alert-dismissible" role="alert" id="reponseAlert">
				  <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
				  Record is successfully saved!
				</div>
	<%
			} else if(resp.equalsIgnoreCase("error")) {
				String err = request.getAttribute("ERROR").toString();
	%>
				<div class="alert alert-danger alert-dismissible" role="alert" id="reponseAlert">
				  <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
				  There is some error saving record. Try once again!
				  <br><strong>Error :</strong> <%= err %>
				</div>
	<%	
			}
		}
	%>
	
	<div class="container">
		<div class="page-header">
		  <h3>Indoor Register</h3>
		</div>

		<div class="row">
			<form class="form-horizontal" method="post" action="indoorregisterservlet" id="indoorForm">
			<input type="hidden" name="action" value="add">
				<div class="form-group">
					<label for="admitDate" class="col-sm-2 control-label">Admit
						Date</label>
					<div class="col-sm-3">
						<input type="date" class="form-control" id="admitDate" name="admitDate"
							placeholder="Admit Date" required>
					</div>

					<label for="dischargeDate" class="col-sm-2 control-label">Discharge
						Date</label>
					<div class="col-sm-3">
						<input type="date" class="form-control" id="dischargeDate" name="dischargeDate"
							placeholder="Discharge Date">
					</div>
				</div>

				<div class="form-group">
					<label for="pName" class="col-sm-2 control-label">Name</label>
					<div class="col-sm-4">
						<input type="text" class="form-control" id="pName" name="pName"
							placeholder="Patient Name" required>
					</div>

					<label class="col-sm-1 control-label"></label>
					<div class="col-sm-4">
						<div class="radio">
							<label class="radio-inline"> <input type="radio"
								name="gender" id="male" value="male">
								Male
							</label> <label class="radio-inline"> <input type="radio"
								name="gender" id="female" value="female" checked>
								Female
							</label>
						</div>
					</div>
				</div>

				<div class="form-group">
					<label for="pAddress" class="col-sm-2 control-label">Address</label>
					<div class="col-sm-4">
						<textarea class="form-control" rows="3" id="pAddress" name="pAddress" placeholder="Address" required></textarea>
					</div>
					
					<label for="age" class="col-sm-1 control-label">Age
						</label>
					<div class="col-sm-3">
						<input type="number" class="form-control" id="age" name="age"
							placeholder="Age" max="100" min="1" required>
					</div>
				</div>

				<div class="form-group">
					<label for="diagnosis" class="col-sm-2 control-label">Diagnosis</label>
					<div class="col-sm-3">
						<select name="diagnosis" id="diagnosis" class="form-control">
							<option value="-1">Select Diagnosis</option>
							<option>Primigravida</option>
							<option>G2P1L0</option>
							<option>G2P1L1</option>
							<option>G3P2L2</option>
							<option>G4P3L3</option>
							<option>Primigravida with CPD</option>
							<option>Primigravida with PIH with oligohydramnios</option>
							<option>Primigravida with failure to progress</option>
							<option>G2P1L1 with previous LSCS with CPD</option>
							<option>Primigravida with breech presentation</option>
							<option>G2P1L1 with previous LSCS</option>
							<option>P2L2 for tubal ligation</option>
							<option>P3L3 for tubal ligation</option>
							<option>Missed Abortion</option>
							<option>Incomplete Abortion</option>
							<option>Chronic Cervicitis</option>
							<option>Dysfunctional uterine bleeding</option>
							<option>Uterine prolapse with CRE</option>
							<option>Fibroid uterus</option>
							<option>Infertility for diagnostic laparoscopy</option>
							<option>Ovarian cyst</option>
							<option value="other">Other</option>
						</select>
						<div id="diagnosisOther">
							<input name="OtherDiagnosis" class="form-control" type="text"
								placeholder="Other Diagnosis" />
						</div>
					</div>

					<label for="treatment" class="col-sm-2 control-label">Treatment</label>
					<div class="col-sm-3">
						<select name="treatment" id="treatment" class="form-control">
							<option value="-1">Select Treatment</option>
							<option value="Delivery">Delivery</option>
							<option value="LSCS">LSCS</option>
							<option value="LSCS with Tubectomy">LSCS with Tubectomy</option>
							<option value="MTP">MTP</option>
							<option value="MTP + Abdominal Tubectomy">MTP + Abdominal Tubectomy</option>
							<option value="MTP + Laparoscopic Tubectomy">MTP + Laparoscopic Tubectomy</option>
							<option value="Tubectomy">Tubectomy</option>
							<option value="Abdominal Tubectomy">Abdominal Tubectomy</option>
							<option value="Laparoscopic Tubectomy">Laparoscopic Tubectomy</option>
							<option value="Abdominal Hysterectomy">Abdominal Hysterectomy</option>
							<option value="Vaginal Hysterectomy">Vaginal Hysterectomy</option>
							<option value="Diagnostic Laparohysteroscopy">Diagnostic Laparohysteroscopy</option>
							<option value="Laparoscopic Hysterectomy">Laparoscopic Hysterectomy</option>
							<option value="D and E">D and E</option>
							<option value="Cervical Encirclage">Cervical Encirclage</option>
							<option value="Other">Other</option>
						</select>
						<div id="treatmentOther">
							<input name="OtherTreatment" class="form-control" type="text"
									placeholder="Other Treatment" />
						</div>
					</div>
				</div>

				<div id="treatmentMTP">
					<div class="form-group">
						<label for="durationOfPregnancy" class="col-sm-2 control-label">Duration of Pregnancy</label>
						<div class="col-sm-2">
							<input type="number" class="form-control" id="durationOfPregnancy" name="durationOfPregnancy" min="1" max="40">
						</div>
						<div class="col-sm-1">
							<label class="col-sm-2 control-label">Week's</label>
						</div>
						
						<label for="mtpOperationDate" class="col-sm-2 control-label">Operation
						Date</label>
						<div class="col-sm-3">
							<input type="date" class="form-control" id="mtpOperationDate" name="mtpOperationDate"
								placeholder="Operation Date">
						</div>
					</div>
					<div class="form-group">
						<label for="religion" class="col-sm-2 control-label">Religion</label>
						<div class="col-sm-3">
							<select name="religion" class="form-control">
								<option value="hindu">Hindu</option>
								<option value="muslim">Muslim</option>
								<option value="Shikh">Shikh</option>
							</select>
						</div>

						<label class="col-sm-2 control-label"></label>
						<div class="col-sm-4">
							<div class="radio">
								<label class="radio-inline"> <input type="radio"
									name="married" id="married" value="Married" checked>
									Married
								</label> <label class="radio-inline"> <input type="radio"
									name="married" id="unmarried" value="Unmarried">
									Unmarried
								</label>
							</div>
						</div>
					</div>
					<div class="form-group">
						<label for="mindication" class="col-sm-2 control-label">Indication</label>
						<div class="col-sm-3">
							<select name="mindication" class="form-control">
								<option value=""></option>
								<option value="A">A</option>
								<option value="B">B</option>
								<option value="C">C</option>
								<option value="D">D</option>
								<option value="E">E</option>
							</select>
						</div>

						<label for="procedure" class="col-sm-2 control-label">Procedure</label>
						<div class="col-sm-3">
							<select name="procedure" class="form-control">
								<option value=""></option>
								<option value="D & E">D & E</option>
								<option value="Second trimester termination">Second trimester termination</option>
								<option value="Medication abortion">Medication abortion</option>
							</select>
						</div>
					</div>
					
					<div class="form-group">
						<label for="alongWith" class="col-sm-2 control-label">Along With</label>
						<div class="col-sm-3">
							<select name="alongWith" class="form-control">
								<option value=""></option>
								<option value="Coper T">Coper T</option>
								<option value="Tubectomy">Tubectomy</option>
								<option value="Injectable Contraceptive">Injectable Contraceptive</option>
								<option value="Barrier Contraceptive">Barrier Contraceptive</option>
							</select>
						</div>
						
						<label for="totalChildrens" class="col-sm-2 control-label">Total Childrens</label>
						<div class="col-sm-1">
							<input type="number" class="form-control" id="mChildrens" name="mChildrens"
								placeholder="M" min="0" max="10">
						</div>
						
						<div class="col-sm-1">
							<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
						</div>
						
						<div class="col-sm-1">
							<input type="number" class="form-control" id="fChildrens" name="fChildrens"
								placeholder="F" min="0" max="10">
						</div>
					</div>
					
					<div class="form-group">
						<label for="doneby" class="col-sm-2 control-label">Done By Dr.</label>
						<div class="col-sm-3">
							<input type="text" class="form-control" id="doneby" name="doneby"
								placeholder="Done by Dr.">
						</div>

						<label for="opinionby" class="col-sm-2 control-label">Opinion Given By</label>
						<div class="col-sm-3">
							<input type="text" class="form-control" id="opinionby" name="opinionby"
								placeholder="Opinion Given By">
						</div>
					</div>
				</div>
				
				<div id="treatmentMTPTubectomy">
					<div class="form-group">
						<label for="MTdurationOfPregnancy" class="col-sm-2 control-label">Duration of Pregnancy</label>
						<div class="col-sm-2">
							<input type="number" class="form-control" id="MTdurationOfPregnancy" name="MTdurationOfPregnancy" min="1" max="40">
						</div>
						<div class="col-sm-1">
							<label class="col-sm-2 control-label">Week's</label>
						</div>
						
						<label for="MTmtpOperationDate" class="col-sm-2 control-label">Operation
						Date</label>
						<div class="col-sm-3">
							<input type="date" class="form-control" id="MTmtpOperationDate" name="MTmtpOperationDate"
								placeholder="Operation Date">
						</div>
					</div>
					<div class="form-group">
						<label for="MTreligion" class="col-sm-2 control-label">Religion</label>
						<div class="col-sm-3">
							<select name="MTreligion" class="form-control">
								<option value="hindu">Hindu</option>
								<option value="muslim">Muslim</option>
								<option value="Shikh">Shikh</option>
							</select>
						</div>

						<label class="col-sm-2 control-label"></label>
						<div class="col-sm-4">
							<div class="radio">
								<label class="radio-inline"> <input type="radio"
									name="MTmarried" id="married" value="Married" checked>
									Married
								</label> <label class="radio-inline"> <input type="radio"
									name="MTmarried" id="unmarried" value="Unmarried">
									Unmarried
								</label>
							</div>
						</div>
					</div>
					<div class="form-group">
						<label for="mindication" class="col-sm-2 control-label">Indication</label>
						<div class="col-sm-3">
							<select name="MTmindication" class="form-control">
								<option value=""></option>
								<option value="A">A</option>
								<option value="B">B</option>
								<option value="C">C</option>
								<option value="D">D</option>
								<option value="E">E</option>
							</select>
						</div>

						<label for="MTprocedure" class="col-sm-2 control-label">Procedure</label>
						<div class="col-sm-3">
							<select name="MTprocedure" class="form-control">
								<option value=""></option>
								<option value="D & E">D & E</option>
								<option value="Second trimester termination">Second trimester termination</option>
								<option value="Medication abortion">Medication abortion</option>
							</select>
						</div>
					</div>
					
					<div class="form-group">
						<label for="MTalongWith" class="col-sm-2 control-label">Along With</label>
						<div class="col-sm-3">
							<select name="MTalongWith" class="form-control">
								<option value=""></option>
								<option value="Coper T">Coper T</option>
								<option value="Tubectomy">Tubectomy</option>
								<option value="Injectable Contraceptive">Injectable Contraceptive</option>
								<option value="Barrier Contraceptive">Barrier Contraceptive</option>
							</select>
						</div>
						
						<label for="MTtotalChildrens" class="col-sm-2 control-label">Total Childrens</label>
						<div class="col-sm-1">
							<input type="number" class="form-control" id="MTmChildrens" name="MTmChildrens"
								placeholder="M" min="0" max="10">
						</div>
						
						<div class="col-sm-1">
							<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
						</div>
						
						<div class="col-sm-1">
							<input type="number" class="form-control" id="MTfChildrens" name="MTfChildrens"
								placeholder="F" min="0" max="10">
						</div>
					</div>
					
					<div class="form-group">
						<label for="MTdoneby" class="col-sm-2 control-label">Done By Dr.</label>
						<div class="col-sm-3">
							<input type="text" class="form-control" id="MTdoneby" name="MTdoneby"
								placeholder="Done by Dr.">
						</div>

						<label for="MTopinionby" class="col-sm-2 control-label">Opinion Given By</label>
						<div class="col-sm-3">
							<input type="text" class="form-control" id="MTopinionby" name="MTopinionby"
								placeholder="Opinion Given By">
						</div>
					</div>
					
					<div class="form-group">
						<label for="assistantForTubectomy" class="col-sm-2 control-label">Assistant</label>
						<div class="col-sm-3">
							<input type="text" class="form-control" id="assistantForTubectomy" name="assistantForTubectomy"
								placeholder="Assistant">
						</div>
						
						<label for="anaesthetistForTubectomy" class="col-sm-2 control-label">Anaesthetist</label>
						<div class="col-sm-3">
							<input type="text" class="form-control" id="anaesthetistForTubectomy" name="anaesthetistForTubectomy"
								placeholder="Anaesthetist" >
						</div>
					</div>
				</div>
				
				<div id="treatmentDelivery">
					<div class="form-group">
						<label for="deliveryDate" class="col-sm-2 control-label">Delivery
						Date</label>
						<div class="col-sm-3">
							<input type="date" class="form-control" id="deliveryDate" name="deliveryDate"
								placeholder="DD/MM/YYYY">
						</div>
					
						<label class="col-sm-2 control-label"></label>
						<div class="col-sm-4">
							<div class="checkbox">
								<label class="radio-inline"> <input type="checkbox"
									name="episiotomy" id="episiotomy" value="Episiotomy">
									Episiotomy
								</label> 
							</div>
						</div>
					</div>
					
					<div class="form-group">
						<label for="deliveryType" class="col-sm-2 control-label">Delivery Type</label>
						<div class="col-sm-3">
							<select id="deliveryType" name="deliveryType" class="form-control">
								<option value=""></option>
								<option value="Vaccum">Vaccum</option>
								<option value="Forceps">Forceps</option>
								<option value="VBAC">VBAC</option>
								<option value="LSCS">LSCS</option>
								<option value="FTND">FTND</option>
								<option value="PTVD">PTVD</option>
							</select>
						</div>

						<label class="col-sm-2 control-label">Sex of Child</label>
						<div class="col-sm-3">
							<div class="radio">
								<label class="radio-inline"> <input type="radio"
									name="sexOfChild" id="gMale" value="Male">
									Male
								</label> <label class="radio-inline"> <input type="radio"
									name="sexOfChild" id="gFemale" value="Female">
									Female
								</label>
							</div>
						</div>
					</div>
					
					<div class="form-group">
						<label for="birthWeight" class="col-sm-2 control-label">Birth Weight</label>
						<div class="col-sm-3">
							<input type="number" class="form-control" id="birthWeight" name="birthWeight"
								placeholder="Weight" step="any">
						</div>

						<label for="birthTime" class="col-sm-2 control-label">Birth Time</label>
						<div class="col-sm-3">
							<input type="time" class="form-control" id="birthTime" name="birthTime"
								placeholder="Time">
						</div>
					</div>
					
					<div class="form-group">
						<label for="deliveryRemarks" class="col-sm-2 control-label">Delivery Remarks</label>
						<div class="col-sm-3">
							<input type="text" class="form-control" id="deliveryRemarks" name="deliveryRemarks"
								placeholder="Remarks">
						</div>
					</div>
				</div>
				
				<div id="treatmentLSCS">
					<div class="form-group">
						<label for="deliveryDateForLSCS" class="col-sm-2 control-label">Operation Date</label>
						<div class="col-sm-3">
							<input type="date" class="form-control" id="deliveryDateForLSCS" name="deliveryDateForLSCS"
								placeholder="DD/MM/YYYY">
						</div>
					
						<label class="col-sm-2 control-label"></label>
						<div class="col-sm-4">
							<div class="checkbox">
								<label class="radio-inline"> <input type="checkbox"
									name="episiotomyForLSCS" id="episiotomyForLSCS" value="Episiotomy">
									Episiotomy
								</label> 
							</div>
						</div>
					</div>
					
					<div class="form-group">
						<label for="deliveryTypeForLSCS" class="col-sm-2 control-label">Delivery Type</label>
						<div class="col-sm-3">
							<select id="deliveryTypeForLSCS" name="deliveryTypeForLSCS" class="form-control">
								<option value=""></option>
								<option value="Vaccum">Vaccum</option>
								<option value="Forceps">Forceps</option>
								<option value="VBAC">VBAC</option>
								<option value="LSCS">LSCS</option>
								<option value="FTND">FTND</option>
								<option value="PTVD">PTVD</option>
							</select>
						</div>

						<label class="col-sm-2 control-label">Sex of Child</label>
						<div class="col-sm-3">
							<div class="radio">
								<label class="radio-inline"> <input type="radio"
									name="sexOfChildForLSCS" id="gMaleForLSCS" value="Male">
									Male
								</label> <label class="radio-inline"> <input type="radio"
									name="sexOfChildForLSCS" id="gFemaleForLSCS" value="Female">
									Female
								</label>
							</div>
						</div>
					</div>
					
					<div class="form-group">
						<label for="birthWeightForLSCS" class="col-sm-2 control-label">Birth Weight</label>
						<div class="col-sm-3">
							<input type="number" class="form-control" id="birthWeightForLSCS" name="birthWeightForLSCS"
								placeholder="Weight" step="any">
						</div>

						<label for="birthTimeForLSCS" class="col-sm-2 control-label">Birth Time</label>
						<div class="col-sm-3">
							<input type="time" class="form-control" id="birthTimeForLSCS" name="birthTimeForLSCS"
								placeholder="Time">
						</div>
					</div>
					
					<div class="form-group">
						<label for="deliveryRemarksForLSCS" class="col-sm-2 control-label">Delivery Remarks</label>
						<div class="col-sm-3">
							<input type="text" class="form-control" id="deliveryRemarksForLSCS" name="deliveryRemarksForLSCS"
								placeholder="Remarks">
						</div>

						<label for="NameOfSurgeonForLSCS" class="col-sm-2 control-label">Name of Surgeon</label>
						<div class="col-sm-3">
							<input type="text" class="form-control" id="NameOfSurgeonForLSCS" name="NameOfSurgeonForLSCS"
								placeholder="Name Of Surgeon" >
						</div>
					</div>
					
					<div class="form-group">
						<label for="assistantForLSCS" class="col-sm-2 control-label">Assistant</label>
						<div class="col-sm-3">
							<input type="text" class="form-control" id="assistantForLSCS" name="assistantForLSCS"
								placeholder="Assistant">
						</div>
						
						<label for="anaesthetistForLSCS" class="col-sm-2 control-label">Anaesthetist</label>
						<div class="col-sm-3">
							<input type="text" class="form-control" id="anaesthetistForLSCS" name="anaesthetistForLSCS"
								placeholder="Anaesthetist" >
						</div>
					</div>
				</div>
				
				<div id="treatmentOperation">
					<div class="form-group">
						<label for="operationDate" class="col-sm-2 control-label">Operation
						Date</label>
						<div class="col-sm-3">
							<input type="date" class="form-control" id="operationDate" name="operationDate"
								placeholder="DD/MM/YYYY">
						</div>
						
						<label for="NameOfSurgeon" class="col-sm-2 control-label">Name of Surgeon</label>
						<div class="col-sm-3">
							<input type="text" class="form-control" id="NameOfSurgeon" name="NameOfSurgeon"
								placeholder="Name Of Surgeon" >
						</div>
					</div>
					
					<div class="form-group">
						<label for="assistant" class="col-sm-2 control-label">Assistant</label>
						<div class="col-sm-3">
							<input type="text" class="form-control" id="assistant" name="assistant"
								placeholder="Assistant">
						</div>
						
						<label for="anaesthetist" class="col-sm-2 control-label">Anaesthetist</label>
						<div class="col-sm-3">
							<input type="text" class="form-control" id="anaesthetist" name="anaesthetist"
								placeholder="Anaesthetist" >
						</div>
					</div>
				</div>
				
				<div class="form-group">
					<label for="fees" class="col-sm-2 control-label">Fees</label>
					<div class="col-sm-3">
						<input type="text" class="form-control" id="fees" name="fees"
							placeholder="Fees">
					</div>

					<label for="remarks" class="col-sm-2 control-label">Remarks</label>
					<div class="col-sm-3">
						<input type="text" class="form-control" id="remarks" name="remarks"
							placeholder="Remarks">
					</div>
				</div>

				<div class="form-group">
					<div class="col-sm-offset-2 col-sm-10">
							<button type="button" class="btn btn-success" onclick="validate()">Save</button>
							<button type="button" class="btn btn-default">Cancel</button>
					</div>
				</div>
			</form>
		</div>
	</div>
</body>
</html>
