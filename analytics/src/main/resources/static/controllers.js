'use strict';

/* Controllers */

var redditScraperControllers = angular.module('redditScraperControllers', []);

redditScraperControllers.controller('TodoListController', [
		'$scope',
		'$http',
		function($scope, $http) {

			console.log("running TodoListController");
			$scope.todos = JSON.parse(sessionStorage.getItem("scrapeData"));
			if ($scope.todos != null) {
				console.log("storage data["
						+ JSON.stringify($scope.todos.length) + "]");
			} else {
				console.log("scope.todo null")
			}
			$scope.filterText = function(a) {
				$scope.searchText = a;
			}

			function doGroupBy(post) {
				if (post != null) {
					return post.map(function(d) {
						return d.src
					}).reduce(function(acc, curr) {
						if (typeof acc[curr] == 'undefined') {
							acc[curr] = 1;
						} else {
							acc[curr] += 1;
						}

						return acc;
					}, {});
				} else {
					console.log("groupby null")
				}
			}

			if (angular.isUndefined($scope.todos) || $scope.todos === null) {
				$http.get('/posts').success(
						function(data) {
							console.log("fetched data");
							$scope.todos = data;
							sessionStorage.setItem("scrapeData", JSON
									.stringify(data));
							console.log("scrapeData set again"
									+ JSON.stringify(data.length));
							$scope.typeCounts = doGroupBy(data);

							console.log($scope.typeCounts)
						});
			}

			$scope.typeCounts = doGroupBy($scope.todos);
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

redditScraperControllers.controller('StatsController', [ '$scope', '$http',
		function($scope, $http) {

			 $scope.items = JSON.parse(sessionStorage.getItem("scrapeData"));

			$http.get('/lastRun').success(function(data) {
				console.log("lastRun fetched")
				$scope.lastRun = data;
			});

			$http.get('/topPostCategory').success(function(data) {
				console.log("topPostCategory fetched")
				$scope.topPostCategory = data;
			});

			$http.get('/topUser').success(function(data) {
				console.log("topUser fetched")
				$scope.topUser = data;
			});

			$http.get('/categoryStat').success(function(data) {
				console.log("categoryStat fetched")
				$scope.categoryStat = data;
			});

		} ]);

redditScraperControllers.controller('SortController', [ '$scope', '$http',
		function($scope, $http) {

		} ]);
