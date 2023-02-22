 <%@ page import="javax.servlet.*" %>
<%@ page import="javax.servlet.http.*" %>
<%@ page import="java.util.regex.*" %>
<%@ page import="com.docedge.core.security.*" %>
<%@ page import="com.docedge.util.*" %>
<%
  if(!SessionManager.getInstance().isValid(request.getParameter("sid")))
	  throw new Exception("Session expired");
    
  UserSession ldSession = SessionManager.getInstance().get(request.getParameter("sid"));    
    
  Long userId = ldSession.getUserId();
  Long docId = Long.parseLong(request.getParameter("docId"));
  String npr = request.getParameter("unicode");
  String userName = ldSession.getUserName();
  com.docedge.core.security.SecurityManager security = (com.docedge.core.security.SecurityManager)Context.getInstance().getBean(com.docedge.core.security.SecurityManager.class);  
  String name = request.getParameter("unicode"); 

  if(name==null){
	  if(!security.isReadEnabled(docId, userId))
		  throw new Exception("Permission Denied");
  }
  
  boolean download = security.isDownloadEnabled(docId, userId);
  String path = "convertpdf";
  if(request.getParameter("path")!=null)
  	path = request.getParameter("path");
%>
<!DOCTYPE html>
    <html>
    <head>
    <meta charset="utf-8">

    <title>docEdge Enterprise Document Management System</title>

    <link href="css/3dflip.css" rel="stylesheet" type="text/css">

    <link href="css/themify-icons.css" rel="stylesheet" type="text/css">
    <style>
    body{
    height:100%;
    width:100%;
    margin:0;
    }
    </style>
    </head>
    <body>
    <div id="3flipbookContainer"></div>

    
    <script src="js/libs/jquery.min.js" type="text/javascript"></script>

    <script src="js/3dflip.min.js" type="text/javascript"></script>

     <script>

    jQuery(document).ready(function () {

    var pdf='<%=request.getContextPath()%>/<%=path%>?<%=request.getQueryString()%>';
    var options = {height: 750, duration: 800,webgl:true,backgroundColor: "rgba(238, 238, 238, 0.9)",};
    var flipBook = $("#3flipbookContainer").flipBook(pdf, options);

    });
    </script>
    </body>
    </html>