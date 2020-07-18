package cloud.lemonslice.afterthedrizzle.client.texture;

public class TexturePos
{
    private int textureX;
    private int textureY;
    private int width;
    private int height;

    public TexturePos(int textureX, int textureY, int width, int height)
    {
        this.textureX = textureX;
        this.textureY = textureY;
        this.width = width;
        this.height = height;
    }

    public int getX()
    {
        return textureX;
    }

    public int getY()
    {
        return textureY;
    }

    public int getU()
    {
        return width;
    }

    public int getV()
    {
        return height;
    }
}
