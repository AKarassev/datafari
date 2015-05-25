package com.francelabs.datafari.servlets.admin;

import java.io.IOException;
import java.io.StringWriter;



import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.common.params.SolrParams;
import org.apache.solr.common.util.ContentStream;
import org.apache.solr.core.SolrCore;
import org.apache.solr.request.SolrQueryRequest;
import org.apache.solr.response.JSONResponseWriter;
import org.apache.solr.response.SolrQueryResponse;
import org.apache.solr.schema.IndexSchema;
import org.apache.solr.search.SolrIndexSearcher;


import com.francelabs.datafari.solrj.SolrServers;
import com.francelabs.datafari.solrj.SolrServers.Core;

/**
 * Servlet implementation class Admin
 */
@WebServlet("/admin/Admin")
public class Admin extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private SolrInputDocument doc;
	private int passage=0;
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Admin() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String redirectUrl = "/admin/capsulesConfig.jsp";
		HttpSolrServer server = (HttpSolrServer) SolrServers
				.getSolrServer(Core.CAPSULE);
		if(passage==0){
			Object key = request.getParameter("keyword"), title = request.getParameter("title"), value = request.getParameter("value"),
					dateB = request.getParameter("dateB")+"T00:00:00Z", dateE = request.getParameter("dateE")+"T23:59:59Z";
			final SolrQuery query = new SolrQuery();
			QueryResponse queryResponse = null;
			doc = new SolrInputDocument();
			SolrQueryRequest req = new SolrQueryRequest() {
				@Override
				public SolrParams getParams() {
					return query;
				}

				@Override
				public void setParams(SolrParams params) {
				}

				@Override
				public Iterable<ContentStream> getContentStreams() {
					return null;
				}

				@Override
				public SolrParams getOriginalParams() {
					return null;
				}

				@Override
				public Map<Object, Object> getContext() {
					return null;
				}

				@Override
				public void close() {
				}

				@Override
				public long getStartTime() {
					return 0;
				}

				@Override
				public SolrIndexSearcher getSearcher() {
					return null;
				}

				@Override
				public SolrCore getCore() {
					return null;
				}

				@Override
				public IndexSchema getSchema() {
					return null;
				}

				@Override
				public String getParamString() {
					return null;
				}

				@Override
				public void updateSchemaToLatest() {

				}

			};
			query.setParam("q", "keyword = \""+key+"\"");
			query.setRequestHandler("/select");
			try {
				queryResponse = server.query(query);
				SolrQueryResponse res = new SolrQueryResponse();
				res.setAllValues(queryResponse.getResponse());
				JSONResponseWriter jsonWriter = new JSONResponseWriter();
				StringWriter s = new StringWriter();
				jsonWriter.write(s, req, res);
				doc.addField("keyword", key);
				doc.addField("title", title);
				doc.addField("content", value);
				if(!dateB.toString().equals("T00:00:00Z"))
					doc.addField("dateBeginning", dateB);
				if(!dateE.toString().equals("T23:59:59Z"))
					doc.addField("dateEnd", dateE);
				if ((s.toString().charAt(10+s.toString().indexOf("numFound")))!='0'){
					request.setAttribute("errorCapsule", "Already a capsule, override it?");
					passage++;
				}else{
					server.add(doc);
					server.commit();
				}
				RequestDispatcher rd = request.getRequestDispatcher(redirectUrl);
				rd.forward(request, response);
			} catch (SolrServerException e) {
				
				e.printStackTrace();
			}
		}else{
			passage=0;
			if(request.getParameter("answer").toString().equals("yes")){
				try {
					server.deleteById(doc.get("keyword").toString());
					server.add(doc);
					server.commit();
				} catch (SolrServerException e) {
			
					e.printStackTrace();
				}
			}
			request.setAttribute("errorCapsule", null);
			RequestDispatcher rd = request.getRequestDispatcher(redirectUrl);
			rd.forward(request, response);
		}

	}

}
