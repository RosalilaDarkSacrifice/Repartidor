package rosalila.dark.sacrifice.repartidor;

import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.opengl.texture.region.ITiledTextureRegion;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;

public class Chuy extends AnimatedSprite {
	float velocity;
	public Body body;
	float destination_x,destination_y;
	AnimatedSprite destination;
	
	public Chuy(float pX, float pY, ITiledTextureRegion pTiledTextureRegion,PhysicsWorld mPhysicsWorld,final AnimatedSprite destination) {
		super(pX, pY, pTiledTextureRegion, Global.vertex_buffer_object_manager);
		
        //Smaller chuys
        this.setHeight(this.getHeight()*(float)0.7);
        this.setWidth(this.getWidth()*(float)0.7);
		
		final FixtureDef playerFixtureDef = PhysicsFactory.createFixtureDef(0, 0, 0.5f);
		this.body = PhysicsFactory.createCircleBody(mPhysicsWorld, this, BodyType.DynamicBody, playerFixtureDef);
        mPhysicsWorld.registerPhysicsConnector(new PhysicsConnector(this, body, true, false){
                @Override
                public void onUpdate(float pSecondsElapsed){
                        super.onUpdate(pSecondsElapsed);
                        
                		if(destination_x!=-1 && destination_y!=-1)
                		{
                			destination.setX(destination_x);
                			destination.setY(destination_y);
                			float distance_x=destination_x-getX();
                			float distance_y=destination_y-getY();
                			float total_distance=(float)Math.sqrt(Math.pow(distance_x, 2)+(float)Math.pow(distance_y, 2));
                			
                			if(Math.abs(distance_x)<25 && Math.abs(distance_y)<25)
                				destination_x=destination_y=-1;
                			
                			body.setLinearVelocity(distance_x*velocity/total_distance,distance_y*velocity/total_distance);
                			Global.mZoomCamera.setCenter(getX(),getY());
                			destination.setVisible(true);
                		}else
                		{
                			destination.setVisible(false);
                			body.setLinearVelocity(0,0);
                			stopAnimation();
                		}
                }
        });
        
		velocity=(float) 7;
		this.destination_x=-1;
		this.destination_y=-1;
		
		animate("right");
		this.stopAnimation();
	}
	
	public void move(float destination_x,float destination_y)
	{
		destination_x-=getWidth()/2;
		destination_y-=getHeight()/2;
		this.destination_x=destination_x;
		this.destination_y=destination_y;
		if(Math.abs(this.getX()-this.destination_x)>Math.abs(this.getY()-this.destination_y))
			this.destination_y=this.getY();
		else
			this.destination_x=this.getX();
		this.stopAnimation();
		
		float distance_x=Math.abs(getX()-destination_x);
		float distance_y=Math.abs(getY()-destination_y);
		
		if(distance_x>distance_y)
		{
			if(getX()>destination_x)
			{
				animate("left");
			}
			else
			{
				animate("right");
			}
		}else
		{
			if(getY()>destination_y)
			{
				animate("up");
			}
			else
			{
				animate("down");
			}
		}
	}
	
	public void animate(String orientation)
	{
		if(orientation.equals("up"))
			animate(new long[]{200, 200, 200}, 0, 2, true);
		if(orientation.equals("down"))
			animate(new long[]{200, 200, 200}, 3, 5, true);
		if(orientation.equals("right"))
			animate(new long[]{200, 200, 200}, 6, 8, true);
		if(orientation.equals("left"))
			animate(new long[]{200, 200, 200}, 9, 11, true);
	}
}
