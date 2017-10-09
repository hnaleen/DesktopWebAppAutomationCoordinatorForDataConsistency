# AutomationCoordinator
Ensures the Consistency of Data creted by a Desktop and a Web Client by running a Common set of TestCases with a common set of TestData (defined in XML format).

1. Coordinates and executes a Automated Test Suite in a Web and a Desktop Swing client.
2. For sucessful Test Case executions (in both clients), Contacts the 'DBDiffServer' module and fetches the differences between DB changes resulted from the two TestCase executions (Web client and Desktop Swing clients).
3. Outputs the difference between data created from the two clients in to JSON files.

4. Repeats the steps 2 and 3 for each of the sucessfuly Test Case execution in the Test Suite.
5. Retried the execution if a Test Case fails in any of the clients. Logged down the test case as a failure after two attempts and skipped the DB data integrity calculation (step 2 and 3) and moved to the next Test Case.