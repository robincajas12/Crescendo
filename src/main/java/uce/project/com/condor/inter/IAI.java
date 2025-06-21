package uce.project.com.condor.inter;

import java.util.List;

public interface IAI {
    void setModel(String model);
    String getModel();

    void setTemperature(float temperature);
    float getTemperature();

    void setMaxTokens(int maxTokens);
    int getMaxTokens();

    List<String> getModels();
}