package com.mygdx.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.MyBochek;
import com.mygdx.game.sprites.Bochek;
import com.mygdx.game.sprites.Tube;

/**
 * Created by andrei on 22.12.2016.
 */

public class PlayState extends State {
    private static final int TUBE_SPACING = 125;
    private static final int TUBE_COUNT = 4;
    private static final int GROUND_Y_OFFSET = -20;
    private Bochek bochek;
    private Texture bg;
    private Texture ground;
    private Vector2 groundPos1, groundPos2;

//    private ShapeRenderer shapeRenderer;


    private Array<Tube> tubes;

    protected PlayState(GameStateManager gsm) {
        super(gsm);
//        shapeRenderer = new ShapeRenderer();
        bochek = new Bochek(30,250);
        ground = new Texture("cola_ground.png");
        groundPos1 = new Vector2(cam.position.x - cam.viewportWidth/2,GROUND_Y_OFFSET);
        groundPos2 = new Vector2((cam.position.x - cam.viewportWidth/2)+ground.getWidth(),GROUND_Y_OFFSET);
        cam.setToOrtho(false, MyBochek.WIDTH/2, MyBochek.HEIGHT/2);
        bg = new Texture("background.png");
        tubes = new Array<Tube>();
        for(int i = 1; i <= TUBE_COUNT; i++){
            tubes.add(new Tube(i*(TUBE_SPACING + Tube.TUBE_WIDTH)));
        }
    }

    @Override
    protected void handleInput() {
        if(Gdx.input.justTouched()){
            bochek.jump();
        }
    }

    @Override
    public void update(float dt) {
        handleInput();
        updateGround();
        bochek.update(dt);
        cam.position.x = bochek.getPosition().x + 80;
        for(int i = 0; i < tubes.size; i++){
            Tube tube = tubes.get(i);
            if(cam.position.x - cam.viewportWidth/2 > tube.getPosTopTube().x + tube.getTopTube().getWidth()){
                tube.reposition(tube.getPosTopTube().x + (Tube.TUBE_WIDTH+ TUBE_SPACING)*TUBE_COUNT);
            }
            if(tube.collides(bochek.getBounds())){
                MyBochek.playDeathSound();
                gsm.set(new MenuState(gsm));
            }
        }
        if(bochek.getPosition().y<10){
            MyBochek.playDeathSound();
            gsm.set(new MenuState(gsm));
        }
        cam.update();

    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(cam.combined);
//        shapeRenderer.setProjectionMatrix(sb.getProjectionMatrix());
        sb.begin();
        sb.draw(bg, cam.position.x - (cam.viewportWidth/2),0);
        sb.draw(bochek.getTexture(),bochek.getPosition().x, bochek.getPosition().y);

        for(Tube tube : tubes){
            sb.draw(tube.getTopTube(), tube.getPosTopTube().x, tube.getPosTopTube().y);
            sb.draw(tube.getBottomTube(), tube.getPosBottomTube().x, tube.getPosBottomTube().y);
        }
        sb.draw(ground,groundPos1.x,groundPos1.y);
        sb.draw(ground,groundPos2.x,groundPos2.y);
        sb.end();
//        Hitbox Display
//        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
//        shapeRenderer.setColor(Color.RED);
//        shapeRenderer.rect(bochek.bounds.x, bochek.bounds.y, bochek.bounds.getWidth(), bochek.bounds.getHeight());
//        shapeRenderer.end();

    }

    @Override
    public void dispose() {
        bg.dispose();
        bochek.dispose();
        for(Tube tube: tubes){
            tube.dispose();
        }
        ground.dispose();

    }

    public void updateGround(){
        if(cam.position.x-(cam.viewportWidth/2)>groundPos1.x+ground.getWidth()){
            groundPos1.add(ground.getWidth()*2,0);
        }
        if(cam.position.x-(cam.viewportWidth/2)>groundPos2.x+ground.getWidth()){
            groundPos2.add(ground.getWidth()*2,0);
        }
    }
}
