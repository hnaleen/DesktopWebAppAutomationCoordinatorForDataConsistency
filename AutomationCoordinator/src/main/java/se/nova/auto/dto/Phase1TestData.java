package se.nova.auto.dto;

import java.util.Properties;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Phase1TestData implements TestData
{
  String userId;
  
  String userName;
  
  String testCaseId;
  
  String cosmicPatientId;
  
  String novaPatientName;
  
  String templateName;
  
  String dosage;
  
  String action;
  
  Start start;
  
  String recipe;
  
  End end;
  
  String testCaseStatus = "NOT RUN";
  
  public Phase1TestData()
  {
  }

  public String getTestCaseId()
  {
    return testCaseId;
  }

  public void setTestCaseId(String testCaseId)
  {
    this.testCaseId = testCaseId;
  }
  
  public String getCosmicPatientId()
  {
    return cosmicPatientId;
  }

  public void setCosmicPatientId(String cosmicPatientId)
  {
    this.cosmicPatientId = cosmicPatientId;
  }

  public String getNovaPatientName()
  {
    return novaPatientName;
  }

  public void setNovaPatientName(String novaPatientName)
  {
    this.novaPatientName = novaPatientName;
  }

  public String getTemplateName()
  {
    return templateName;
  }

  public void setTemplateName(String templateName)
  {
    this.templateName = templateName;
  }

  public String getDosage()
  {
    return dosage;
  }

  public void setDosage(String dosage)
  {
    this.dosage = dosage;
  }

  public String getAction()
  {
    return action;
  }

  public void setAction(String action)
  {
    this.action = action;
  }

  public Start getStart()
  {
    return start;
  }

  public void setStart(Start start)
  {
    this.start = start;
  }

  public End getEnd()
  {
    return end;
  }

  public void setEnd(End end)
  {
    this.end = end;
  }
  
  public void setTestCaseStatus(String testCaseStatus)
  {
    this.testCaseStatus = testCaseStatus;
  }
  
  public String getTestCaseStatus()
  {
    return testCaseStatus;
  }

  public int compareTo(Phase1TestData other)
  {
    return this.testCaseId.compareTo(other.getTestCaseId());
  }
  
  public Properties toProperties()
  {
    Properties properties = new Properties();
    properties.setProperty("userId", userId);
    properties.setProperty("userName", userName);
    properties.setProperty("testCaseId", testCaseId);
    properties.setProperty("patientId", cosmicPatientId);
    properties.setProperty("patientName", novaPatientName);
    properties.setProperty("templateName", templateName);
    properties.setProperty("dosage", dosage);
    properties.setProperty("action", action);
    properties.setProperty("testCaseStatus", testCaseStatus);
    properties.setProperty("recipe", recipe);
    start.toProperties(properties);
    end.toProperties(properties);
    return properties;
  }

  public int compareTo(TestData other)
  {
    return this.testCaseId.compareTo(other.getTestCaseId());
  }

  public String getUserId()
  {
    return userId;
  }

  public void setUserId(String userId)
  {
    this.userId = userId;
  }

  public String getUserName()
  {
    return userName;
  }

  public void setUserName(String userName)
  {
    this.userName = userName;
  }
  
  public void setRecipe(String recipe)
  {
    this.recipe = recipe;
  }
  
  public String getRecipe()
  {
    return recipe;
  }
}
