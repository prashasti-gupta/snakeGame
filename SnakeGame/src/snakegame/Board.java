package snakegame;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Board extends JPanel implements ActionListener{
    private Image apple;
    private Image dot;
    private Image head;
    
    private final int allDots=90000;
    private final int dotSize=10;
    private final int randomPos=29;
    
    private int appleX;
    private int appleY;
    
    private final int x[]=new int[allDots];
    private final int y[]=new int[allDots];
    
    private boolean leftDirection=false;
    private boolean rightDirection=true;
    private boolean upDirection=false;
    private boolean downDirection=false;
    
    private boolean inGame=true;
    
    private int dots;
    private Timer timer;
    Board(){
        addKeyListener(new TAdapter());
        setBackground(Color.BLACK);
        setPreferredSize(new Dimension(300,300));
        setFocusable(true);
        loadImages();
        initGame();
    }
    public void loadImages(){
        ImageIcon i1=new ImageIcon(ClassLoader.getSystemResource("snakegame/icons/apple.png"));
        apple=i1.getImage();
        ImageIcon i2=new ImageIcon(ClassLoader.getSystemResource("snakegame/icons/dot.png"));
        dot=i2.getImage();
        ImageIcon i3=new ImageIcon(ClassLoader.getSystemResource("snakegame/icons/head.png"));
        head=i3.getImage();
    }
    
    public void initGame(){
        dots=3;
        for(int i=0;i<dots;i++) {
            y[i]=50;
            x[i]=50-i*dotSize;
        }
        locateApple();
        
        timer=new Timer(140,this);
        timer.start();
    }
    public void locateApple() {
        int r=(int)(Math.random() * randomPos);
        appleX=r*dotSize;
        r=(int)(Math.random() * randomPos);
        appleY=r*dotSize;
    }
    
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }
    public void draw(Graphics g){
        if(inGame){
            g.drawImage(apple, appleX, appleY, this);
        for(int i=0;i<dots;i++) {
            if(i==0) {
                g.drawImage(head, x[i], y[i], this);
            } else{
                g.drawImage(dot, x[i], y[i], this);
            }
        }
        Toolkit.getDefaultToolkit().sync();
        } else{
            gameOver(g);
        }
    }
    public void gameOver(Graphics g) {
        String msg = "Game Over!";
        Font font = new Font("SAN_SERIF", Font.BOLD, 18);
        FontMetrics metrices = getFontMetrics(font);
        
        g.setColor(Color.WHITE);
        g.setFont(font);
        g.drawString(msg, (300 - metrices.stringWidth(msg)) / 2, 300/2);
    }
    public void move(){
        for(int i=dots;i>0;i--){
            x[i]=x[i-1];
            y[i]=y[i-1];
        }
        if (leftDirection) {
            x[0] = x[0] - dotSize;
        }
        if (rightDirection) {
            x[0] = x[0] + dotSize;
        }
        if (upDirection) {
            y[0] = y[0] - dotSize;
        }
        if (downDirection) {
            y[0] = y[0] + dotSize;
        }
    }
    public void checkApple() {
        if ((x[0] == appleX) && (y[0] == appleY)) {
            dots++;
            locateApple();
        }
    }
    
    public void checkCollision(){
        for(int i = dots; i > 0; i--) {
            if (( i > 4) && (x[0] == x[i]) && (y[0] == y[i])) {
                inGame = false;
            }
        }
        
        if (y[0] >= 300) {
            inGame = false;
        }
        if (x[0] >= 300) {
            inGame = false;
        }
        if (y[0] < 0) {
            inGame = false;
        }
        if (x[0] < 0) {
            inGame = false;
        }
        
        if (!inGame) {
            timer.stop();
        }
    }
    public void actionPerformed(ActionEvent ae) {
        if(inGame){
            checkApple();
            checkCollision();
            move();
        }
        repaint(); //same as pack()
    }
    public class TAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();
            
            if (key == KeyEvent.VK_LEFT && (!rightDirection)) {
                leftDirection = true;
                upDirection = false;
                downDirection = false;
            }
            
            if (key == KeyEvent.VK_RIGHT && (!leftDirection)) {
                rightDirection = true;
                upDirection = false;
                downDirection = false;
            }
            
            if (key == KeyEvent.VK_UP && (!downDirection)) {
                upDirection = true;
                leftDirection = false;
                rightDirection = false;
            }
            
            if (key == KeyEvent.VK_DOWN && (!upDirection)) {
                downDirection = true;
                leftDirection = false;
                rightDirection = false;
            }
        }
    }
}
