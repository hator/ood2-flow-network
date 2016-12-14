package simulation.elements;

import simulation.Settings;

abstract public class Element {
    float currentFlow;
    float maxFlow;

    abstract void recalculateFlow();

    float getFlow() {
        return currentFlow;
    }

    Settings getSettings() {
        return new Settings(currentFlow, maxFlow, null);
    }

    void applySettings(Settings settings) {
        this.currentFlow = settings.currentFlow;
        this.maxFlow = settings.maxFlow;
    }
}