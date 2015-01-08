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
package eu.celarcloud.cloud_is.visualizationTool.servlet.pages;

import java.io.IOException;
import java.net.URI;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriBuilder;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

// TODO: Auto-generated Javadoc
/**
 * Servlet implementation class DeplInfo.
 */
public class DeplInfo extends HttpServlet {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
       
    /**
	 * Instantiates a new depl info.
	 *
	 * @see HttpServlet#HttpServlet()
	 */
    public DeplInfo() {
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
    	String appID, versID, deplID;
    	
    	appID = request.getParameter("appID") == null ? "" : request.getParameter("appID");
    	versID = request.getParameter("versID") == null ? "" : request.getParameter("versID");
    	deplID = request.getParameter("deplID") == null ? "" : request.getParameter("deplID");
    	
    	// Build the rest client
    	ClientResponse restResponse;
    	MultivaluedMap<String, String> queryParams;
    	String isserver = (String) getServletContext().getAttribute("isserver");
    	URI uri = UriBuilder.fromUri( isserver + "/rest/").build();
		Client client = Client.create();
		WebResource service = client.resource(uri);    	
    	
    	// Issue the request
		// TODO enclose to try / catch, in order to avoid error when the is-core service is not available.
		//restResponse = service.path("application/"+appID+"/"+versID+"/"+deplID).accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);
    	//String deploymentInfo = restResponse.getEntity(String.class);
    	
    	// Inject the obtained info to request object
    	// in order for jsp page to get the info
    	
    	
    	RequestDispatcher dispatcher = request.getRequestDispatcher("/hPages/deployment.jsp");
    	dispatcher.forward(request, response); 
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
		// TODO Auto-generated method stub
		doRequest(request, response);
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
		// TODO Auto-generated method stub
		doRequest(request, response);
	}

}
