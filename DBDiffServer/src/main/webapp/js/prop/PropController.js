var app = angular.module("propApp", []);

app.controller("propController", function ($scope) {
    $scope.fileInfo;

    $scope.readFile = function () {
        $scope.fileInfo = "HNA";
    };

});