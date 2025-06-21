package uce.project.com.robin.ai;

import com.google.genai.Client;
import uce.project.com.condor.inter.IAI;

import java.util.List;

public class GoogleAIBase implements IAI {
    private Client client;
    private String model;
    private float temperature;
    private int maxTokens;

    public GoogleAIBase(String apiKey)
    {
        this.client = Client.builder().apiKey(apiKey).build();
        this.model = "gemma-3-27b-it";
        this.temperature = 1;
        this.maxTokens = 100;

    }
    @Override
    public void setModel(String model) {
        this.model = model;
    }

    @Override
    public String getModel() {
        return this.model;
    }

    @Override
    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }

    @Override
    public float getTemperature() {
        return this.temperature;
    }

    @Override
    public void setMaxTokens(int maxTokens) {
        this.maxTokens = maxTokens;
    }

    @Override
    public int getMaxTokens() {
        return this.maxTokens;
    }

    @Override
    public List<String> getModels() {
        return List.of();
    }
    public Client getClient() {
        return client;
    }
}
