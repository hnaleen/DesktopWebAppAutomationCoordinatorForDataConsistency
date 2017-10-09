var diffApp = angular.module('diffApp', ['ngRoute', 'ngFileSaver', 'ui.bootstrap']);

diffApp.config(['$routeProvider',
    function ($routeProvider) {
        $routeProvider.when('/newDiff', {
            templateUrl: 'index.html',
            controller: 'diffController'
        }).when('/existingSuite', {
            templateUrl: 'TestSuiteResults.html',
            controller: 'testSuiteController'
        }).otherwise({
            redirectTo: 'Main.html',
            controller: 'MainCtrl'
        });
    }]);

diffApp.controller('MainCtrl', function ($scope) {
});

