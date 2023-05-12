package com.edu.scu;

import java.net.URLConnection;

public class Constants {
    public static final String documentRoot = "-document_root";
    public static final String port = "-port";
    public static final String CRLF = "\r\n";
	public static final String HTTP0 = "HTTP/1.0";
	public static final String HTTP1 = "HTTP/1.1";
	
	public static final String entityBody501 = "<html>" + 
	"<head><title>501 Service Unimplemented</title></head>" +
	"<body>Currently only GET Method is supported.</body></html>";
	public static final String statusLine501 = " 501 Service Unimplemented"+ CRLF;

	public static final String entityBody505 = "<html>" + 
	"<head><title>505 Version Unsupported</title></head>" +
	"<body>Only HTTP/1.0 and HTTP/1.1 Version are supported.</body></html>";
	public static final String statusLine505 = "HTTP/1.1 505 HTTP Version not Supported"+ CRLF;

	public static final String enetityBody404 = "<html>" + 
	"<head><title>404 Not Found</title></head>" +
	"<body>Requested File Not Found</body></html>";
	public static final String statusLine404 = " 404 File Not Found"+ CRLF;

	public static final String entityBody403 = "<html>" + 
	"<head><title>403 Forbidden</title></head>" +
	"<body>Requested File is Restricted</body></html>";
	public static final String statusLine403 = " 403 Forbidden" + CRLF;

	public static final String entityBody400 = "<html>" + 
	"<head><title>400 Bad Request</title></head>" +
	"<body>Please check if the request is correct</body></html>";
	public static final String statusLine400 = " 400 Bad Request" + CRLF;

	public static final String statusLine200 = " 200 OK"+ CRLF;
	
    public static String getContentType(String fileName){
        return URLConnection.guessContentTypeFromName(fileName);
    }
}   
