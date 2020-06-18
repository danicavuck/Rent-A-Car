package com.group56.adminservice.service;

import com.group56.adminservice.client.SoapClient;
import com.group56.adminservice.model.Agent;
import com.group56.adminservice.repository.AgentRepository;
import com.group56.soap.AgentXML;
import com.group56.soap.GetAgentsRequest;
import com.group56.soap.GetAgentsResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AgentService {
    private AgentRepository agentRepository;
    private SoapClient soapClient;

    @Autowired
    public AgentService(AgentRepository agentRepository, SoapClient soapClient) {
        this.agentRepository = agentRepository;
        this.soapClient = soapClient;
    }

    public List<AgentXML> getAgentsXMLFromSOAPRequest() {
        GetAgentsRequest request = new GetAgentsRequest();
        request.setUsername("");
        GetAgentsResponse response = soapClient.getAgents(request);
        return response.getAgentsXML();
    }

    public void saveAgentsToDatabase(List<Agent> agents) {
        agents.forEach(agent -> {
            agentRepository.save(agent);
        });
    }
}
