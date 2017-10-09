package se.nova;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import se.nova.dto.Diff;
import se.nova.jdbc.ScriptRunner;
import se.nova.spring.AppContext;

@Path("/db")
public class DBDiffServer
{
  @Path("/users/{userId}")
  @Produces(MediaType.APPLICATION_JSON)
  @GET
  public Diff compareLatestRecordsOfUser(@PathParam("userId") String userId)
  {
    return getScriptRunner().compareLatestRecordsOfUser(userId);
  }

  @Path("/testcases/{testCaseId}")
  @Produces(MediaType.APPLICATION_JSON)
  @GET
  public Diff compareWithExistingTestCase(@PathParam("testCaseId") String testCaseId)
  {
    throw new UnsupportedOperationException();
  }

  private ScriptRunner getScriptRunner()
  {
    return (ScriptRunner) AppContext.getBean("scriptRunner");
  }
}
