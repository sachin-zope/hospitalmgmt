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
	<script src="js/delivery_register.js"></script>
	
	<link href="css/bootstrap.min.css" rel="stylesheet">
	<link href="css/common_styles.css" rel="stylesheet">
</head>

<body>
	<jsp:include page="nav.html"></jsp:include>

	<div class="container" ng-controller="DeliveryRegisterCtrl">
		<div class="page-header">
			<h3>Delivery Register Report</h3>
		</div>
		<div class="row" style="padding-bottom: 20px;">
			<div class="col-sm-6">
				<h4>{{currentMonth}}, {{currentYear}}</h4>
			</div>
			<div class="col-sm-6" style="text-align: right;">
			<form class="form-inline">
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
					ng-click="loadDeliveryRegister()">Submit</button>
			</form>
		</div>
		</div>
		<div class="row">
			<div>
				<table class="table table-bordered">
					<thead>
						<tr>
							<th>Serial No</th>
							<th>DOD</th>
							<th>Name &amp; Address</th>
							<th>Child Details</th>
							<th>Diagnosis &amp; Treatment</th>
							<th>Delivery Type</th>
							<th>Actions</th>
						</tr>
					</thead>
					<tbody>
						<tr
							ng-repeat="dr in allRecords | offset: currentPage*itemsPerPage | limitTo: itemsPerPage">
							<td>{{dr.deliveryRegister.serialNo}}</td>
							<td>{{dr.deliveryRegister.deliveryDate}}</td>
							<td>{{dr.pName}}<br>{{dr.pAddress}} &nbsp; &nbsp;
								&nbsp; &nbsp; &nbsp; &nbsp; {{dr.gender}}/{{dr.age}}</td>
							<td>Time: {{dr.deliveryRegister.birthTime}}<br>Weight: {{dr.deliveryRegister.birthWeight}}<br>Sex: {{dr.deliveryRegister.sexOfChild}}</td>
							<td>{{dr.diagnosis}} <br> {{dr.treatment}}</td>
							<td>{{dr.deliveryRegister.deliveryType}}</td>
							<td><a class="btn btn-primary btn-xs" href="deliveryregisterservlet?action=edit&id={{dr.delivery_register_id}}"><span
									class="glyphicon glyphicon-edit" aria-hidden="true"></span>
									Edit</a> <a class="btn btn-danger btn-xs" href="deliveryregisterservlet?action=delete&id={{dr.delivery_register_id}}"><span
									class="glyphicon glyphicon-remove" aria-hidden="true"></span>
									Delete</a> <a class="btn btn-success btn-xs" href="#"><span
									class="glyphicon glyphicon-list-alt" aria-hidden="true"></span>
									Bill</a></td>
						</tr>
						<tr ng-hide="allRecords.length">
							<td style="text-align:center; color:#ff0000;" colspan="8" >No records found!</td>
						</tr>
					</tbody>
				</table>
				<div class="row" ng-show="allRecords.length">
					<div class="col-sm-6">
						<a class="btn btn-warning" href="deliveryregisterservlet?action=download"><span
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
		</div>
	</div>
</body>
</html>