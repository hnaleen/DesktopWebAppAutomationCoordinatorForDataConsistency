/**
 * Created by hasantha.alahakoon on 9/12/2017.
 */

angular.module('diffApp').directive('testSuiteResultSelector', function (TestSuiteResultService) {
    return {
        restrict: 'E',
        templateUrl: 'TestSuiteSelector.html',
        scope: {
            testResults: "&"
        },
        link: function (scope, element) {
            var folderSelector = element[0].children[0];

            folderSelector.addEventListener("change", function (e) {
                TestSuiteResultService.clearExistingTestResults();
                // scope.testResults();

                var filesList = e.target.files;
                var files = [].slice.call(filesList);
                files.sort(function (file1, file2) {
                    return file1.name > file2.name;
                });
                
                for (var i = 0; i < files.length; i++) {
                    (function (file) {
                        if (file.name.endsWith('json')) {
                            var reader = new FileReader();
                            reader.onload = function (evnt) {
                                var diff = JSON.parse(reader.result);
                                var diffByTestCaseId = {
                                    "testCaseId": file.name.slice(0, -5),
                                    "diff": diff
                                };
                                // TestSuiteResultService.addTestCaseResult(diffByTestCaseId);
                                // scope.testResults();
                                scope.testResults({"diffByTestCaseId": diffByTestCaseId});
                            }
                            reader.readAsText(file, "UTF-8");
                        }
                    })(files[i]);
                }
            });
        }
    }
});
