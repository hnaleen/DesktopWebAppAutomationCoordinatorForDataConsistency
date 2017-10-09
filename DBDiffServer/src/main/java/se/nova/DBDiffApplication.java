package se.nova;

import java.util.Collections;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@ApplicationPath("/diff")
public class DBDiffApplication extends Application
{
  @Override
  public Set<Class<?>> getClasses()
  {
    return Collections.emptySet();
  }
}
