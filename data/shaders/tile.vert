#version 400 core

layout (location = 0) in vec3 vertPos;
layout (location = 1) in vec2 texCoord;

out vec2 pass_TexCoord;

uniform mat4 projectionMatrix;
uniform mat4 viewMatrix;
uniform mat4 transformationMatrix;

void main() {

	gl_Position = projectionMatrix * viewMatrix * transformationMatrix * vec4(vertPos, 1.0);

	pass_TexCoord = texCoord;

}
