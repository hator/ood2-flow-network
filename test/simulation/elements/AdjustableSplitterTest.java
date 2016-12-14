package simulation.elements;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AdjustableSplitterTest {
    @Test
    public void unconnectedAdjustableSplitterHas0Flow() {
        // Given
        AdjustableSplitter splitter = new AdjustableSplitter();

        // When
        splitter.recalculateFlow();

        // Then
        assertEquals(0.f, splitter.getFlow(), 0.001f);
    }

    @Test
    public void adjustableSplitterHasFlowOfItsInput() {
        // Given
        float inputFlow = 5.f;
        AdjustableSplitter splitter = new AdjustableSplitter();
        Pipeline inputPipe = new MockFixedFlowPipeline(inputFlow, splitter.getInput());

        // When
        inputPipe.recalculateFlow();

        // Then
        assertEquals(inputFlow, splitter.getFlow(), 0.001f);
    }

    @Test
    public void adjustableSplitterSetsOutputsAsFractionOfInput() throws Exception {
        // Given
        float inputFlow = 6.f;
        float ratio = 0.2f;
        float outputAFlow = inputFlow*ratio;
        float outputBFlow = inputFlow*(1-ratio);
        AdjustableSplitter splitter = new AdjustableSplitter(ratio);
        Pipeline inputPipe = new MockFixedFlowPipeline(inputFlow, splitter.getInput());

        // When
        inputPipe.recalculateFlow();

        // Then
        assertEquals(outputAFlow, splitter.getOutputA().getFlow(), 0.001f);
        assertEquals(outputBFlow, splitter.getOutputB().getFlow(), 0.001f);
    }
}