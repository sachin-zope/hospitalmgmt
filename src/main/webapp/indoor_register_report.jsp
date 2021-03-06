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
	<script src="js/indoor_register.js"></script>

	<link href="css/bootstrap.min.css" rel="stylesheet">
	<link href="css/common_styles.css" rel="stylesheet">
	
	<script>
		function verifyDelete() {
			var ans = confirm("Do you really want to delete?");
			if(ans == true) {
				$('#deleteForm').submit();
			}
			
			return;
		}
	</script>
</head>

<body>
	<jsp:include page="nav.html"></jsp:include>
	<div class="container" ng-controller="IndoorRegisterCtrl">
		<div class="page-header">
			<h3>Indoor Register Report</h3>
		</div>
		<div class="row" style="padding-bottom: 20px;">
			<div class="col-sm-6">
				<h4>{{currentMonth}}, {{currentYear}}</h4>
			</div>
			<div class="col-sm-6" style="text-align: right;">
				<form class="form-inline">
					<input type="hidden" value="get_by_month">
					<div class="form-group">
						<select ng-model="month" name="month"
							ng-options="choice as choice for (idx, choice) in months.split(',')"
							class="form-control">
							<option value="">Select Month</option>
						</select>
					</div>
					<div class="form-group">
						<select ng-model="year" name="year"
							ng-options="choice as choice for (idx, choice) in years.split(',')"
							class="form-control">
							<option value="">Select Year</option>
						</select>
					</div>
					<button type="button" class="btn btn-primary"
						ng-click="loadIndoorRegister()">Submit</button>
				</form>
			</div>
		</div>
		<div class="row">
			<div>
				<table class="table table-bordered">
					<thead>
						<tr>
							<th>IPD No</th>
							<th>Dates</th>
							<th>Name &amp; Address</th>
							<th>Diagnosis</th>
							<th>Treatment</th>
							<th>Fees</th>
							<th>Actions</th>
						</tr>
					</thead>
					<tbody>
						<tr
							ng-repeat="ir in indoorRegister | offset: currentPage*itemsPerPage | limitTo: itemsPerPage">
							<td>{{ir.ipdNo}}</td>
							<td>DOA: {{ir.admitDate}}<br>DOD: {{ir.dischargeDate}}</td>

							<td ng-switch on="ir.treatment">
								<span ng-switch-when="MTP">
									{{ir.mtpRegister.mtpSerialNo}}
								</span>
								<span ng-switch-when="MTP + Tubectomy">
									{{ir.mtpRegister.mtpSerialNo}}
								</span>
								<span ng-switch-when="MTP + Abdominal Tubectomy">
									{{ir.mtpRegister.mtpSerialNo}}
								</span>
								<span ng-switch-when="MTP + Laparoscopic Tubectomy">
									{{ir.mtpRegister.mtpSerialNo}}
								</span>
								<span ng-switch-default>
									{{ir.pName}}<br>{{ir.pAddress}} &nbsp; &nbsp;
									&nbsp; &nbsp; &nbsp; &nbsp; {{ir.gender}}/{{ir.age}}
								</span>
							</td>

							<td>{{ir.diagnosis}}</td>
							<td>{{ir.treatment}}</td>
							<td>{{ir.fees}}</td>
							<td><a class="btn btn-primary btn-xs" href="indoorregisterservlet?action=edit&id={{ir.id}}">Edit</a>
							 <a class="btn btn-success btn-xs" href="indoorregisterservlet?action=bill&id={{ir.id}}">Bill</a>
									<form method="post" action="indoorregisterservlet" id="deleteForm">
										<input type="hidden" name="id" value="{{ir.id}}">
										<input type="hidden" name="action" value="delete">
										<input type="hidden" name="src" value="indoor_register">
										<button type="button" class="btn btn-danger btn-xs" onclick="verifyDelete()">Delete</button>
									</form>
									</td>
						</tr>
						<tr ng-hide="indoorRegister.length">
							<td style="text-align:center; color:#ff0000;" colspan="7" >No records found!</td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
		<div class="row" ng-show="indoorRegister.length">
			<div class="col-sm-6">
				<a class="btn btn-warning" href="indoorregisterservlet?action=download"><span
									class="glyphicon glyphicon-download" aria-hidden="true"></span> Download</a>
			</div>
			<div class="col-sm-6" style="text-align:right;">
				<ul class="pagination">
				<li ng-class="prevPageDisabled()"><a href="#"
					aria-label="Previous" ng-click="prevPage()"> <span
						aria-hidden="true">&laquo;</span>
				</a></li>
				<li ng-repeat="n in range()" ng-class="{active: n == currentPage}"
					ng-click="setPage(n)"><a href="#">{{n+1}}</a></li>
				<li ng-class="nextPageDisabled()"><a href="#"
					aria-label="Next" ng-click="nextPage()"> <span
						aria-hidden="true">&raquo;</span>
				</a></li>
			</ul>
			</div>
		</div>
	</div>
</body>
</html>