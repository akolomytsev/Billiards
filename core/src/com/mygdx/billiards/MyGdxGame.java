package com.mygdx.billiards;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.ScreenUtils;

public class MyGdxGame extends ApplicationAdapter {
	private SpriteBatch batch;
	private Texture img, coinImg;
	private ShapeRenderer shapeRenderer; //Рисует конкретно фигуры

	private MyInputProcessor myInputProcessor;

	private OrthographicCamera camera;

	private float x, y;

	private PhysicsX physicsX;

	private Rectangle rectangle, window;

	private Circle circle;
	private Body body;
	
	@Override
	public void create () {
		physicsX = new PhysicsX();


		BodyDef def = new BodyDef(); // описатель тела
		FixtureDef fDef = new FixtureDef(); // описатель формы

		def.gravityScale = 1; // Масштабирование гравитации, можно поставить и отрицательное значение, тело полетит вверх
		def.type = BodyDef.BodyType.StaticBody; // тип тела, статическое, динамическое или кинематическое
		def.position.set(300,150); // позиция тела

		//CircleShape // Круг
		PolygonShape shape = new PolygonShape(); //Прямоугольник
		shape.setAsBox(250, 10);// размеры прямоугольника от цента в обе стороны, то есть 20х20
		//shape.setRadius(10);
		fDef.shape = shape;
		fDef.density = 1; // плотность
		fDef.friction = 0; //скольжение
		fDef.restitution = 0; // Прыгучесть

		physicsX.world.createBody(def).createFixture(fDef).setUserData("rectangle");



		def.type = BodyDef.BodyType.DynamicBody; // тип тела, статическое, динамическое или кинематическое
		def.gravityScale = 4; // Масштабирование гравитации, можно поставить и отрицательное значение, тело полетит вверх
		for (int i = 1; i < 10; i++) {
			def.position.set(MathUtils.random(100,500),MathUtils.random(300,500)); // позиция тела
			CircleShape shape1 = new CircleShape(); // Круг
			shape1.setRadius(10);// диаметр круга, бля, есть только через радиус
			fDef.shape = shape1; // впихнул
			fDef.density = 1; // плотность
			fDef.friction = 0; //скольжение
			fDef.restitution = 1; // Прыгучесть

			physicsX.world.createBody(def).createFixture(fDef).setUserData("sphere");
		}

		//Привязываем к картинке
		def.position.set(MathUtils.random(100,500),MathUtils.random(300,500)); // позиция тела
		CircleShape shape1 = new CircleShape(); // Круг
		shape1.setRadius(10);// диаметр круга, бля, есть только через радиус
		fDef.shape = shape1; // впихнул
		fDef.density = 1; // плотность
		fDef.friction = 0; //скольжение
		fDef.restitution = 1; // Прыгучесть

		body = physicsX.world.createBody(def);
		body.createFixture(fDef).setUserData("sphere");


		shape.dispose();
		shapeRenderer = new ShapeRenderer();
		rectangle = new Rectangle();
		circle = new Circle();
		window = new Rectangle(0,0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		myInputProcessor = new MyInputProcessor();
		Gdx.input.setInputProcessor(myInputProcessor);

		batch = new SpriteBatch();
		img = new Texture("break_time_the_national_pool_tour.jpg");
		coinImg = new Texture("sharikc.jpg");







		camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());



	}

	@Override
	public void render () {
		ScreenUtils.clear(1, 1, 1, 0);


		camera.position.x = body.getPosition().x;
		camera.position.y = body.getPosition().y;
		camera.zoom = 1;
		camera.update();
//		float x = Gdx.input.getX()- coinImg.getWidth()/2; //привязка к мышке по х
//		float y = Gdx.graphics.getHeight() - Gdx.input.getY() - coinImg.getHeight()/2; //привязка к мышке по у

		if (myInputProcessor.getOutString().contains("A")) x--;
		if (myInputProcessor.getOutString().contains("D")) x++;
		if (myInputProcessor.getOutString().contains("W")) y++;
		if (myInputProcessor.getOutString().contains("S")) y--;



		batch.begin();
		batch.setProjectionMatrix(camera.combined);
		batch.draw(img, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		batch.draw(coinImg, x, y,
				body.getPosition().x,
				body.getPosition().y,
				coinImg.getWidth(),
				coinImg.getHeight(),
				0.03f, 0.03f, 0, 0, 0,
				coinImg.getWidth(),
				coinImg.getHeight(),
				false,false);

		batch.end();



		rectangle.x = x;
		rectangle.y = y;
		rectangle.width = coinImg.getWidth()/10;
		rectangle.height = coinImg.getHeight()/10;

		circle.x = x;
		circle.y = y;




		shapeRenderer.begin(ShapeRenderer.ShapeType.Line);// Как отрисовывать, в данном случае линии
		shapeRenderer.setColor(Color.BLACK); //цвет линий
		shapeRenderer.rect(rectangle.x + coinImg.getWidth()/2 - rectangle.width/2, rectangle.y + coinImg.getHeight()/2 - rectangle.height/2, rectangle.width, rectangle.height);
		shapeRenderer.circle(circle.x + coinImg.getWidth()/2, circle.y + coinImg.getHeight()/2, 10);//какую фигуру отрисовать, круг
		shapeRenderer.end();



		//System.out.println(myInputProcessor.getOutString());
		batch.setProjectionMatrix(camera.combined);

		physicsX.step();
		physicsX.debugDraw(camera);
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
		coinImg.dispose();
	}
}
