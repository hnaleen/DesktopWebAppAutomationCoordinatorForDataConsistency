angular.module("diffApp").controller("testSuiteController", function ($scope, TestSuiteResultService) {

    $scope.currentTestCaseId;
    $scope.diff;

    $scope.setCurrentTestCase = function (id) {
        $scope.currentTestCaseId = id;
        $scope.diff = TestSuiteResultService.getDiffByTestCaseId(id);
    };

    $scope.addTestResultsFromSuite = function (diffByTestCaseId) {
        TestSuiteResultService.addTestCaseResult(diffByTestCaseId);
        $scope.$apply();
    };

    $scope.getTestCaseIds = function () {
        return TestSuiteResultService.getTestCaseIds();
    };
});
