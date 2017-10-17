package se.nova.auto.dto;

import java.util.Properties;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Phase2TestData implements TestData
{
  String userId;

  String userName;

  String testCaseId;

  String cosmicPatientId;

  String novaPatientName;

  String templateName;

  String templateType;

  PGD pgd;

  Drug drug;

  String testCaseStatus = "NOT RUN";

  public Phase2TestData()
  {
  }

  public void setUserId(String userId)
  {
    this.userId = userId;
  }

  public void setUserName(String userName)
  {
    this.userName = userName;
  }

  public void setTestCaseId(String testCaseId)
  {
    this.testCaseId = testCaseId;
  }

  public void setCosmicPatientId(String cosmicPatientId)
  {
    this.cosmicPatientId = cosmicPatientId;
  }

  public void setNovaPatientName(String novaPatientName)
  {
    this.novaPatientName = novaPatientName;
  }

  public void setTemplateName(String templateName)
  {
    this.templateName = templateName;
  }

  public void setTemplateType(String templateType)
  {
    this.templateType = templateType;
  }

  public void setPgd(PGD pgd)
  {
    this.pgd = pgd;
  }

  public void setDrug(Drug drug)
  {
    this.drug = drug;
  }

  public void setTestCaseStatus(String testCaseStatus)
  {
    this.testCaseStatus = testCaseStatus;
  }

  public String getCosmicPatientId()
  {
    return cosmicPatientId;
  }

  public String getNovaPatientName()
  {
    return novaPatientName;
  }

  public String getTemplateName()
  {
    return templateName;
  }

  public String getTemplateType()
  {
    return templateType;
  }

  public PGD getPgd()
  {
    return pgd;
  }

  public Drug getDrug()
  {
    return drug;
  }

  @Override
  public String getTestCaseId()
  {
    return testCaseId;
  }

  @Override
  public String getTestCaseStatus()
  {
    return testCaseStatus;
  }

  @Override
  public String getUserId()
  {
    return userId;
  }

  @Override
  public String getUserName()
  {
    return userName;
  }

  @Override
  public Properties toProperties()
  {
    Properties properties = new Properties();
    properties.setProperty("userId", userId);
    properties.setProperty("userName", userName);
    properties.setProperty("testCaseId", testCaseId);
    properties.setProperty("patientId", cosmicPatientId);
    properties.setProperty("patientName", novaPatientName);
    properties.setProperty("templateName", templateName);
    properties.setProperty("templateType", templateType);
    properties.setProperty("testCaseStatus", testCaseStatus);
    if (pgd != null)
    {
      pgd.toProperties(properties);
    }
    if (drug != null)
    {
      drug.toProperties(properties);
    }
    return properties;
  }

  @Override
  public int compareTo(TestData o)
  {
    return this.getTestCaseId().compareTo(o.getTestCaseId());
  }
}
