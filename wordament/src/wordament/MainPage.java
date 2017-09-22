package wordament;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class MainPage extends javax.swing.JFrame implements KeyListener {
    
    ObservableQueue queue;
    Wordament wordament;
    
    public MainPage() {
        initComponents();
        queue = new ObservableQueue(this::showNext);
        wordament = new Wordament("../words", (int[][] path) -> {
            queue.enqueue(path);
        });
        this.addKeyListener(this);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        wordgrid = new wordament.WordamentGrid();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        wordgrid.setPreferredSize(new java.awt.Dimension(300, 300));

        javax.swing.GroupLayout wordgridLayout = new javax.swing.GroupLayout(wordgrid);
        wordgrid.setLayout(wordgridLayout);
        wordgridLayout.setHorizontalGroup(
            wordgridLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
        wordgridLayout.setVerticalGroup(
            wordgridLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(wordgrid, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(wordgrid, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public static void main(String args[]) {
        try {
            javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(() -> {
            new MainPage().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private wordament.WordamentGrid wordgrid;
    // End of variables declaration//GEN-END:variables

    private void showNext() {
        try { wordgrid.showPath(queue.dequeue()); }
        catch (Exception ex) { }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        char ip = e.getKeyChar();
        boolean shownext = false;
        
        if(ip == '\n') {
            if(wordgrid.isFull()) {
                
                synchronized(queue) {
                    if(queue.isEmpty()) {
                        queue.unlock();
                    } else {
                        shownext = true;
                    }
                }
                if(shownext)
                    showNext();
            }
        } else {
            if(wordgrid.isFull())
                return;
            if(wordgrid.input(ip))
                wordament.solve(wordgrid.grid);
        }
    }

    @Override
    public void keyPressed(KeyEvent e) { }
    @Override
    public void keyReleased(KeyEvent e) { }
}
