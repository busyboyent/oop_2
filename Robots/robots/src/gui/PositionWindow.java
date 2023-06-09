package gui;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

public class PositionWindow extends JInternalFrame implements Observer
{
    private GameModel gameModel;
    private JLabel xLabel;
    private JLabel yLabel;
    private JLabel angleLabel;

    public PositionWindow(GameModel model)
    {
        super("Поле координат", true, true, true, true);
        gameModel=model;
        gameModel.addObserver(this);
        xLabel = new JLabel();
        xLabel.setLocation(50,10);
        xLabel.setSize(100,20);
        xLabel.setText("xLabel");

        yLabel = new JLabel();
        yLabel.setLocation(50,40);
        yLabel.setSize(100,20);
        yLabel.setText("yLabel");

        angleLabel = new JLabel();
        angleLabel.setLocation(50,70);
        angleLabel.setSize(100,20);
        angleLabel.setText("angleLabel");

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(xLabel);
        panel.add(yLabel);
        panel.add(angleLabel);
        getContentPane().add(panel);
        pack();
    }

    @Override
    public void update(Observable o, Object arg)
    {
        updateCoordinates();
    }

    private void updateCoordinates()
    {
        xLabel.setText("X: "+gameModel.getRobotPositionX());
        yLabel.setText("Y: "+gameModel.getRobotPositionY());
        angleLabel.setText("Angle: "+gameModel.getRobotDirection());
    }
}
