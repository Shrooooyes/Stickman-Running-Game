import java.awt.Image;


public class Block{
    public int blockHeight;
    public int blockWidth;

    public int posX;
    public int posY;

    Image img;

    public Block(int height, int width, int x, int y, Image img){
        this.blockHeight = height;
        this.blockWidth = width;
        this.posX = x;
        this.posY = y;
        this.img = img;
    }
}
