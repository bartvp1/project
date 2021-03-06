package CarGraph;

import MyComponents.Model;
import MyComponents.View;

import java.awt.*;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Iterator;

/**
 * GraphView
 * Dit is de panel wat de grafiek laat zien
 * Deze panel laat alleen de buitenkant van de grafiek zien en haalt de variabelen uit het model(GraphModel)
 *  De grafiek laat de volgende waardes zien: aantal reguliere, pashouders en totaal aantal auto's
 */
public class GraphView extends View {
    private GraphModel model;

    //    De dagen van de week om onderin de grafiek te zetten
    private String[] dagen = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};

    void setModel(GraphModel model) {
        this.model = model;
    }

    // alleen de achtergrond kleur wordt hier aangepast.
    public void init() {
        setBackground(new Color(47, 49, 54));
    }

    /**
     * @param g graphics waarop wordt getekent
     *          Tekent de nummers links verticaal van boven naar beneden (540 - 0)
     */
    private void drawValueNumbers(Graphics2D g) {
        for (int i = 0; i <= 10; i++) {
            g.setColor(Color.WHITE);
            g.drawString(Integer.toString((10 - i) * 60), 5, (i * 25) - 10);
        }
    }

    /**
     * @param g graphics waarop wordt getekent
     *          Tekent de verticale en horizontale lijnen van de grafiek en de namen van de dagen.
     */
    private void drawGrid(Graphics2D g) {
        //Vertical Lines
        int i = 0;
        int beginX = model.getStartingX();
        double dayWidth = (double) ((getWidth() - beginX) / 7);
        int gridSize = 25;
        int beginY = 10;
        int rows = 10;
        for (int x = beginX; x < (getWidth() - beginX); x += dayWidth) {
            if (x == beginX) {
                g.setStroke(new BasicStroke(3));
                g.setColor(new Color(0, 0, 0, 255));

            } else {
                g.setStroke(new BasicStroke(2));
                g.setColor(new Color(0, 0, 0, 30));
            }

            g.drawLine(x, beginY, x, getHeight() - gridSize - 10);
            g.setColor(Color.WHITE);
            g.drawString(dagen[i], x + (int) dayWidth / 2 - 25, getHeight() - 10);
            i++;
        }

        //Horizontal Lines
        for (int y = 0; y < rows; y++) {
            if (y == (rows - 1)) {
                g.setStroke(new BasicStroke(3));
                g.setColor(new Color(0, 0, 0, 255));
            } else {
                g.setStroke(new BasicStroke(2));
                g.setColor(new Color(0, 0, 0, 30));
            }
            g.drawLine(beginX, beginY + (y * gridSize), getWidth(), beginY + (y * gridSize));
        }
    }

    /**
     * @param g graphics waarop wordt getekent
     *          De vulling, de lijnen en daarna de zwarte lijn vooraan wordt getekend
     *
     */
    private void drawLines(Graphics2D g) {
        // De vulling wordt getekend
        // De waardes komen uit model
        g.setColor(model.getColor("Total", true));
        g.fill(model.getPath("Total"));

        g.setColor(model.getColor("Normal", true));
        g.fill(model.getPath("Normal"));

        g.setColor(model.getColor("Pass", true));
        g.fill(model.getPath("Pass"));

        // tijdelijke arraylists waar de lijnen in worden gekopieerd
        // Er wordt een kopie gemaakt omdat er vanuit een andere thread de waardes kunnen worden aangepast
        ArrayList<Line2D> totalLines = new ArrayList<>(model.getLines("Total"));
        ArrayList<Line2D> normalLines = new ArrayList<>(model.getLines("Normal"));
        ArrayList<Line2D> passLines = new ArrayList<>(model.getLines("Pass"));

        // Iterators
        Iterator<Line2D> totalIterator = totalLines.iterator();
        Iterator<Line2D> normalIterator = normalLines.iterator();
        Iterator<Line2D> passIterator = passLines.iterator();

        // Teken de lijnen
        // Kleuren staan in model
        g.setStroke(new BasicStroke(2));
        try {
            g.setColor(model.getColor("Total", false));
            totalIterator.forEachRemaining(g::draw);

            g.setColor(model.getColor("Normal", false));
            normalIterator.forEachRemaining(g::draw);

            g.setColor(model.getColor("Pass", false));
            passIterator.forEachRemaining(g::draw);
        } catch (ConcurrentModificationException e) {
            e.printStackTrace();
        }


        // Teken de lijn vooraan, dit ziet er beter uit dan de uiteindes van de vulling
        g.setColor(Color.BLACK);
        double bottomY = (getHeight() - 25 - 10);
        if (normalLines.size() > 0) {
            double x = normalLines.get(normalLines.size() - 1).getX2();
            Line2D frontLine = new Line2D.Double(x, 10, x, bottomY);
            g.draw(frontLine);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        drawValueNumbers(g2);
        drawLines(g2);
        drawGrid(g2);

        g2.setColor(new Color(0, 0, 0, 255));
        g2.setStroke(new BasicStroke(1));
        g2.drawLine(0, 0, getWidth() - 1, 0);
    }


    @Override
    protected void update(Model model) {
        repaint();
    }
}
