# Cubo3D (v0.1)

> **Primera versión**: por ahora el proyecto **solo visualiza** un dado (.obj) exportado desde MagicaVoxel.

## Vista previa
![Cubo renderizado en jMonkeyEngine](src/main/resources/models/vers/1.0.png)



# Cubo3D (v0.2)

> **Estado:** visualizador 3D sencillo hecho con **Maven + jMonkeyEngine 3.6.1**.  
> Esta versión agrega **piso** (Box) y atajos de teclado. El objetivo por ahora es **ver** un dado exportado desde MagicaVoxel (`.obj`).

## 🎮 Controles
- **W/A/S/D + Mouse:** moverte por la escena
- **R:** pausar/continuar la rotación del dado
- **G:** alternar *wireframe* (malla) para depurar
- **C:** resetear la cámara al encuadre inicial


# Cubo3D (v0.3 — versión optimizada)

Visualizador 3D en **Maven + jMonkeyEngine 3.6.1** para cargar un dado `.obj` (MagicaVoxel).  
Esta versión prioriza simplicidad y rendimiento.

## ✅ Qué cambió en v0.3 (optimizada)
Quitamos para optimizar:
- **Toggles** `R/G/C` y el método `setWireframe` (menos callbacks y recorrido del grafo).
- **AmbientLight** (una **DirectionalLight** es suficiente en este caso).
- **Materiales innecesarios en el piso**: ahora usamos `Unshaded` **sin** specular (más barato).

Resultado: menos imports, menos lógica, menor consumo y el mismo objetivo visual.

![Cubo renderizado en jMonkeyEngine](src/main/resources/models/vers/3.0.png)