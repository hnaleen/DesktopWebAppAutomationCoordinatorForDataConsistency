/**
 * Created by hasantha.alahakoon on 9/14/2017.
 */

angular.module('diffApp').service('TestSuiteResultService', function () {
    var testResults = [];

    this.clearExistingTestResults = function () {
        testResults = [];
    };

    this.addTestCaseResult = function (diffByTestCaseId) {
        testResults.push(diffByTestCaseId);
    };

    this.getTestCaseIds = function () {
        var ids = [];
        for (var i=0; i<testResults.length; i++) {
            ids.push(testResults[i].testCaseId);
        }
        return ids;
    };

    this.getDiffByTestCaseId = function (testCaseId) {
        for (var i=0; i<testResults.length; i++) {
            if (testCaseId === testResults[i].testCaseId) {
                return testResults[i].diff.tables;
            }
        }
    };

});