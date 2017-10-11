package se.nova.auto.test.runner;

import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.Response.StatusType;
import javax.xml.bind.annotation.XmlRootElement;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;

import se.nova.auto.dto.TestData;
import se.nova.auto.dto.TestResult;
import se.nova.auto.util.ArgumentProcessor;

public class NovaTestRunner implements TestRunner
{
  private ArgumentProcessor argumentProcessor;

  ClientConfig clientConfig = new DefaultClientConfig();

  Client client;

  public NovaTestRunner(ArgumentProcessor argumentProcessor)
  {
    this.argumentProcessor = argumentProcessor;
    clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
    client = Client.create(clientConfig);
  }

  public TestResult runTest(TestData testData, String testScriptName)
  {
    WebResource webResourcePost = client.resource(argumentProcessor.getNovaTestRunnerUrl());
    ClientResponse response = webResourcePost.type("application/json").post(ClientResponse.class,
        new NovaTestCaseInfo(argumentProcessor.getNovaUrl(), testScriptName, testData));
    StatusType testStatus = response.getStatusInfo();
    return testStatus.getFamily() == Status.Family.SUCCESSFUL ? TestResult.SUCESS : TestResult.FAILURE;
  }

  public String getName()
  {
    return "Nova";
  }

  @XmlRootElement
  private class NovaTestCaseInfo
  {
    String novaUrl;
    
    String testScriptName;

    TestData testData;

    public NovaTestCaseInfo()
    {
    }

    public NovaTestCaseInfo(String novaUrl, String testScriptName, TestData testData)
    {
      this.novaUrl = novaUrl;
      this.testScriptName = testScriptName;
      this.testData = testData;
    }

    public String getNovaUrl()
    {
      return novaUrl;
    }
    
    public String getTestScriptName()
    {
      return testScriptName;
    }

    public TestData getTestData()
    {
      return testData;
    }

  }
}
