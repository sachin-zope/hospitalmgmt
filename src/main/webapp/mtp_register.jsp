<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">

<!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
<title>Navjeevan Hospital</title>

<script src="js/jquery.min.js"></script>
<script src="js/bootstrap.min.js"></script>

<link href="css/bootstrap.min.css" rel="stylesheet">
<style>
.form-horizontal .radio-inline {
	padding-top: 0px;
}

#batchNoDiv {
	display: none;
	padding-top: 10px;
}
</style>

<script type="text/javascript">
	$(document).ready(function() {
	    $("#reponseAlert").fadeTo(2000, 500).slideUp(500, function(){
	    });
	    
		$('input[name=pName]').on('keyup', function() {
			var $this = $(this), value = $this.val();
			$this.val(value.toUpperCase());
		});

		$('select[name=procedure]').change(function(e) {
			if ($(this).val() == 'Medication abortion') {
				$('#batchNoDiv').show();
			} else {
				$('#batchNoDiv').hide();
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
	<div class="alert alert-success alert-dismissible" role="alert"
		id="reponseAlert">
		<button type="button" class="close" data-dismiss="alert"
			aria-label="Close">
			<span aria-hidden="true">&times;</span>
		</button>
		Record is successfully saved!
	</div>
	<%
			} else if(resp.equalsIgnoreCase("error")) {
				String err = request.getAttribute("ERROR").toString();
	%>
	<div class="alert alert-danger alert-dismissible" role="alert"
		id="reponseAlert">
		<button type="button" class="close" data-dismiss="alert"
			aria-label="Close">
			<span aria-hidden="true">&times;</span>
		</button>
		There is some error saving record. Try once again! <br>
		<strong>Error :</strong>
		<%= err %>
	</div>
	<%	
			}
		}
	%>

	<div class="container">
		<div class="page-header">
			<h3>MTP Register</h3>
		</div>
		<div class="row">
			<form class="form-horizontal" method="post"
				action="mtpregisterservlet" name="mtpForm">
				<div class="form-group">
					<label for="opdDate" class="col-sm-2 control-label">OPD
						Date</label>
					<div class="col-sm-4">
						<input type="date" class="form-control" id="opdDate"
							name="opdDate" placeholder="OPD Date" required>
					</div>
					
					<label class="col-sm-1 control-label"></label>
					<div class="col-sm-4">
						<div class="radio">
							<label class="radio-inline"> <input type="radio"
								name="married" id="married" value="Married" checked> Married
							</label> <label class="radio-inline"> <input type="radio"
								name="married" id="unmarried" value="Unmarried"> Unmarried
							</label>
						</div>
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
								name="gender" id="male" value="male"> Male
							</label> <label class="radio-inline"> <input type="radio"
								name="gender" id="female" value="female" checked> Female
							</label>
						</div>
					</div>
				</div>

				<div class="form-group">
					<label for="pAddress" class="col-sm-2 control-label">Address</label>
					<div class="col-sm-4">
						<textarea class="form-control" rows="3" id="pAddress"
							name="pAddress" placeholder="Address" required></textarea>
					</div>

					<label for="age" class="col-sm-1 control-label">Age </label>
					<div class="col-sm-3">
						<input type="number" class="form-control" id="age" name="age"
							placeholder="Age" min="1" max="100" required>
					</div>
				</div>

				<div class="form-group">
					<label for="durationOfPregnancy" class="col-sm-2 control-label">Duration
						of Pregnancy</label>
					<div class="col-sm-2">
						<input type="number" class="form-control" id="durationOfPregnancy"
							name="durationOfPregnancy" min="1" max="40">
					</div>
					<div class="col-sm-1">
						<label class="col-sm-2 control-label">Week's</label>
					</div>

					<label for="religion" class="col-sm-2 control-label">Religion</label>
					<div class="col-sm-3">
						<select name="religion" class="form-control">
							<option value="hindu">Hindu</option>
							<option value="muslim">Muslim</option>
							<option value="Shikh">Shikh</option>
						</select>
					</div>
				</div>

				<div class="form-group">
					<label for="indication" class="col-sm-2 control-label">Indication</label>
					<div class="col-sm-3">
						<select name="indication" class="form-control">
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
							<option value="Second trimester termination">Second
								trimester termination</option>
							<option value="Medication abortion">Medication abortion</option>
						</select>
						<div id="batchNoDiv">
							<input name="batchNo" class="form-control" type="text"
								placeholder="Batch No" />
						</div>
					</div>
				</div>

				<div class="form-group">
					<label for="alongWith" class="col-sm-2 control-label">Along
						With</label>
					<div class="col-sm-3">
						<select name="alongWith" class="form-control">
							<option value=""></option>
							<option value="Coper T">Coper T</option>
							<option value="Tubectomy">Tubectomy</option>
							<option value="Injectable Contraceptive">Injectable
								Contraceptive</option>
							<option value="Barrier Contraceptive">Barrier
								Contraceptive</option>
						</select>
					</div>

					<label for="mChildrens" class="col-sm-2 control-label">Total
						Childrens</label>
					<div class="col-sm-1">
						<input type="number" class="form-control" id="mChildrens"
							name="mChildrens" placeholder="M" min="0" max="10">
					</div>

					<div class="col-sm-1">
						<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
					</div>

					<div class="col-sm-1">
						<input type="number" class="form-control" id="fChildrens"
							name="fChildrens" placeholder="F" min="0" max="10">
					</div>
				</div>

				<div class="form-group">
					<label for="doneby" class="col-sm-2 control-label">Done By
						Dr.</label>
					<div class="col-sm-3">
						<input type="text" class="form-control" id="doneby" name="doneby"
							placeholder="Done by Dr.">
					</div>

					<label for="opinionby" class="col-sm-2 control-label">Opinion
						Given By</label>
					<div class="col-sm-3">
						<input type="text" class="form-control" id="opinionby"
							name="opinionby" placeholder="Opinion Given By">
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
						<input type="text" class="form-control" id="remarks"
							name="remarks" placeholder="Remarks">
					</div>
				</div>

				<div class="form-group">
					<div class="col-sm-offset-2 col-sm-10">
						<button type="submit" class="btn btn-success">Save</button>
						<button type="button" class="btn btn-default">Cancel</button>
					</div>
				</div>
			</form>
		</div>
	</div>
</body>
</html>
