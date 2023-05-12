package com.edu.scu;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class HttpResponse {

    public static String getHeader(String statusLine, String filename, int length) {
        StringBuilder headerBuilder = new StringBuilder();
        headerBuilder
            .append(statusLine)
            .append("Date: ")
            .append(LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("EEE, dd MMM yyyy HH:mm:ss z", Locale.US).withZone(ZoneId.of("GMT"))))
            .append(Constants.CRLF);
        headerBuilder
            .append("Content-Type: ")
            .append(filename.equals("") ? "text/html" : Constants.getContentType(filename))
            .append(Constants.CRLF);
        
        if (length != 0) {
            headerBuilder.append("Content-Length: ").append(length).append(Constants.CRLF);
        }
        
        return headerBuilder.toString();
    }
}