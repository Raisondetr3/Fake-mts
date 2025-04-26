package ru.itmo.node_a_core.connectors;

import java.io.*;
import java.util.Properties;
import java.util.Collection;
import java.util.Iterator;
import java.net.HttpURLConnection;
import java.net.URL;

import jakarta.resource.cci.Connection;
import jakarta.resource.cci.ConnectionFactory;
import javax.naming.InitialContext;

public class Client {
    private Properties props = null;

    private String hostname = null;

    private int portnum = 0;

    // this must be the decoded context path corresponding to the web module
    private String contextPath = "/conn_resourcedefs_web";

    private String crdServletPath = contextPath + "/CRDTestServlet";

    private String aodServletPath = contextPath + "/AODTestServlet";

    private String username = "";

    private String password = "";

    private String appContextHostname;

    private String RARGlobalScopedJndiName = "java:global/env/EJBTestServlet_Global_ConnectorResource";

    private String RARModuleScopedJndiName = "java:module/env/EJBTestServlet_Module_ConnectorResource";

    private String RARCompScopedJndiName = "java:comp/env/EJBTestServlet_Comp_ConnectorResource";

    private String RARAppScopedJndiName = "java:app/env/EJBTestServlet_App_ConnectorResource";

    private String AODGlobalScopedJndiName = "java:global/env/EJBAdminObjectForGlobalScope";

    private String AODModuleScopedJndiName = "java:module/env/EJBAdminObjectForModuleScope";

    private String AODCompScopedJndiName = "java:comp/env/EJBAdminObjectForCompScope";

    private String AODAppScopedJndiName = "java:app/env/EJBAdminObjectForAppScope";

    private String servletAppContext = null;

    public static void main(String args[]) {
        Client client = new Client();
        String url = "https://api.sampleapis.com/coffee/hot";
        client.invokeServletAndGetResponse(url);
    }
    
//    public void setup(String[] args, Properties p) throws Fault {
//        props = p;
//
//        try {
//            hostname = p.getProperty("webServerHost");
//            portnum = Integer.parseInt(p.getProperty("webServerPort"));
//            username = p.getProperty("user");
//            password = p.getProperty("password");
//
//            appContextHostname = p.getProperty("logical.hostname.servlet");
//
//            TestUtil.logMsg("setup(): appContextHostname = " + appContextHostname);
//            TestUtil.logMsg("setup(): servletAppContext = " + servletAppContext);
//
//        } catch (Exception e) {
//            logErr("Error: got exception: ", e);
//        }
//    }
//
//    public void cleanup() throws Fault {
//    }

    private String invokeServletAndGetResponse(String url) {

//        String url = ctsurl.getURLString("http", hostname, portnum, sContext);
        StringBuilder retVal = null;

        try {
            URL newURL = new URL(url);

            // Encode authData
            // hint: make sure username and password are valid for your
            // (J2EE) security realm otherwise you recieve http 401 error.
//            String authData = username + ":" + password;
//            BASE64Encoder encoder = new BASE64Encoder();

//            String encodedAuthData = encoder.encode(authData.getBytes());
//            TestUtil.logMsg("encoded authData : " + encodedAuthData);

            // open URLConnection
            HttpURLConnection conn = (HttpURLConnection) newURL.openConnection();

            // set request property
            conn.setDoOutput(true);
            conn.setDoInput(true);
//            conn.setRequestProperty("Authorization",
//                    "Basic " + encodedAuthData.trim());
            conn.setRequestMethod("GET"); // POST or GET etc
            conn.connect();

            retVal = new StringBuilder(conn.getResponseMessage());

            InputStream content = (InputStream) conn.getInputStream();
            BufferedReader in = new BufferedReader(new InputStreamReader(content));
            try {
                String line;
                while ((line = in.readLine()) != null) {
                    System.out.print(line);
                    retVal.append(line);
                }
            } finally {
                in.close();
            }
        } catch (Exception e) {

        }

        assert retVal != null;
        return retVal.toString();
    } // invokeServletAndGetResponse()
    
}