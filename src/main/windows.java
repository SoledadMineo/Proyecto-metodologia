/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import Graphics.Assets;
import States.GameState;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import javax.swing.JFrame;

public class windows extends JFrame implements Runnable {

    public static final int WIDTH = 800, HEIGHT = 600;
    private Canvas canvas;
    private Thread thread;
    private boolean running = false;

    private BufferStrategy bs;
    private Graphics g;

    private final int fps = 60;
    private double targetTime = 1000000000 / fps;
    private double delta = 0;
    private int averageFps = fps;
    
    private GameState gameState;

    public windows() {
        setTitle("JavaApplication32");
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
        canvas = new Canvas();
        canvas.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        canvas.setMaximumSize(new Dimension(WIDTH, HEIGHT));
        canvas.setMinimumSize(new Dimension(WIDTH, HEIGHT));
        canvas.setFocusable(true);
        add(canvas);

    }

    public static void main(String[] args) {
        new windows().start();
    }

    private void update() {
        gameState.update();

    }

    private void draw() {
        bs = canvas.getBufferStrategy();
        if (bs == null) {
            canvas.createBufferStrategy(3);
            return;
        }
        g = bs.getDrawGraphics();
        //----------------------------------
        
        g.setColor(Color.BLACK);
        g.fillRect(0,0,WIDTH,HEIGHT);
        gameState.draw(g);
        
        
        //----------------------------------
        g.dispose();
        bs.show();
    }

    private void init() {

        Assets.init();
        gameState = new GameState();

    }

    @Override
    public void run() {

        long now = 0;
        long lastTime = System.nanoTime();
        int frames = 0;
        long time = 0;

        init();

        while (running) {
            now = System.nanoTime();
            delta += (now - lastTime) / targetTime;
            time += (now - lastTime);

            lastTime = now;
            if (delta >= 1) {
                update();
                draw();
                delta--;
                frames++;
            }
            if (time >= 1000000000) {
                averageFps = frames;
                System.out.println(averageFps);
                frames = 0;
                time = 0;
            }

        }
        stop();
    }

    private void start() {
        thread = new Thread(this);
        thread.start();
        running = true;
    }

    private void stop() {
        try {
            thread.join();
            running = false;
        } catch (InterruptedException e) {

        }
    }
}
