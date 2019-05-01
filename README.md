<p align="center"><img height="200px" src="https://cdn.discordapp.com/attachments/414163950855389186/414427038796218370/mdpanel.png"></p>

<!-- Badges -->
<p align="center">
  <a><img src="https://img.shields.io/badge/status-in--dev-29ab87.svg"></a>
  <a href="https://discordapp.com/invite/v4JHQeT"><img src="https://img.shields.io/discord/414039152045064192.svg?colorB=7289da&label=discord"></a>
  <a href="https://github.com/iGoodie/Isom-engine/releases"><img src="https://img.shields.io/github/downloads/iGoodie/Isom-engine/total.svg"></a>
  <a href="https://github.com/iGoodie/Isom-engine/blob/master/LICENSE"><img src="https://img.shields.io/github/license/iGoodie/Isom-engine.svg"></a>
</p>

## What is Isom-engine?

Isom-engine is an isometric rendering engine was being built on-top of Processing library. However, due to the performance hit on loaded amount of tile rendering callback, an attempt to implement it from scratch with LWJGL has started. This branch aims to re-implement following features using OpenGL bindings:
* 3 layered coordination map (Isometric World, Canvas, Screen planes)
* Multi-threaded resource loading mechanism
* Debug units (with [Goodieutils](https://github.com/iGoodie/Goodieutils) extensions)
* LUA scripting port
* Better input handling patterns
* Isometric projection for 3D meshes

:exclamation: **DISCLAIMER** :exclamation: *The project started as a capstone project. The development before the pre-release is planned to continue a whole semester or more (6-8 months). The engine doesn't promise a highly optimal performance comparing to the other engines out there.*

<!--p><img height="60px" src="https://cdn.discordapp.com/attachments/414163950855389186/414426001146118165/preview.png"></p-->
## Preview

![3D Mesh Projection](preview/p1.png?raw=true)
![Culling Demo](preview/p2.png?raw=true)