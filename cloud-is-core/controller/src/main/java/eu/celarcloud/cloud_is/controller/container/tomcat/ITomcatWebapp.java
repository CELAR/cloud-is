package eu.celarcloud.cloud_is.controller.container.tomcat;

import eu.celarcloud.cloud_is.controller.container.IWebapp;

public interface ITomcatWebapp extends IWebapp{
	public void create(TomcatEmbeddedRunner embeddedRunner, String contextPath);
}
