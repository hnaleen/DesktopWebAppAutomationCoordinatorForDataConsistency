var express = require('express')
var bodyParser = require('body-parser')

var app = express();

app.set('port', (process.env.PORT || 5000))
app.use(express.static(__dirname + '/public'))
app.use(bodyParser.json())

app.post('/runtest', function (req, response) {

    var testFrameworkBootstrapScript = req.body.testFrameworkBootsrapScript;
    var testSuiteScriptName = req.body.testScriptName;
    var jsonString = JSON.stringify(req.body);
    var testData = "--params.data=" + jsonString;
    var pro = require('child_process').spawn('protractor.cmd', [testFrameworkBootstrapScript, '--suite', testSuiteScriptName, testData]);

    pro.stdout.on('data', function (data) {
        console.log('stdout: ' + data);
    });

    pro.stderr.on('data', function (data) {
        console.log('stderr: ' + data);
    });

    pro.on('close', function (code) {
        console.log('child process exited with code ' + code);
        response.status(code == 0 ? 200 : 400);
        response.send(code == 0 ? "PASSED" : "FAILED");
    });
});

const server = app.listen(app.get('port'), function () {
    console.log("Node app is running at localhost:" + app.get('port'))
})

server.timeout  = 360000;

