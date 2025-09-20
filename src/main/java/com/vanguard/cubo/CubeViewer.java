package com.vanguard.cubo;

import com.jme3.app.SimpleApplication;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.material.RenderState;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;

public class CubeViewer extends SimpleApplication {

    private Spatial dado;
    private boolean rotando = true;
    private boolean wireframe = false;

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
        dado.scale(1.0f);
        dado.setShadowMode(RenderQueue.ShadowMode.CastAndReceive);
        dado.setLocalTranslation(0f, 0.5f, 0f); // súbelo un poco sobre el piso
        rootNode.attachChild(dado);

        // Luces básicas
        DirectionalLight sol = new DirectionalLight();
        sol.setDirection(new Vector3f(-1f, -2f, -1f).normalizeLocal());
        rootNode.addLight(sol);

        AmbientLight ambiente = new AmbientLight();
        ambiente.setColor(ColorRGBA.White.mult(0.4f));
        rootNode.addLight(ambiente);

        // Cámara y fondo
        flyCam.setMoveSpeed(10f);
        cam.setLocation(new Vector3f(0f, 2f, 6f));
        cam.lookAt(Vector3f.ZERO, Vector3f.UNIT_Y);
        viewPort.setBackgroundColor(new ColorRGBA(0.1f, 0.1f, 0.12f, 1f));

        // --- Piso simple con Box (horizontal, centrado y recibe sombras) ---
        float size = 20f; // ancho/largo del piso
        Geometry piso = new Geometry("piso", new Box(size / 2f, 0.01f, size / 2f)); // (X,Y,Z)
        piso.setLocalTranslation(0f, 0f, 0f); // centrado en el origen

        Material m = new Material(assetManager, "Common/MatDefs/Light/Lighting.j3md");
        m.setBoolean("UseMaterialColors", true);
        m.setColor("Diffuse", new ColorRGBA(0.3f, 0.3f, 0.35f, 1f));
        m.setColor("Specular", ColorRGBA.Black);
        m.setFloat("Shininess", 0f);
        m.getAdditionalRenderState().setFaceCullMode(RenderState.FaceCullMode.Off); // evita culling desde abajo
        piso.setMaterial(m);

        piso.setShadowMode(RenderQueue.ShadowMode.Receive);
        rootNode.attachChild(piso);

        // Controles de teclado
        inputManager.addMapping("TOGGLE_ROT", new KeyTrigger(KeyInput.KEY_R));
        inputManager.addMapping("TOGGLE_WIRE", new KeyTrigger(KeyInput.KEY_G));
        inputManager.addMapping("RESET_CAM",  new KeyTrigger(KeyInput.KEY_C));
        inputManager.addListener(actionListener, "TOGGLE_ROT", "TOGGLE_WIRE", "RESET_CAM");
    }

    private final ActionListener actionListener = (name, isPressed, tpf) -> {
        if (isPressed) return; // solo al soltar
        switch (name) {
            case "TOGGLE_ROT":
                rotando = !rotando;
                break;
            case "TOGGLE_WIRE":
                wireframe = !wireframe;
                setWireframe(rootNode, wireframe);
                break;
            case "RESET_CAM":
                cam.setLocation(new Vector3f(0f, 2f, 6f));
                cam.lookAt(Vector3f.ZERO, Vector3f.UNIT_Y);
                break;
        }
    };

    @Override
    public void simpleUpdate(float tpf) {
        if (rotando && dado != null) {
            dado.rotate(0, 0.5f * tpf, 0); // giro suave en Y
        }
    }

    private void setWireframe(Spatial spatial, boolean enable) {
        spatial.depthFirstTraversal(s -> {
            if (s instanceof Geometry) {
                Geometry g = (Geometry) s;
                if (g.getMaterial() != null) {
                    g.getMaterial().getAdditionalRenderState().setWireframe(enable);
                }
            }
        });
    }
}
