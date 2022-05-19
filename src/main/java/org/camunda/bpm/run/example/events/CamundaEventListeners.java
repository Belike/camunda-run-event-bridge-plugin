package org.camunda.bpm.run.example.events;

import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.impl.history.event.HistoryEvent;
import org.camunda.bpm.engine.impl.history.event.HistoryEventTypes;
import org.camunda.bpm.spring.boot.starter.event.ExecutionEvent;
import org.camunda.bpm.spring.boot.starter.event.TaskEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CamundaEventListeners {

    @EventListener
    public void onExecutionEvent(ExecutionEvent executionEvent) {
        //handle immutable execution event
        log.info("Handling immutable ExecutionEvent:{}", executionEvent.getProcessDefinitionId());
        log.info("Event Name is: " + executionEvent.getEventName());
        log.info("Event Activity ID is: " +executionEvent.getCurrentActivityId());
        log.info("Event Current Activity Name is: " + executionEvent.getCurrentActivityName());
        log.info("Event ProcessDefinitionId is: " + executionEvent.getProcessDefinitionId());
        log.info("Excluding is: " +CamundaEventListenerUtils.getExcluding().toString());

    }

    //Show-Case for condition - see more on https://www.baeldung.com/spring-events
    @EventListener(condition = "@camundaEventListeners.evaluate(#historyEvent)")
    public void onHistoryEvent(HistoryEvent historyEvent) {
        // handle immutable history event for Process End
        log.info("History Event Type is: " + historyEvent.getEventType());

        historyEvent.isEventOfType(HistoryEventTypes.PROCESS_INSTANCE_END);
    }

    @EventListener
    public void onTaskEvent(TaskEvent taskEvent) {
        // handle immutable task event
        log.info("TaskEvent listener processing immutable task event. \n TaskDefinitionKey: {} TaskId: {} \n >>> Event Name: {}  \n",
                taskEvent.getTaskDefinitionKey(), taskEvent.getId(), taskEvent.getEventName());
    }

    @Order(1)
    @EventListener
    public void firstTaskEventListener(DelegateTask taskDelegate) {
        // handle mutable task event
        log.info("1st TaskEvent listener processing mutable event. \n TaskDefinitionKey: {} TaskId: {} \n >>> Event Name: {}  \n",
                taskDelegate.getTaskDefinitionKey(), taskDelegate.getId(), taskDelegate.getEventName());
    }

    @Order(2)
    @EventListener
    public void secondTaskEventListener(DelegateTask taskDelegate) {
        // handle mutable task event
        log.info("2nd TaskEvent listener processing mutable event. \n TaskDefinitionKey: {} TaskId: {} \n >>> Event Name: {}  \n",
                taskDelegate.getTaskDefinitionKey(), taskDelegate.getId(), taskDelegate.getEventName());
    }

    public boolean evaluate(HistoryEvent historyEvent){
        return historyEvent.isEventOfType(HistoryEventTypes.PROCESS_INSTANCE_END);
    }
}
