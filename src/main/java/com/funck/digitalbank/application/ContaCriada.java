package com.funck.digitalbank.application;

import com.funck.digitalbank.application.events.ContaCriadaEvent;

public interface ContaCriada {

    void procederContaCriada(ContaCriadaEvent contaCriadaEvent);

}
