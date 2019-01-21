package MyComponents;

import javax.swing.*;
import java.awt.*;

public class MyLabel extends JLabel {
    String text;
    String fontName = "Dubai Light";
    int fontStyle = Font.PLAIN;
    int fontSize = 15;

    String type;
    Font font;

    public MyLabel(String type) {
        this("", type);
    }

    public MyLabel(String text, String type) {
        this(text, JLabel.LEFT, type);
    }

    public MyLabel(String text, int horizontalAlignment, String type) {
        super(text, horizontalAlignment);
        this.type = type;
        this.text = text;
        font = new Font(fontName, fontStyle, fontSize);
        init();
    }


    public void setFontSize(int fontSize) {
        this.fontSize = fontSize;
        update();
    }

    public void setFontName(String fontName){
        this.fontName = fontName;
        update();
    }

    void update() {
        font = new Font(fontName, fontStyle, fontSize);
        setFont(font);
        repaint();
    }



    public void init() {
        setForeground(Color.WHITE);
        String _type = type.toLowerCase();
        switch (_type) {
            case "title":
                font = new Font("Dubai Light", Font.PLAIN, 25);
                break;
            case "title_small":
                font = new Font("Dubai Light", Font.BOLD, 15);
                break;
            case "description":
                font = new Font("Dubai Light", Font.PLAIN, 15);
                break;
        }


        setFont(font);


    }
}
