package com.vanguard.cubo;

import com.jme3.app.SimpleApplication;
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;

public class CubeViewer extends SimpleApplication {

    public static void main(String[] args) {
        new CubeViewer().start(); // inicia la app y abre la ventana
    }

    @Override
    public void simpleInitApp() {
        // 1) Cargar el modelo desde resources:
        Spatial dado = assetManager.loadModel("models/dado/dado.obj");

        // 2) (Opcional) Ajustar tamaño si hace falta
        dado.scale(1.0f); // prueba 0.1f si sale gigante, o 10f si sale muy chico

        // 3) Agregarlo a la escena
        rootNode.attachChild(dado);

        // 4) Luces para que se vea con sombras/colores
        DirectionalLight sol = new DirectionalLight();
        sol.setDirection(new Vector3f(-1f, -2f, -1f).normalizeLocal());
        rootNode.addLight(sol);

        AmbientLight ambiente = new AmbientLight();
        ambiente.setColor(ColorRGBA.White.mult(0.4f));
        rootNode.addLight(ambiente);

        // 5) Cámara y fondo
        flyCam.setMoveSpeed(10f);                 // moverte con WASD + mouse
        cam.setLocation(new Vector3f(0, 1.5f, 5));
        cam.lookAt(Vector3f.ZERO, Vector3f.UNIT_Y);
        viewPort.setBackgroundColor(new ColorRGBA(0.1f, 0.1f, 0.12f, 1f));
    }
}
