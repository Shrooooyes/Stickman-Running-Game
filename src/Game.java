import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class Game extends JPanel implements ActionListener, KeyListener{
    int gameHeight = 250;
    int gameWidth = 700;

    int spitritHeight = 150;

    boolean gameOver = false;
    int score = 0;
    //Physics
    int velocityY = 0;
    int velocityX = -9;
    int gravity = 1;

    Image stickmanRunImg;
    Image stickmanJumpImg;

    Image cactus1;
    Image cactus2;
    Image cactus3;

    //StickMan
    int stickmanHeight = 50;
    int stickmanWidhth = 75;
    int stickmanX = 50;
    int stickmanY = spitritHeight;

    Stickman stickman;

    int cactus2Width = 85;
    int cactus3Width = 60;
    int cactus1Width = 90;

    int cactusHeight = 100;
    int cactusX = 700;
    int cactusY = 100;

    ArrayList<Cactus> cacti;

    Timer gameTimer;
    Timer cactusTimer;

    public Game(){
        setPreferredSize(new Dimension(gameWidth, gameHeight));
        setFocusable(true);
        addKeyListener(this);
        
        stickmanRunImg = new ImageIcon(getClass().getResource("/assets/running.gif")).getImage();
        stickmanJumpImg = new ImageIcon(getClass().getResource("/assets/jumping.jpg")).getImage();

        cactus1 = new ImageIcon(getClass().getResource("/assets/cact1.png")).getImage();
        cactus2 = new ImageIcon(getClass().getResource("/assets/cact2.png")).getImage();
        cactus3 = new ImageIcon(getClass().getResource("/assets/cact3.png")).getImage();
        
        //Stickman
        stickman = new Stickman(stickmanHeight, stickmanWidhth, stickmanX, stickmanY, stickmanRunImg);

        //Cacti
        cacti = new ArrayList<>();

        //Timers
        gameTimer = new Timer(1000/60, this);
        gameTimer.start();

        cactusTimer = new Timer(1500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                placeCactus();
            }
        });
        cactusTimer.start();

        //Change this
        setBackground(Color.WHITE);
    }

    boolean detectCollision(Stickman rect1, Cactus rect2){
        return rect1.posX < rect2.posX + rect2.blockWidth &&
        rect1.posX + rect1.blockWidth > rect2.posX &&
        rect1.posY < rect2.posY + rect2.blockHeight &&
        rect1.posY + rect1.blockHeight > rect2.posY;
    }

    public void placeCactus(){
        double chance = Math.random();
        if(chance > 0.9){
            Cactus c = new Cactus(cactusHeight, cactus2Width, cactusX, cactusY, cactus2);
            cacti.add(c);
        }
        else if(chance > 0.7){
            Cactus c = new Cactus(cactusHeight, cactus3Width, cactusX, cactusY, cactus3);
            cacti.add(c);
        }
        else if(chance > 0.5){
            Cactus c = new Cactus(cactusHeight, cactus1Width, cactusX, cactusY, cactus1);
            cacti.add(c);
        }
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.drawImage(stickman.img, stickman.posX, stickman.posY, stickman.blockWidth, stickman.blockHeight, null);
        
        for(Cactus c : cacti){

            if(detectCollision(stickman, c)){
                gameOver = true;
            }

            g.drawImage(c.img, c.posX, c.posY, c.blockWidth, c.blockHeight,null);
        }

        g.setColor(Color.black);
        g.setFont(new Font("Courier", Font.PLAIN, 32));
        if (gameOver) {
            g.drawString("Game Over: " + String.valueOf(score), 10, 35);
        }
        else {
            g.drawString("Score: " + String.valueOf(score), 10, 35);
        }
    }

    public void move(){
        velocityY += gravity;
        stickman.posY += velocityY;

        score += 1;

        if(gameOver){
            velocityX = 0;
            velocityY = 0;


            gameTimer.stop();
            cactusTimer.stop();
        }

        if(stickman.posY > stickmanY){
            stickman.posY = stickmanY;
            velocityY = 0;
            stickman.img = stickmanRunImg;
        }

        for(Cactus c: cacti){
            c.posX += velocityX;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        move();
        repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_SPACE){
            if(gameOver){
                gameOver = false;
                gameTimer.start();
                cactusTimer.start();
                stickman.posX = 50;
                stickman.posY = stickmanY;
                velocityX = -9;
                velocityY = 0;

                score  = 0;

                cacti.clear();

                gameTimer.start();
                cactusTimer.start();
            }

            if(stickman.posY == stickmanY){
                velocityY = -18;
                stickman.img = stickmanJumpImg;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void keyTyped(KeyEvent e) {}

}
