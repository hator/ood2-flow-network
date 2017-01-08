package simulation.elements;

import simulation.Settings;
import util.Point;

import java.util.List;

public class Pipeline extends Element implements java.io.Serializable{
    static final int CLICK_RADIUS = 15;
    private List<Point> points;
    private Input input;
    private Output output;

    public List<Point> getPoints()
    {
        return points;
    }
    public Pipeline(Output output, Input input, float maxFlow) {
        assert input != null;
        assert output != null;

        this.output = output;
        this.input = input;
        this.maxFlow = maxFlow;

        input.attachPipeline(this);
        output.attachPipeline(this);
    }

    public Pipeline(Output output, Input input, float maxFlow, List<Point> points) {
        this(output, input, maxFlow);
        this.points = points;
    }
    @Override
    public Settings getSettings() {
        return new Settings(currentFlow, maxFlow, null);
    }

    // TODO builder or addPoint method?

    @Override
    void recalculateFlow() {
        this.currentFlow = output.getFlow();
        input.recalculateFlow(this.currentFlow);
    }

    @Override
    public boolean inBoundingArea(Point point) {
        assert point != null;

        for (int i = 0; i < points.size() - 1; i++) {
            Point segmentA = points.get(i);
            Point segmentB = points.get(i + 1);

            if (point.inBoundingSegment(segmentA, segmentB, CLICK_RADIUS)) {
                return true;
            }
        }
        return false;
    }

    public void detach() {
        input.detachPipeline();

        if (!input.isComponentBeingDeleted()) {
            input.recalculateFlow(0);
        }

        output.detachPipeline();
    }

    public void attach() {
        output.component.recalculateFlow();
    }
}
