package rosalila.dark.sacrifice.repartidor;

import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.ITiledTextureRegion;

public class Point extends AnimatedSprite {
	Box box;
	boolean completed;
	public Point(float pX, float pY,
			ITiledTextureRegion pTiledTextureRegion) {
		super(pX, pY, pTiledTextureRegion, Global.vertex_buffer_object_manager);
		box=null;
		completed=false;
	}
	void complete()
	{
		this.setCurrentTileIndex(1);
		completed=true;
	}
	boolean isCompleted()
	{
		return completed;
	}
}
