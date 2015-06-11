'use strict';

/* Controllers */

var redditScraperControllers = angular.module('redditScraperControllers', []);

redditScraperControllers.controller('TodoListController', [ '$scope', '$http',
		function($scope, $http) {

			console.log("running controller");
			$scope.todos = sessionStorage.getItem("scrapeData");
			console.log("storage data[" + JSON.stringify($scope.todos) + "]");
			$scope.filterText = function(a) {
				$scope.searchText = a;
			}

			if (angular.isUndefined($scope.todos) || $scope.todos === null) {
				$http.get('/posts').success(function(data) {
					console.log("fetched data");
					$scope.todos = data;
					sessionStorage.setItem("scrapeData", JSON.stringify(data));
					console.log("scrapeData set again");
					$scope.typeCounts = data.map(function(d) {
						return d.src
					}).reduce(function(acc, curr) {
						if (typeof acc[curr] == 'undefined') {
							acc[curr] = 1;
						} else {
							acc[curr] += 1;
						}

						return acc;
					}, {});

					console.log($scope.typeCounts)
				});
			}
		} ]);

redditScraperControllers.controller('VotesController', [ '$scope', '$http',
		'$timeout', '$routeParams',
		function($scope, $http, $timeout, $routeParams) {
			$scope.chartConfig = {
				options : {
					chart : {
						zoomType : 'x'
					},
					rangeSelector : {
						enabled : true
					},
					navigator : {
						enabled : true
					}
				},
				series : [],
				title : {
					text : 'Vote history'
				},
				useHighStocks : true
			};

			$http.get('/votes?id=' + $routeParams.id).success(function(data) {
				$scope.data = data;
				$scope.up = $scope.data.map(function(item) {
					var b = [];
					var s = new Date(item.sqltime);
					b.push(s.getTime());
					b.push(item.up);
					return b
				});
				$scope.down = $scope.data.map(function(item) {
					var b = [];
					var s = new Date(item.sqltime);
					b.push(s.getTime());
					b.push(item.down);
					return b
				});
				$scope.score = $scope.data.map(function(item) {
					var b = [];
					var s = new Date(item.sqltime);
					b.push(s.getTime());
					b.push(item.score);
					return b
				});
				$scope.chartConfig.series.push({
					id : 1,
					name : "UP",
					data : $scope.up
				}, {
					id : 2,
					name : "DOWN",
					data : $scope.down
				}, {
					id : 3,
					name : "SCORE",
					data : $scope.score
				}

				);
			});

		} ]);