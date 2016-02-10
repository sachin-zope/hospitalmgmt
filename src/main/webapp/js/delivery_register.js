var app = angular.module('myApp', []);

app.filter('offset', function() {
	return function(input, start) {
		start = parseInt(start, 10);
		return input.slice(start);
	};
});

app.controller('DeliveryRegisterCtrl', [
		'$scope',
		'$http', '$filter',
		function($scope, $http, $filter) {

			$scope.months = "Jan,Feb,May,Jun,Jul,Aug,Sep,Oct,Nov,Dec,Mar,Apr";
			$scope.years = "2015,2016,2017,2018,2019,2020";

			$scope.itemsPerPage = 10;
			$scope.currentPage = 0;
			$scope.currentMonth = "";
			$scope.currentYear = "";
			$scope.allRecords = [];
			
			$http.get('deliveryregisterservlet', {
				params : {
					month : $filter('date')(new Date(), 'MMM'),
					year : $filter('date')(new Date(), 'yyyy'),
					action : 'get_by_month'
				}
			}).success(function(data) {
				$scope.allRecords = data;
				$scope.currentMonth = $filter('date')(new Date(), 'MMM');
				$scope.currentYear = $filter('date')(new Date(), 'yyyy');
			});

			$scope.loadDeliveryRegister = function() {
				console.log("request to get dr")
				$http.get('deliveryregisterservlet', {
					params : {
						month : $scope.month,
						year : $scope.year,
						action : 'get_by_month'
					}
				}).success(function(data) {
					$scope.allRecords = data;
					$scope.currentPage = 0;
					$scope.currentMonth = $scope.month;
					$scope.currentYear = $scope.year;
					range();
				});
			};
			
			$scope.deleteDeliveryRegister = function(deliveryRegisterId) {
				$http.get('deliveryregisterservlet', {
					params : {
						id : deliveryRegisterId,
						action : 'delete'
					}
				}).success(function(data) {
					loadDeliveryRegister();
				});
			}

			$scope.range = function() {
				var rangeSize = 5;
				var ret = [];
				var start;
				
				if(rangeSize > $scope.pageCount()) {
			        rangeSize = $scope.pageCount() + 1;
			    }

				start = $scope.currentPage;
				if (start > $scope.pageCount() - rangeSize) {
					start = $scope.pageCount() - rangeSize + 1;
				}

				for (var i = start; i < start + rangeSize; i++) {
					ret.push(i);
				}
				return ret;
			};

			$scope.prevPage = function() {
				if ($scope.currentPage > 0) {
					$scope.currentPage--;
				}
			};

			$scope.prevPageDisabled = function() {
				return $scope.currentPage === 0 ? "disabled" : "";
			};

			$scope.pageCount = function() {
				return Math.ceil($scope.allRecords.length
						/ $scope.itemsPerPage) - 1;
			};

			$scope.nextPage = function() {
				if ($scope.currentPage < $scope.pageCount()) {
					$scope.currentPage++;
				}
			};

			$scope.nextPageDisabled = function() {
				return $scope.currentPage === $scope.pageCount() ? "disabled"
						: "";
			};
			
			$scope.setPage = function(n) {
			    $scope.currentPage = n;
			};
		} ]);