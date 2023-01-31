package com.mygdx.billiards;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;

public class MyInputProcessor implements InputProcessor {

    private String outString = "";

    public String getOutString(){
        return outString;
    }

    @Override
    public boolean keyDown(int keycode) { //возвращает код кнопки которую мы нажали
        if(!outString.contains(Input.Keys.toString(keycode))){
            outString += Input.Keys.toString(keycode);
        }
        return true;
    }

    @Override
    public boolean keyUp(int keycode) { // возвращает код кнопки которую мы отпустили
        if(outString.contains(Input.Keys.toString(keycode))){
            String tmp = outString.replace(Input.Keys.toString(keycode), "");
            outString = tmp;
        }
        return true;
    }

    @Override
    public boolean keyTyped(char character) {// Возвращает букву кнопки которую мы нажали
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) { // Возвращает координаты где мы коснулись экрана
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {// Возвращает букву кнопки которую мы отпустили
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) { // Это когда тащишь мышкой по экрану
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }
}
