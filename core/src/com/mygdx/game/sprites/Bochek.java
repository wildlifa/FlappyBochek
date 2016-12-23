package com.mygdx.game.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

import java.util.Random;

/**
 * Created by andrei on 22.12.2016.
 */

public class Bochek {
    private static final int MOVEMENT = 100;
    private static final int GRAVITY = -15;
    private Vector3 position;
    private Vector3 velocity;
    private Texture texture;
    public Rectangle bounds;
    private Animation bochekAnimation;
    private Sound flap0,flap1,flap2,flap3,flap4,flap5,flap6,flap7;

    private Random generator;

    public Bochek(int x, int y){
        position = new Vector3(x,y,0);
        velocity = new Vector3(0,0,0);
        texture = new Texture("bocheckanimation.png");
        bochekAnimation = new Animation(new TextureRegion(texture), 3, 0.5f);
        bounds = new Rectangle(x+20,y+15, texture.getWidth()/3-40, texture.getHeight()-30);
        flap0 = Gdx.audio.newSound(Gdx.files.internal("sfx_flap.ogg"));
        flap1 = Gdx.audio.newSound(Gdx.files.internal("sfx_flap1.ogg"));
        flap2 = Gdx.audio.newSound(Gdx.files.internal("sfx_flap2.ogg"));
        flap3 = Gdx.audio.newSound(Gdx.files.internal("sfx_flap3.ogg"));
        flap4 = Gdx.audio.newSound(Gdx.files.internal("sfx_flap4.ogg"));
        flap5 = Gdx.audio.newSound(Gdx.files.internal("sfx_flap5.ogg"));
        flap6 = Gdx.audio.newSound(Gdx.files.internal("sfx_flap6.ogg"));
        flap7 = Gdx.audio.newSound(Gdx.files.internal("sfx_flap7.ogg"));

    }

    public void update(float dt){
        bochekAnimation.update(dt);
        if(position.y > 0){
            velocity.add(0, GRAVITY, 0);
        }
        velocity.scl(dt);
        position.add(MOVEMENT * dt, velocity.y,0);
        if (position.y < 0){
            position.y = 0;
        }
        velocity.scl(1/dt);
        bounds.setPosition(position.x+20, position.y+15);

    }

    public Vector3 getPosition() {
        return position;
    }

    public TextureRegion getTexture() {
        return bochekAnimation.getFrame();
    }
    public void jump(){
        generator = new Random();
        int i = generator.nextInt(7);
        velocity.y = 250;
        switch (i) {
            case 0:  flap0.play(0.3f);
                break;
            case 1:  flap1.play(0.3f);
                break;
            case 2:  flap2.play(0.3f);
                break;
            case 3:  flap3.play(0.3f);
                break;
            case 4:  flap4.play(0.3f);
                break;
            case 5:  flap5.play(0.3f);
                break;
            case 6:  flap6.play(0.3f);
                break;
            case 7:  flap7.play(0.3f);
                break;

        }

    }

    public Rectangle getBounds(){
        return bounds;
    }
    public void dispose(){
        texture.dispose();
        flap0.dispose();
    }
}
