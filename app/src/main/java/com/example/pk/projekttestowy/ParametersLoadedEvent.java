package com.example.pk.projekttestowy;

/**
 * Created by PK on 21.07.2017.
 */

class ParametersLoadedEvent {

    private Parameters[] contents=null;

    ParametersLoadedEvent(Parameters[] contents) {
        this.contents=contents;
    }
    Parameters[] getParameters() {
        return (contents);
    }

}
