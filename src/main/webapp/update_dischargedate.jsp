<!DOCTYPE html>
<html lang="en" ng-app="myApp">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
<title>Navjeevan Hospital</title>

<script src="js/jquery.min.js"></script>
<script src="js/bootstrap.min.js"></script>
<script src="js/angular.min.js"></script>

<link href="css/bootstrap.min.css" rel="stylesheet">
<link href="css/common_styles.css" rel="stylesheet">

<script type="text/javascript">

function validate() {
	if(!$("#dischargeDate").val()) {
		alert("Please select discharge date");
		return false;
	}
	
	$('#indoorForm').submit();
};

function goBack() {
	window.history.back();
}
</script>
</head>

<body>
	<div class="container">
		<div class="page-header">
			<h3>Update Discharge Date</h3>
		</div>

		<div class="row">
			<form class="form-horizontal" method="post"
				action="indoorregisterservlet" id="indoorForm">
				<input type="hidden" name="action" value="update_discharge_date">
				<input type="hidden" name="id" value = "<%= request.getParameter("id") %>">
				<div class="form-group">
					<label for="dischargeDate" class="col-sm-2 control-label">Discharge
						Date</label>
					<div class="col-sm-3">
						<input type="date" class="form-control" id="dischargeDate"
							name="dischargeDate" placeholder="Discharge Date">
					</div>
				</div>
				
				<div class="form-group">
					<div class="col-sm-offset-2 col-sm-10">
							<button type="button" class="btn btn-success" onclick="validate()">Save</button>
							<button type="button" class="btn btn-default" onclick="goBack()">Cancel</button>
					</div>
				</div>
			</form>
		</div>
	</div>

</body>
</html>