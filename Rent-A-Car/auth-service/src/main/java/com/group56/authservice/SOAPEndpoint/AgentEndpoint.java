package com.group56.authservice.SOAPEndpoint;

import com.group56.authservice.model.Agent;
import com.group56.authservice.service.AgentService;
import com.group56.soap_package.AgentXML;
import com.group56.soap_package.GetAgentsRequest;
import com.group56.soap_package.GetAgentsResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;

import java.util.ArrayList;
import java.util.List;

@Endpoint
public class AgentEndpoint {
    private AgentService agentService;

    @Autowired
    public AgentEndpoint(AgentService agentService) {
        this.agentService = agentService;
    }

    @PayloadRoot(namespace = "http://group56.com/soap-package", localPart = "getAgentsRequest")
    public GetAgentsResponse getAgentsRequest(@RequestPayload GetAgentsRequest request) {
        GetAgentsResponse response = new GetAgentsResponse();
        List<Agent> agents = agentService.getAgentsThatAreNotSharedWithAdminService(request.getUsername());
        List<AgentXML> agentsXML = convertAgentsToXML(agents);
        response.setAgentsXML(agentsXML);

        return response;
    }

    private List<AgentXML> convertAgentsToXML(List<Agent> agents) {
        List<AgentXML> agentsXML = new ArrayList<>();

        agents.forEach(data -> {
            AgentXML agent = new AgentXML();
            agent.setAgentName(data.getAgentName());
            agent.setEmail(data.getEmail());
            agent.setRegistrationNumber(data.getRegistrationNumber());
            agent.setAddress(data.getAddress());

            agentsXML.add(agent);
        });

        return agentsXML;
    }
}
