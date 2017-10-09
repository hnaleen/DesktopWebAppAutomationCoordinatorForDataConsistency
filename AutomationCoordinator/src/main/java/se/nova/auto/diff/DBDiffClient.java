package se.nova.auto.diff;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;

import se.nova.auto.util.ArgumentProcessor;

public class DBDiffClient
{
  private ArgumentProcessor argumentProcessor;

  ClientConfig clientConfig = new DefaultClientConfig();

  Client client;

  public DBDiffClient(ArgumentProcessor argumentProcessor)
  {
    this.argumentProcessor = argumentProcessor;
    clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
    client = Client.create(clientConfig);
  }

  public String calculateDiffForUser(String userId)
  {
    WebResource diffResource = client.resource(argumentProcessor.getDiffUrl() + userId);
    String diffInJson = diffResource.type("application/json").get(String.class);
    System.out.println(diffInJson);
    return diffInJson;
  }

}
