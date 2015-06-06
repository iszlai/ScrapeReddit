'use strict';

/* Controllers */

var redditScraperControllers = angular.module('redditScraperControllers', []);

redditScraperControllers.controller('TodoListController', [ '$scope', '$http',
		function($scope, $http) {

			$http.get('/posts').success(function(data) {
				$scope.todos = data;
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

		} ]);