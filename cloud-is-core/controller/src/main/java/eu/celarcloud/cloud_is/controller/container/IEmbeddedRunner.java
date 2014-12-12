package eu.celarcloud.cloud_is.controller.container;

import java.io.File;

import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;

public interface IEmbeddedRunner {
	public void start()throws LifecycleException;
	public void stop() throws LifecycleException;
	public boolean isRunning();
	public Tomcat getServer();	
	public File getServerBase();
}
