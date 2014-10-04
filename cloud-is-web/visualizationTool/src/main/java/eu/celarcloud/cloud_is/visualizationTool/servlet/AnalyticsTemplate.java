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
import java.io.FileReader;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
    	// Get Template name
    	String templateName = request.getParameter("template");
    	if (templateName.length() < 1)
    		templateName = "appOverview";
    	
    	// Read the analytics report template
    	StringBuilder sb = new StringBuilder();
        BufferedReader br = new BufferedReader(new FileReader(getServletContext().getRealPath("/hPages/reportTemplates/" + templateName + ".html")));
        String line;
        while ( (line=br.readLine()) != null) {
          sb.append(line);
          // or
          //  sb.append(line).append(System.getProperty("line.separator"));
        }
    	br.close();
    	// Build report
	    JSONObject json = new JSONObject();
	    json.put("reportTemplate", sb.toString());
	    
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
