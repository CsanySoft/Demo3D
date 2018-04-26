package com.majzer.threeddemo;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.utils.FirstPersonCameraController;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;

public class ThreeDDemo extends ApplicationAdapter{

	private PerspectiveCamera perspectiveCamera;
	private ModelBatch modelBatch;
	private ModelBuilder modelBuilder;
	private Model blueBox, greenCone, redSphere;
	private ModelInstance blueBoxInstance, greenConeInstance, redSphereInstance;
	private Environment environment;
	private float x, y, z;
	private boolean right;
	private FirstPersonCameraController cameraController;

	@Override
	public void create () {
		x = 0f; y = 0f; z = 3f;
		perspectiveCamera = new PerspectiveCamera(75, Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
		perspectiveCamera.position.set(x,y,z);
		perspectiveCamera.lookAt(0f,0f,0f);
		perspectiveCamera.near = 0.1f;
		perspectiveCamera.far = 300f;
		modelBatch = new ModelBatch();
		modelBuilder = new ModelBuilder();
		blueBox = modelBuilder.createBox(2f,2f,2f,
				new Material(ColorAttribute.createDiffuse(new Color(0f,0f,0.6f,0.65f))),
				VertexAttributes.Usage.Position|VertexAttributes.Usage.Normal);
		blueBoxInstance = new ModelInstance(blueBox,0f,0f,0f);

		greenCone = modelBuilder.createCone(2f,2f,2f,3,
				new Material(ColorAttribute.createDiffuse(new Color(0f,0.6f,0f,0.65f))),
				VertexAttributes.Usage.Position|VertexAttributes.Usage.Normal);
		greenConeInstance = new ModelInstance(greenCone, 4f,0f,0f);

		redSphere = modelBuilder.createSphere(2f,2f,2f, 16, 16,
				new Material(ColorAttribute.createDiffuse(new Color(0.6f,0f,0f,0.65f))),
				VertexAttributes.Usage.Position|VertexAttributes.Usage.Normal);
		redSphereInstance = new ModelInstance(redSphere,8f,0f,0f);
		environment = new Environment();
		environment.set(new ColorAttribute(ColorAttribute.AmbientLight,0.8f,0.8f,0.8f,1f));

		right = false;

		cameraController = new FirstPersonCameraController(perspectiveCamera);
		Gdx.input.setInputProcessor(cameraController);
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0.7f, 1f, 0.3f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT|GL20.GL_DEPTH_BUFFER_BIT);
		perspectiveCamera.update();
		cameraController.update(Gdx.graphics.getDeltaTime());
		modelBatch.begin(perspectiveCamera);
		modelBatch.render(blueBoxInstance);
		modelBatch.render(greenConeInstance);
		modelBatch.render(redSphereInstance);
		modelBatch.end();
		//updateKeyPress();
		//perspectiveCamera.position.set(x,0f,5f);
		//blueBoxInstance.transform.rotate(2f,1.5f,1f,8f);
		//greenConeInstance.transform.rotate(6f,1.5f,1f,8f);
		//redSphereInstance.transform.rotate(10f,1.5f,1f,8f);
		//perspectiveCamera.lookAt(5f,0f,3f);
	}

	@Override
	public void dispose () {
		modelBatch.dispose();
		blueBox.dispose();
		greenCone.dispose();
		redSphere.dispose();
	}

	private void updateKeyPress(){
		if(Gdx.input.isKeyPressed(Input.Keys.RIGHT))
			perspectiveCamera.rotateAround(new Vector3(0f,0f,0f), new Vector3(0f,1f,0f), -1f);
		if(Gdx.input.isKeyPressed(Input.Keys.LEFT))
			perspectiveCamera.rotateAround(new Vector3(0f,0f,0f), new Vector3(0f,1f,0f), 1f);
		if(Gdx.input.isKeyPressed(Input.Keys.UP))
			perspectiveCamera.rotateAround(new Vector3(0f,0f,0f), new Vector3(1f,0f,0f), 1f);
		if(Gdx.input.isKeyPressed(Input.Keys.DOWN))
			perspectiveCamera.rotateAround(new Vector3(0f,0f,0f), new Vector3(1f,0f,0f), -1f);

		if(Gdx.input.isKeyPressed(Input.Keys.D)){
			x+=0.01f;
			perspectiveCamera.position.set(x,y,z);
		}
		if(Gdx.input.isKeyPressed(Input.Keys.A)){
			x-=0.01f;
			perspectiveCamera.position.set(x,y,z);
		}
		if(Gdx.input.isKeyPressed(Input.Keys.W)){
			z-=0.01f;
			perspectiveCamera.position.set(x,y,z);
		}
		if(Gdx.input.isKeyPressed(Input.Keys.S)){
			z+=0.01f;
			perspectiveCamera.position.set(x,y,z);
		}
	}

}
