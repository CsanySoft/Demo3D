package com.majzer.threeddemo;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.loader.G3dModelLoader;
import com.badlogic.gdx.graphics.g3d.utils.AnimationController;
import com.badlogic.gdx.graphics.g3d.utils.FirstPersonCameraController;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.UBJsonReader;

/**
 * Created by tanulo on 2018. 04. 26..
 */

public class ModelDemo extends ApplicationAdapter {

    private PerspectiveCamera camera;
    private ModelBatch modelBatch;
    private Model model;
    private ModelInstance modelInstance;
    private Environment environment;
    private AnimationController animationController;
    private float x, y, z;
    private FirstPersonCameraController cameraController;

    @Override
    public void create() {
        camera = new PerspectiveCamera(75, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        x = 0f; y = 100f; z = 100f;
        camera.position.set(x,y,z);
        camera.lookAt(0f,100f,0f);
        camera.near = 0.1f;
        camera.far = 300.0f;

        modelBatch = new ModelBatch();
        UBJsonReader jsonReader = new UBJsonReader();
        G3dModelLoader modelLoader = new G3dModelLoader(jsonReader);
        model = modelLoader.loadModel(Gdx.files.getFileHandle("walking_3.g3db", Files.FileType.Internal));

        modelInstance = new ModelInstance(model);

        environment = new Environment();
        environment.set(new ColorAttribute(ColorAttribute.AmbientLight,0.8f,0.8f,0.8f,1.0f));

        animationController = new AnimationController(modelInstance);
        animationController.setAnimation("mixamo.com", -1, new AnimationController.AnimationListener() {
            @Override
            public void onEnd(AnimationController.AnimationDesc animation) {

            }

            @Override
            public void onLoop(AnimationController.AnimationDesc animation) {
                Gdx.app.log("INFO","Animation ended");
            }
        });

        cameraController = new FirstPersonCameraController(camera);
        Gdx.input.setInputProcessor(cameraController);
    }

    @Override
    public void render() {
        Gdx.gl.glViewport(0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        Gdx.gl.glClearColor(1,1,1,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        camera.update(true);
        animationController.update(Gdx.graphics.getDeltaTime());
        cameraController.update(Gdx.graphics.getDeltaTime());

        modelBatch.begin(camera);
        modelBatch.render(modelInstance, environment);
        //updateKeyPress();
        modelBatch.end();
    }

    private void updateKeyPress(){
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT))
            camera.rotateAround(new Vector3(0f,0f,0f), new Vector3(0f,1f,0f), -1f);
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT))
            camera.rotateAround(new Vector3(0f,0f,0f), new Vector3(0f,1f,0f), 1f);
        if(Gdx.input.isKeyPressed(Input.Keys.UP))
            camera.rotateAround(new Vector3(0f,0f,0f), new Vector3(1f,0f,0f), 1f);
        if(Gdx.input.isKeyPressed(Input.Keys.DOWN))
            camera.rotateAround(new Vector3(0f,0f,0f), new Vector3(1f,0f,0f), -1f);

        if(Gdx.input.isKeyPressed(Input.Keys.D)){
            x+=0.01f;
            camera.position.set(x,y,z);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.A)){
            x-=0.01f;
            camera.position.set(x,y,z);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.W)){
            z-=0.01f;
            camera.position.set(x,y,z);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.S)){
            z+=0.01f;
            camera.position.set(x,y,z);
        }
    }

    @Override
    public void dispose() {
        modelBatch.dispose();
        model.dispose();
    }
}
