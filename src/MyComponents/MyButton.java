package MyComponents;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class MyButton extends JButton {
    private boolean selected = false;
    private String text;
    private String pathName = "src\\MyComponents\\img\\";
    private BufferedImage icon = null;
    private Color color = new Color(74, 78, 86);
    private Color SelectedColor = new Color(180, 180, 180);

    private Color textColor = new Color(120, 122, 128);

    public MyButton(String text) {
        super(text);
        this.text = text;
        setBackground(new Color(47, 49, 54));
        setBorder(null);
        setContentAreaFilled(false);

        init();
    }


    private void init() {
        if (text.equals("Control")) {
            pathName += "control_icon.png";
        } else if (text.equals("Reserve")) {
            pathName += "reserve_icon.png";
        } else if (text.equals("About")) {
            pathName += "about_icon.png";
        } else if (text.equals("Exit")) {
            pathName += "exit_icon.png";
            color = new Color(74, 20, 20);
            SelectedColor = new Color(120, 40, 40);
        }

        try {
            icon = ImageIO.read(new File(pathName));

        } catch (IOException e) {
            e.printStackTrace();
        }

        repaint();
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                if (!text.equals("Exit")) {
                    color = new Color(114, 137, 218);
                    textColor = new Color(255, 255, 255);
                } else {
                    textColor = new Color(255, 255, 255);
                    color = new Color(100, 20, 20);
                }


            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                if (!text.equals("Exit")) {
                    color = new Color(74, 78, 86);
                    textColor = new Color(120, 122, 128);
                } else {
                    color = new Color(74, 20, 20);
                }
            }


        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        if (selected) {
            g2.setColor(SelectedColor);
        } else {
            g2.setColor(color);
        }


        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);

        if (icon != null) {
            int o_width = icon.getWidth();
            int o_height = icon.getHeight();
            double scale;
            int newWidth, newHeight;

            if (o_width >= o_height) {
                newWidth = getWidth() / 4;
                scale = (double) o_width / newWidth;
                newHeight = o_height / (int) scale;
            } else {
                newHeight = getHeight() - 6;
                scale = (double) o_height / newHeight;
                newWidth = getHeight() / (int) scale;
            }
            g2.drawImage(icon, 5, 5, newWidth, newHeight, null);
        }
        if (selected) {
            g2.setColor(Color.WHITE);
        } else {
            g2.setColor(textColor);
        }
        g2.setFont(new Font("Dubai Light", Font.BOLD, 15));
        g2.drawString(text, getWidth() / 3 + 2, getHeight() / 2 + 5);
    }

    @Override
    public void setSelected(boolean selected) {
        this.selected = selected;
        repaint();
    }
}
