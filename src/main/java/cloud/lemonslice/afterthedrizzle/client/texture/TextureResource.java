package cloud.lemonslice.afterthedrizzle.client.texture;

import net.minecraft.util.ResourceLocation;

public class TextureResource
{
    private ResourceLocation texture;
    private TexturePos pos;

    public TextureResource(ResourceLocation texture, TexturePos pos)
    {
        this.texture = texture;
        this.pos = pos;
    }

    public ResourceLocation getTexture()
    {
        return texture;
    }

    public TexturePos getPos()
    {
        return pos;
    }
}
