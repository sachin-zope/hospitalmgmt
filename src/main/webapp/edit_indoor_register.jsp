<!DOCTYPE html>
<%@page import="java.text.SimpleDateFormat"%>
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
	  } else if($(this).val() == 'MTP' || $(this).val() == 'MTP + Tubectomy') {
		$('#treatmentMTP').show();
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
	    	
    	$('#indication').attr('disabled', false);
	    
	    $('select[name=indication]').change(function(e){
	  	  if ($(this).val() == 'other'){
	  	    $('#indicationOther').show();
	  	  }else{
	  	    $('#indicationOther').hide();
	  	  }
	  	});
	  } else if($(this).val() == 'LSCS') { 
		  $('#treatmentOther').hide();
	  	  $('#treatmentLSCS').show();
		  $('#treatmentMTP').hide();
		  $('#treatmentDelivery').hide();
		  $('#treatmentOperation').hide();
		  
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
		<%
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			IndoorRegister indoorRegister = (IndoorRegister) request.getAttribute("INDOOR_REGISTER");
		%>
		<div class="row">
			<form class="form-horizontal" method="post" action="indoorregisterservlet" id="indoorForm">
				<input type="hidden" name="action" value="edit">
				<input type="hidden" name="indoorId" value="<%= indoorRegister.getId() %>>">
				<div class="form-group">
					<label for="admitDate" class="col-sm-2 control-label">Admit
						Date</label>
					<div class="col-sm-3">
						<input type="date" class="form-control" id="admitDate" name="admitDate"
							placeholder="Admit Date" value='<%= sdf.format(indoorRegister.getAdmitDate()) %>'>
					</div>

					<label for="dischargeDate" class="col-sm-2 control-label">Discharge
						Date</label>
					<div class="col-sm-3">
						<input type="date" class="form-control" id="dischargeDate" name="dischargeDate"
							placeholder="Discharge Date" value='<%= sdf.format(indoorRegister.getDischargeDate()) %>'>
					</div>
				</div>

				<div class="form-group">
					<label for="pName" class="col-sm-2 control-label">Name</label>
					<div class="col-sm-4">
						<input type="text" class="form-control" id="pName" name="pName"
							placeholder="Patient Name" value="<%= indoorRegister.getpName() %>" required>
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
						<textarea class="form-control" rows="3" id="pAddress" name="pAddress" placeholder="Address" required><%= indoorRegister.getpAddress() %></textarea>
					</div>
					
					<label for="age" class="col-sm-1 control-label">Age
						</label>
					<div class="col-sm-3">
						<input type="number" class="form-control" id="age" name="age"
							placeholder="Age" max="100" min="1" value="<%= indoorRegister.getAge() %>" required>
					</div>
				</div>

				<div class="form-group">
					<label for="diagnosis" class="col-sm-2 control-label">Diagnosis</label>
					<div class="col-sm-3">
						<select name="diagnosis" id="diagnosis" class="form-control">
							<option value="-1">Select Diagnosis</option>
							<option value="Primigravida" <% if(indoorRegister.getDiagnosis().equals("Primigravida")) { %> selected <% } %>>Primigravida</option>
							<option value="G2P1L0" <% if(indoorRegister.getDiagnosis().equals("G2P1L0")) { %> selected <% } %>>G2P1L0</option>
							<option value="G2P1L1" <% if(indoorRegister.getDiagnosis().equals("G2P1L1")) { %> selected <% } %>>G2P1L1</option>
							<option value="G3P2L2" <% if(indoorRegister.getDiagnosis().equals("G3P2L2")) { %> selected <% } %>>G3P2L2</option>
							<option value="G4P3L3" <% if(indoorRegister.getDiagnosis().equals("G4P3L3")) { %> selected <% } %>>G4P3L3</option>
							<option value="Primigravida with CPD" <% if(indoorRegister.getDiagnosis().equals("Primigravida with CPD")) { %> selected <% } %>>Primigravida with CPD</option>
							<option value="Primigravida with PIH with oligohydramnios" <% if(indoorRegister.getDiagnosis().equals("Primigravida with PIH with oligohydramnios")) { %> selected <% } %>>Primigravida with PIH with oligohydramnios</option>
							<option value="Primigravida with failure to progress" <% if(indoorRegister.getDiagnosis().equals("Primigravida with failure to progress")) { %> selected <% } %>>Primigravida with failure to progress</option>
							<option value="G2P1L1 with previous LSCS with CPD" <% if(indoorRegister.getDiagnosis().equals("G2P1L1 with previous LSCS with CPD")) { %> selected <% } %>>G2P1L1 with previous LSCS with CPD</option>
							<option value="Primigravida with breech presentation" <% if(indoorRegister.getDiagnosis().equals("Primigravida with breech presentation")) { %> selected <% } %>>Primigravida with breech presentation</option>
							<option value="G2P1L1 with previous LSCS" <% if(indoorRegister.getDiagnosis().equals("G2P1L1 with previous LSCS")) { %> selected <% } %>>G2P1L1 with previous LSCS</option>
							<option value="P2L2 for tubal ligation" <% if(indoorRegister.getDiagnosis().equals("P2L2 for tubal ligation")) { %> selected <% } %>>P2L2 for tubal ligation</option>
							<option value="P3L3 for tubal ligation" <% if(indoorRegister.getDiagnosis().equals("P3L3 for tubal ligation")) { %> selected <% } %>>P3L3 for tubal ligation</option>
							<option value="Missed Abortion" <% if(indoorRegister.getDiagnosis().equals("Missed Abortion")) { %> selected <% } %>>Missed Abortion</option>
							<option value="Incomplete Abortion" <% if(indoorRegister.getDiagnosis().equals("Incomplete Abortion")) { %> selected <% } %>>Incomplete Abortion</option>
							<option value="Chronic Cervicitis" <% if(indoorRegister.getDiagnosis().equals("Chronic Cervicitis")) { %> selected <% } %>>Chronic Cervicitis</option>
							<option value="Dysfunctional uterine bleeding" <% if(indoorRegister.getDiagnosis().equals("Dysfunctional uterine bleeding")) { %> selected <% } %>>Dysfunctional uterine bleeding</option>
							<option value="Uterine prolapse with CRE" <% if(indoorRegister.getDiagnosis().equals("")) { %> selected <% } %>>Uterine prolapse with CRE</option>
							<option value="Fibroid uterus" <% if(indoorRegister.getDiagnosis().equals("Fibroid uterus")) { %> selected <% } %>>Fibroid uterus</option>
							<option value="Infertility for diagnostic laparoscopy" <% if(indoorRegister.getDiagnosis().equals("Infertility for diagnostic laparoscopy")) { %> selected <% } %>>Infertility for diagnostic laparoscopy</option>
							<option value="Ovarian cyst" <% if(indoorRegister.getDiagnosis().equals("Ovarian cyst")) { %> selected <% } %>>Ovarian cyst</option>
							<option value="other">Other</option>
						</select>
						<div id="diagnosisOther">
							<input name="OtherDiagnosis" class="form-control" type="text"
								placeholder="Other Diagnosis" />
						</div>
					</div>

					<label for="treatment" class="col-sm-2 control-label">Treatment</label>
					<div class="col-sm-3">
						<select name="treatment" id="treatment" class="form-control" disabled>
							<option value="-1">Select Treatment</option>
							<option value="Delivery" <% if(indoorRegister.getTreatment().equals("Delivery")) { %> selected <% } %>>Delivery</option>
							<option value="LSCS" <% if(indoorRegister.getTreatment().equals("LSCS")) { %> selected <% } %>>LSCS</option>
							<option value="MTP" <% if(indoorRegister.getTreatment().equals("MTP")) { %> selected <% } %>>MTP</option>
							<option value="MTP + Tubectomy" <% if(indoorRegister.getTreatment().equals("MTP + Tubectomy")) { %> selected <% } %>>MTP + Tubectomy</option>
							<option value="Tubectomy" <% if(indoorRegister.getTreatment().equals("Tubectomy")) { %> selected <% } %>>Tubectomy</option>
							<option value="Abdominal Tubectomy" <% if(indoorRegister.getTreatment().equals("Abdominal Tubectomy")) { %> selected <% } %>>Abdominal Tubectomy</option>
							<option value="Laparoscopic Tubectomy" <% if(indoorRegister.getTreatment().equals("Laparoscopic Tubectomy")) { %> selected <% } %>>Laparoscopic Tubectomy</option>
							<option value="Abdominal Hysterectomy" <% if(indoorRegister.getTreatment().equals("Abdominal Hysterectomy")) { %> selected <% } %>>Abdominal Hysterectomy</option>
							<option value="Vaginal Hysterectomy" <% if(indoorRegister.getTreatment().equals("Vaginal Hysterectomy")) { %> selected <% } %>>Vaginal Hysterectomy</option>
							<option value="Diagnostic Laparohysteroscopy" <% if(indoorRegister.getTreatment().equals("Diagnostic Laparohysteroscopy")) { %> selected <% } %>>Diagnostic Laparohysteroscopy</option>
							<option value="Laparoscopic Hysterectomy" <% if(indoorRegister.getTreatment().equals("Laparoscopic Hysterectomy")) { %> selected <% } %>>Laparoscopic Hysterectomy</option>
							<option value="D and E" <% if(indoorRegister.getTreatment().equals("D and E")) { %> selected <% } %>>D and E</option>
							<option value="Cervical Encirclage" <% if(indoorRegister.getTreatment().equals("Cervical Encirclage")) { %> selected <% } %>>Cervical Encirclage</option>
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
							placeholder="Fees" value="<%= indoorRegister.getFees() %>">
					</div>

					<label for="remarks" class="col-sm-2 control-label">Remarks</label>
					<div class="col-sm-3">
						<input type="text" class="form-control" id="remarks" name="remarks"
							placeholder="Remarks" value="<%= indoorRegister.getRemarks() %>">
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
