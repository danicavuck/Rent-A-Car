//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.0 
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2020.06.15 at 02:48:59 PM CEST 
//


package com.group56.soap;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.group56.soap package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {


    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.group56.soap
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetUsersRequest }
     * 
     */
    public GetUsersRequest createGetUsersRequest() {
        return new GetUsersRequest();
    }

    /**
     * Create an instance of {@link GetUsersResponse }
     * 
     */
    public GetUsersResponse createGetUsersResponse() {
        return new GetUsersResponse();
    }

    /**
     * Create an instance of {@link UserXML }
     * 
     */
    public UserXML createUserXML() {
        return new UserXML();
    }

    /**
     * Create an instance of {@link GetAdminsRequest }
     * 
     */
    public GetAdminsRequest createGetAdminsRequest() {
        return new GetAdminsRequest();
    }

    /**
     * Create an instance of {@link GetAdminsResponse }
     * 
     */
    public GetAdminsResponse createGetAdminsResponse() {
        return new GetAdminsResponse();
    }

    /**
     * Create an instance of {@link AdminXML }
     * 
     */
    public AdminXML createAdminXML() {
        return new AdminXML();
    }

    /**
     * Create an instance of {@link GetAgentsRequest }
     * 
     */
    public GetAgentsRequest createGetAgentsRequest() {
        return new GetAgentsRequest();
    }

    /**
     * Create an instance of {@link GetAgentsResponse }
     * 
     */
    public GetAgentsResponse createGetAgentsResponse() {
        return new GetAgentsResponse();
    }

    /**
     * Create an instance of {@link AgentXML }
     * 
     */
    public AgentXML createAgentXML() {
        return new AgentXML();
    }

}
