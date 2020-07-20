package cloud.lemonslice.afterthedrizzle.client.texture;

public class TexturePos
{
    private final int textureX;
    private final int textureY;
    private final int width;
    private final int height;

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

    public int getWidth()
    {
        return width;
    }

    public int getHeight()
    {
        return height;
    }
}
