package ui;

import simulation.Result;
import simulation.Settings;
import simulation.SimulationFacade;
import simulation.Tool;
import util.Point;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.*;
import java.util.List;
import java.util.function.Consumer;

class Diagram extends JPanel {
    private Tool currentTool;
    private Settings currentSettingsReference;
    private SimulationFacade simulation;
    private Consumer<Settings> changeSettingsReferenceCallback;
    //stores all the points on the pipeline that we're currently working with
    private List<Point> pipelinePointList = new ArrayList<>();
    private boolean isResultNotSuccessful;
    private String resultMessage = "OK";


    Diagram(SimulationFacade simulation) {
        this.simulation = simulation;
        Dimension dimension = new Dimension();
        dimension.setSize(800, 600);
        setPreferredSize(dimension);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                mouseClickCallback(e.getX(), e.getY());
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Dimension dim = getSize();

        //Draw background color
        g.setColor(Color.WHITE);
        g.fillRect(0,0,dim.width,dim.height);
        setStatusLabel(g);

        simulation.render(g);
    }

    void setStatusLabel(Graphics g)
    {
        g.setColor(Color.GREEN);
        g.setFont(new Font("Arial", Font.BOLD, 15));
        if(isResultNotSuccessful)
        {
            g.setColor(Color.RED);
        }

        g.drawString("Status: " + resultMessage, 10, 10);
        g.setColor(Color.GREEN);

        System.out.println();
    }

    void setChangeSettingsReferenceCallback(Consumer<Settings> changeSettingsReferenceCallback) {
        this.changeSettingsReferenceCallback = changeSettingsReferenceCallback;
    }

    private void mouseClickCallback(int x, int y) {
        assert currentTool != null;

        Point point = new Point(x, y);
        isResultNotSuccessful = false;
        switch (currentTool) {
            case Select:
                Settings settings = simulation.select(point);
                if (settings != null) {
                    changeSettingsReferenceCallback.accept(settings);
                } else {
                }

                break;
            case Remove:
                if(simulation.remove(point) != Result.Success){
                    //On failure
                }
                break;
            case AddPipeline: {
                //TODO
                pipelinePointList.add(pipelinePointList.size()-1, point);
                simulation.applyTool(pipelinePointList, currentSettingsReference);
                break;
            }
            default:
                applyAddTool(point, currentTool);

                break;
        }
        this.repaint();
    }

    void selectTool(Tool tool, Settings settings) {
        currentTool = tool;
        currentSettingsReference = settings;
        if(pipelinePointList.size()>2)
            pipelinePointList.clear();

    }

    public void setResultMessage(Result result) {
        if(result == Result.Success){
            isResultNotSuccessful = false;
            resultMessage = "OK";
        }
        else{
            isResultNotSuccessful = true;
            if (result == Result.InvalidSettings){
                resultMessage = "Invalid settings!";
            }
            else if (result == Result.ComponentsOverlapping) {
                resultMessage = "Component overlapping!";
            }
            else if (result == Result.Failure){
                resultMessage = "Failure!";
            }
        }
        this.repaint();
    }

    private void applyAddTool(Point point, Tool tool) {
        Result result = simulation.applyTool(point, tool, currentSettingsReference);
        setResultMessage(result);
    }
}
