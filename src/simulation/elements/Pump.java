package simulation.elements;

import util.Point;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class Pump extends Component {
    private Output output = new Output(this);

    Pump(float currentFlow, float maxFlow) {
        super(null, "res/pump100-100.png");
        this.currentFlow = currentFlow;
        this.maxFlow = maxFlow;
    }

    public Pump(float currentFlow, float maxFlow, Point position) {
        super(position, "res/pump100-100.png");
        this.currentFlow = currentFlow;
        this.maxFlow = maxFlow;
    }

    Output getOutput() {
        return output;
    }

    @Override
    void recalculateFlow() {
        this.getOutput().recalculateFlow(this.currentFlow);
    }

    @Override
    public List<Pipeline> getPipelines() {
        List<Pipeline> pipelines = new ArrayList<>();

        pipelines.add(output.pipeline);

        return pipelines;
    }
}
