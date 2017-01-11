package simulation.elements;

import simulation.Settings;
import ui.ImageLibrary;
import util.Point;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Merger extends Component implements java.io.Serializable{
    private Input inputA = new Input(this);
    private Input inputB = new Input(this);
    private Output output = new Output(this);

    public Merger(Point position) {
        super(position);
    }

    Input getInputA() {
        return inputA;
    }

    Input getInputB() {
        return inputB;
    }

    @Override
    void recalculateFlow() {
        this.settings.currentFlow = inputA.getFlow() + inputB.getFlow();
        this.output.recalculateFlow(this.settings.currentFlow);
    }

    Output getOutput() {
        return output;
    }

    @Override
    public List<Pipeline> getPipelines() {
        List<Pipeline> pipelines = new ArrayList<>();

        pipelines.add(inputA.pipeline);
        pipelines.add(inputB.pipeline);
        pipelines.add(output.pipeline);

        return pipelines;
    }

    @Override
    public void render(Graphics graphics) {
        ImageLibrary.drawImage(ImageLibrary.Images.Merger, graphics, getPosition());
    }
}
