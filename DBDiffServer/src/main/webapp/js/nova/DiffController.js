var app = angular.module("diffApp");

app.controller("diffController", function ($scope, $http) {

    $scope.dataLoadingInProgress = false;
    $scope.testCaseId;
    $scope.userId;
    $scope.busyButtonText;
    $scope.promptForFileName = false;

    $scope.$watch('testCaseId', function (newValue) {
        if (newValue) {
            $scope.userId = null;
        }
    });

    $scope.$watch('userId', function (newValue) {
        if (newValue) {
            $scope.testCaseId = null;
        }
    });

    $scope.compareDiffTestCases = function () {
        $scope.busyButtonText = "Comparing with Test Case, Please Wait...";
        fetchDiff(getDiffServerBaseUrl()+"/testcases/" + $scope.testCaseId);
    };

    $scope.compareDiffLatestRecords = function () {
        $scope.busyButtonText = "Comparing latest Records, Please Wait...";
        fetchDiff(getDiffServerBaseUrl() +"/users/" + $scope.userId);
    };

    function getDiffServerBaseUrl() {
        var serverHostURL = window.location.protocol + '//' + window.location.host;
        return serverHostURL + '/DBDiff/diff/db'
    }

    function fetchDiff(url) {
        clearState();
        $http.get(url).then(function (response) {
            $scope.tables = response.data.tables;
            $scope.dataLoadingInProgress = false;
        }, function (error) {
            $scope.dataLoadingInProgress = false;
            $scope.errorMsg = " Error Occured, Check server logs !";
        });
    }

    $scope.getButtonText = function () {
        return $scope.dataLoadingInProgress ? $scope.busyButtonText : "Compare";
    };

    $scope.getLatestRecordsButtonText = function () {
        return $scope.dataLoadingInProgress ? "Compare" : "Comparing latest Records, Please Wait...";
    };

    function clearState() {
        $scope.dataLoadingInProgress = true;
        $scope.tables = null;
        $scope.errorMsg = null;
    };
});