/*
 * --------------------------------------------------------------------------------------------------------------
 * Copyright 2014, Laboratory of Internet Computing (LInC), Department of Computer Science, University of Cyprus
 * 
 * For any information relevant to Cloud Information System,
 * please contact Athanasios Foudoulis,  afoudo01{at}cs.ucy.ac.cy
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * --------------------------------------------------------------------------------------------------------------
 */
package eu.celarcloud.cloud_is.visualizationTool.servlet;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.io.Writer;
import java.net.URI;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriBuilder;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.json.JSONArray;
import org.json.JSONObject;
import org.apache.batik.dom.GenericDOMImplementation;
import org.apache.batik.svggen.SVGGraphics2D;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.CharacterData;
import org.xml.sax.InputSource;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.core.util.MultivaluedMapImpl;

// TODO: Auto-generated Javadoc
/**
 * Servlet implementation class AppInfo.
 */
public class AppInfo extends HttpServlet {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
       
    /**
	 * Instantiates a new app info.
	 *
	 * @see HttpServlet#HttpServlet()
	 */
    public AppInfo() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
	 * Do request.
	 *
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @throws ServletException
	 *             the servlet exception
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
    private void doRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	String appId = request.getParameter("appId");
    	String versId = request.getParameter("versId");
    	
    	// Build the rest client
    	ClientResponse restResponse;
    	MultivaluedMap<String, String> queryParams;
    	String isserver = (String) getServletContext().getAttribute("isserver");
    	URI uri = UriBuilder.fromUri( isserver + "/rest/").build();
		Client client = Client.create();
		WebResource service = client.resource(uri);
    	
		// Issue the request
		/*
		restResponse = service.path("application/"+appId+"/"+versId+"/deployment").accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);
    	String deployments = restResponse.getEntity(String.class);
    	System.out.println(deployments);
    	*/
    	
    	// Issue the request
		restResponse = service.path("application/"+appId+"/"+versId+"/description").accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);
    	String descStr = restResponse.getEntity(String.class);
    	JSONObject temp = new JSONObject(descStr);
    	String desc = temp.getString("versTopology");
    	System.out.println(desc);
    	
    	/*
    	 // Build report
	    JSONObject json = new JSONObject();
	    json.put("desc", new JSONObject(desc));
	    json.put("depl", new JSONArray(deployments));
	    
	    response.setContentType("application/json");
	    response.getWriter().write(json.toString());
	    */
    	
    	// Get a DOMImplementation.
        DOMImplementation domImpl = GenericDOMImplementation.getDOMImplementation();

        // Create an instance of org.w3c.dom.Document.
        String svgNS = "http://www.w3.org/2000/svg";
        Document document = domImpl.createDocument(svgNS, "svg", null);

        // Create an instance of the SVG Generator.
        SVGGraphics2D svgGenerator = new SVGGraphics2D(document);

        // Ask the test to render into the SVG Graphics2D implementation.
        try 
        {
			this.paint(svgGenerator, desc);
		} 
        catch (Exception e) 
        {
			e.printStackTrace();
		}

        // Finally, stream out SVG to the standard output using
        // UTF-8 encoding.
        boolean useCSS = true; // we want to use CSS style attributes
        OutputStream outStream = response.getOutputStream();
        //Writer out = new OutputStreamWriter(System.out, "UTF-8");
        Writer out = new OutputStreamWriter(outStream, "UTF-8");
        svgGenerator.stream(out, useCSS);
    	
        
        response.setContentType("application/xml");
	    //response.getWriter().write(svgGenerator.toString());
        //response.getWriter().write(outStream.toString());    	
	}

	/**
	 * Do get.
	 *
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @throws ServletException
	 *             the servlet exception
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doRequest(request, response);
	}

	/**
	 * Do post.
	 *
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @throws ServletException
	 *             the servlet exception
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doRequest(request, response);
	}
	
	/**
	 * Paint.
	 *
	 * @param g2d
	 *            the g2d
	 * @param xmlData
	 *            the xml data
	 * @throws Exception
	 *             the exception
	 */
	public void paint(Graphics2D g2d, String xmlData) throws Exception 
	{		
		DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
	    InputSource is = new InputSource();
	    is.setCharacterStream(new StringReader(xmlData));
	    
	    //
	    Document doc = db.parse(is);
	    NodeList nodes = doc.getElementsByTagName("module");

	    int lastX = 10, lastY = 30;
	    for (int i = 0; i < nodes.getLength(); i++) 
	    {
	      Element element = (Element) nodes.item(i);
	      String mdName = element.getAttribute("name");
	      // Get Module Position
	      int posX = lastX  + 50;
	      int posY = lastY;
	      
	      lastX = posX + 200;
	      lastY = posY;
	      
	      // Draw Module
	      g2d.setPaint(Color.black);
		  g2d.draw(new Rectangle(posX, posY, 200, 300));
	      
		  // Draw module name
		  g2d.drawString(mdName, posX, posY - 10);
		  
	      // get module Image
	      NodeList name = element.getElementsByTagName("image");
	      Element line = (Element) name.item(0);
	      String image = getCharacterDataFromElement(line);
	      
	      // Draw image name inside the module
	      g2d.setPaint(Color.black);
		  g2d.draw(new Rectangle(posX, posY + 300 - 30, 200, 25));
		  // Draw image name
		  g2d.drawString(image, posX, posY + 300 - 10);
	    }
	  }
	
	/**
	 * Gets the character data from element.
	 *
	 * @param e
	 *            the e
	 * @return the character data from element
	 */
	public static String getCharacterDataFromElement(Element e) 
	{
	    Node child = e.getFirstChild();
	    if (child instanceof CharacterData) {
	      CharacterData cd = (CharacterData) child;
	      return cd.getData();
	    }
	    return "";
	  }

}
