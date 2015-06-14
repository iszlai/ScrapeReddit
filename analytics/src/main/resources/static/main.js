'use strict';

/* App Module */

var redditScraper = angular.module('redditScraper', [
  'ngRoute',
  'redditScraperControllers',
  'highcharts-ng'
]);

redditScraper.config(['$routeProvider',
  function($routeProvider) {
    $routeProvider.
      when('/main', {
        templateUrl: 'main.html',
        controller: 'TodoListController'
      }).
      when('/chart/:id', {
        templateUrl: 'chart.html',
        controller: 'VotesController'
      }).
      when('/stats', {
        templateUrl: 'stats.html',
        controller: 'StatsController'
      }).
      when('/stats', {
        templateUrl: 'pain.html',
        controller: 'TeachController'
      }).
      otherwise({
        redirectTo: '/main'
      });
  }]);

