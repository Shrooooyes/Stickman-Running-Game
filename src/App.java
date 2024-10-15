import javax.swing.*;

public class App {
    public static void main(String[] args) {
        int gameWidth = 750;
        int gameHeight = 250;


        JFrame frame = new JFrame("Running Game");
        Game game = new Game();
        frame.add(game);
        game.requestFocus();
        frame.setVisible(true);
        frame.pack();
        frame.setSize(gameWidth, gameHeight);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
    }
}