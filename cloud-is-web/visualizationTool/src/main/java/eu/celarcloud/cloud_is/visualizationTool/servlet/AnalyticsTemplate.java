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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

// TODO: Auto-generated Javadoc
/**
 * Servlet implementation class AnalyticsTemplate.
 */
public class AnalyticsTemplate extends HttpServlet {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
       
    /**
	 * Instantiates a new analytics template.
	 *
	 * @see HttpServlet#HttpServlet()
	 */
    public AnalyticsTemplate() {
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
    	// Build report object
	    JSONObject json = new JSONObject();
    	
	    // Helpers
	    StringBuilder sb;
	    BufferedReader br;
	    String fileName = "";
	    
    	// Get Template name
    	String templateName = request.getParameter("template").trim();
    	if (templateName.length() < 1)
    		templateName = "appOverview";
    	    	  	
    	// Check if the specific file (report) exists
    	fileName = "/hPages/reportTemplates/" + templateName + ".html";
    	File f = new File(getServletContext().getRealPath(fileName));
        if(!f.exists()) {
        	// if not load the default
        	fileName = "/hPages/reportTemplates/" + "appComponent" + ".html";
        }
    	
        // Read the analytics report template
    	sb = new StringBuilder();
        br = new BufferedReader(new FileReader(getServletContext().getRealPath(fileName)));
        String line;
        while ( (line=br.readLine()) != null) {
          sb.append(line);
          // or
          //  sb.append(line).append(System.getProperty("line.separator"));
        }
    	br.close();
    	// Append to report
	    json.put("reportTemplate", sb.toString());
        
	    //-
	    /* The old way
	    //-
	    // TODO
	    // Temporary approach to make reportTemplate more flexible
	    // here we are defining the metrics that are going to be displayed
	    // The name of the metrics must be consistent with data-id attribute
	    // in the .html report templates
	    JSONArray ms = new JSONArray();
	    	ms.put("cpu");
	    	ms.put("ram");
	    	ms.put("disk");
	    json.put("metrics", ms);
	    */
        // Template type / configuration
	    if(!templateName.equals("appOverview")) // appOverview does not need additional configuration
	    {
	        String templateType = request.getParameter("type").trim();
	    	if (templateType.length() < 1)
	    		templateType = "default";
	    	
	    	fileName = "/hPages/reportTemplates/" + templateName + "." + templateType + ".json";
	    	
	    	sb = new StringBuilder();
	        br = new BufferedReader(new FileReader(getServletContext().getRealPath(fileName)));
	        String templine;
	        while ( (templine=br.readLine()) != null) {
	          sb.append(templine);
	        }
	    	br.close();
	    	// Parse to object
	    	JSONArray m = new JSONArray(sb.toString());    	
	    	// Append to report
		    json.put("metrics", m);
	    }
    	
        
    	
	   
	    	
	    response.setContentType("application/json");
	    response.getWriter().write(json.toString());    	
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

}
