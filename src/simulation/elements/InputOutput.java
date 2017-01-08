package simulation.elements;

abstract class InputOutput implements java.io.Serializable{
    float currentFlow;
    Pipeline pipeline = null;
    Component component;

    InputOutput(Component component) {
        this.component = component;
    }

    protected abstract void recalculateFlow(float previousElementFlow);

    void attachPipeline(Pipeline pipeline) {
        // TODO check if there is already a pipeline?
        this.pipeline = pipeline;
    }

    float getFlow() {
        return currentFlow;
    }

    void detachPipeline() {
        pipeline = null;
    }
}
