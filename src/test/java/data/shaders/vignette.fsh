#ifdef GL_ES
precision mediump float;
precision mediump int;
#endif

#define PROCESSING_TEXTURE_SHADER

uniform sampler2D texture;
uniform vec2 resolution;
 
varying vec4 vertColor;
varying vec4 vertTexCoord;

void main() {
	vec4 texColor = texture2D(texture, vertTexCoord.xy);
	
	vec2 position = (gl_FragCoord.xy / resolution.xy) - vec2(0.5);
	
	float len = length(position);
	
	gl_FragColor = vec4( texColor.rgb*(1.0-len), 1.0 );
}