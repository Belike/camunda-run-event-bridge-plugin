package org.camunda.bpm.run.example;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.camunda.bpm.engine.impl.cfg.ProcessEnginePlugin;
import org.camunda.bpm.run.example.events.CamundaEventListenerUtils;

@Slf4j
public class EventBridgeProcessEnginePlugin implements ProcessEnginePlugin {

  @Getter
  @Setter
  private List<Object> excluding = new ArrayList<>();

  @Override
  public void preInit(ProcessEngineConfigurationImpl processEngineConfiguration) {
    log.info("Post Init with excluding size " + excluding.size());
    for(Object configParameter : excluding){
      log.info("Excluding Value : " + configParameter.toString());
    }
    CamundaEventListenerUtils.setExcluding(excluding);
  }

  @Override
  public void postInit(ProcessEngineConfigurationImpl processEngineConfiguration) {

  }

  @Override
  public void postProcessEngineBuild(ProcessEngine processEngine) {

  }
}
