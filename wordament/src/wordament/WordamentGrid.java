package wordament;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JPanel;
import sun.audio.AudioDataStream;

public class WordamentGrid extends JPanel {

    int num, cellWidth, padding = 5, highlight;
    char[][] grid;
    int[][] oldpath;
    Color background;
    Font font;

    public WordamentGrid() {
        num = 0;
        grid = new char[4][4];
        background = new Color(0xbb8fce);
        highlight = 0x000000;
    }

    @Override
    public void paintComponent(Graphics g) {

        cellWidth = max(min(getWidth(), getHeight()), 4) / 4;
        g.drawRect(0, 0, getWidth(), getHeight());
        font = new Font("Courier", Font.BOLD, cellWidth - (2 * padding));

        g.setColor(background);
        g.fillRect(0, 0, getWidth(), getHeight());
        g.setColor(Color.WHITE);

        for (int i = 0; i < 3; i++) {
            int y = (i + 1) * cellWidth;
            int x = (i + 1) * cellWidth;
            g.drawLine(0, y, getWidth(), y);
            g.drawLine(x, 0, x, getHeight());
        }
    }

    public boolean input(char c) {
        c = ("" + c).toUpperCase().charAt(0);
        if (c <= 'Z' && c >= 'A' && num < 16) {
            addChar(c);
            if (num == 16) {
                return true;
            }
        }
        return false;
    }

    void unhighLightCell(int y, int x) {
        Graphics g = this.getGraphics();
        g.setColor(background);
        drawCell(g, y, x);
    }

    void hightLightCell(int y, int x, int color) {
        Graphics g = this.getGraphics();
        g.setColor(new Color(color));
        drawCell(g, y, x);
    }

    void drawCell(Graphics g, int y, int x) {
        String s = grid[y][x] + "";
        x *= cellWidth;
        y *= cellWidth;
        g.fillRect(x + 1, y + 1, cellWidth - 1, cellWidth - 1);
        g.setColor(Color.WHITE);
        g.setFont(font);
        g.drawString(s, x + 2 * padding, y + cellWidth - 2 * padding);
    }

    void addChar(char c) {
        grid[num / 4][num % 4] = c;
        unhighLightCell(num / 4, num % 4);
        num++;
    }

    synchronized void showPath(int[][] path) {
        if (oldpath != null) {
            for (int i = 0; i < oldpath.length; i++) {
                unhighLightCell(oldpath[i][0], oldpath[i][1]);
            }
        }
        int color = highlight;
        if (path != null) {
            for (int i = 0; i < path.length; i++) {
                hightLightCell(path[i][0], path[i][1], color += 0x252525);
                playSound();
                try {
                    Thread.sleep(250);
                } catch (InterruptedException ex) {
                }
            }
        }
        oldpath = path;
    }

    int min(int a, int b) {
        return a > b ? b : a;
    }

    int max(int a, int b) {
        return a > b ? a : b;
    }

    boolean isFull() {
        return num == 16;
    }

    void playSound() {
        /*try {
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(new FileInputStream("music.wav"));
            Clip clip = AudioSystem.getClip();
            clip.open(audioIn);
            clip.start();
        } catch (LineUnavailableException | IOException | UnsupportedAudioFileException ex) {
            Logger.getLogger(WordamentGrid.class.getName()).log(Level.SEVERE, null, ex);
        }*/
    }

}
