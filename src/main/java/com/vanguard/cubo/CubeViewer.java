package com.vanguard.cubo;

import com.jme3.app.SimpleApplication;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;

public class CubeViewer extends SimpleApplication {

    private Spatial dado;
    private boolean rotando = true;

    public static void main(String[] args) {
        new CubeViewer().start();
    }

    @Override
    public void simpleInitApp() {
        // UI limpia
        setDisplayFps(false);
        setDisplayStatView(false);

        // Cargar modelo
        dado = assetManager.loadModel("models/dado/dado.obj");
        dado.scale(1f);
        dado.setLocalTranslation(0f, 0.5f, 0f); // un poco sobre el piso
        dado.setShadowMode(RenderQueue.ShadowMode.CastAndReceive);
        rootNode.attachChild(dado);

        // Luz direccional (sencilla y suficiente)
        DirectionalLight sol = new DirectionalLight();
        sol.setDirection(new Vector3f(-1f, -2f, -1f).normalizeLocal());
        rootNode.addLight(sol);

        // Piso ultra simple (Box delgadito)
        float size = 20f;
        Geometry piso = new Geometry("piso", new Box(size/2f, 0.01f, size/2f));
        piso.setLocalTranslation(0f, 0f, 0f);
        piso.setShadowMode(RenderQueue.ShadowMode.Receive);

        // Material Unshaded (barato y suficiente para “piso plano”)
        Material m = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        m.setColor("Color", new ColorRGBA(0.28f, 0.3f, 0.34f, 1f));
        piso.setMaterial(m);
        rootNode.attachChild(piso);

        // Cámara y fondo
        flyCam.setMoveSpeed(10f);
        cam.setLocation(new Vector3f(0f, 2f, 6f));
        cam.lookAt(Vector3f.ZERO, Vector3f.UNIT_Y);
        viewPort.setBackgroundColor(new ColorRGBA(0.1f, 0.1f, 0.12f, 1f));
    }

    @Override
    public void simpleUpdate(float tpf) {
        if (rotando && dado != null) {
            dado.rotate(0, 0.5f * tpf, 0); // giro suave
        }
    }
}
