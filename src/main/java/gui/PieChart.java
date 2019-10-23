package gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import javax.swing.JComponent;

public class PieChart extends JComponent {
    private float percentage = 0f;

    /**
     * Creates a new PieChart with provided completed percentage
     *
     * @param percentage percentage of tasks completed
     */
    PieChart(float percentage) {
        this.percentage = percentage;
    }

    /**
     * Paints the pie chart
     *
     * @param g Graphics object of the Component
     */
    public void paint(Graphics g) {
        drawPie((Graphics2D) g, getBounds());
    }

    /**
     * Draws the pie chart with the given percentages with a 3 pixel border
     *
     * @param g    Graphics object of the Component
     * @param area Bounds of the space to draw
     */
    void drawPie(Graphics2D g, Rectangle area) {
        g.rotate(Math.toRadians(270));
        g.translate(-getBounds().width, 0);

        g.setColor(Color.black);
        g.fillArc(area.x, area.y, area.width, area.height, 0, 360);
        g.setColor(Color.lightGray);
        int angleIncomplete = (int) Math.ceil((100f - percentage) * 3.60f);
        g.fillArc(area.x + 3, area.y + 3, area.width - 6, area.height - 6, 0, angleIncomplete);
        g.setColor(Color.gray);
        g.fillArc(area.x + 3, area.y + 3, area.width - 6, area.height - 6, angleIncomplete, 360 - angleIncomplete);
    }

    /**
     * Updates percentage of the pie chart
     *
     * @param percentage of tasks completed
     */
    public void setPercentage(float percentage) {
        this.percentage = percentage;
        this.paint(getGraphics());
    }
}