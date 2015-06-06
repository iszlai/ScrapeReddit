'use strict';

/* App Module */

var redditScraper = angular.module('redditScraper', [
  'ngRoute',
  'redditScraperControllers'
]);

redditScraper.config(['$routeProvider',
  function($routeProvider) {
    $routeProvider.
      when('/main', {
        templateUrl: 'main.html',
        controller: 'TodoListController'
      }).
      when('/votes/:phoneId', {
        templateUrl: 'votes.html',
        controller: 'VotesController'
      }).
      otherwise({
        redirectTo: '/main'
      });
  }]);

