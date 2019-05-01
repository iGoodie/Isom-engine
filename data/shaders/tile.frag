#version 400 core

in vec2 pass_TexCoord;

out vec4 fragColor;

uniform sampler2D sampler;

void main() {

	fragColor = texture(sampler, pass_TexCoord);

}
