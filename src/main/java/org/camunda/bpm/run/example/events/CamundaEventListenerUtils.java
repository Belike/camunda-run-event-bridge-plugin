package org.camunda.bpm.run.example.events;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public final class CamundaEventListenerUtils {

    @Getter
    @Setter
    private static List<Object> excluding = new ArrayList<>();
}
