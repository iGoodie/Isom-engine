#ifdef GL_ES
precision mediump float;
precision mediump int;
#endif

#define PROCESSING_TEXTURE_SHADER

uniform sampler2D texture;

varying vec4 vertColor;
varying vec4 vertTexCoord;

const vec3 SEPIA = vec3(1.2, 1.0, 0.8);
const vec3 NTSC_WEIGHTS = vec3(0.299, 0.587, 0.114);

uniform float percent;

void main() {
	vec4 texColor = texture2D(texture, vertTexCoord.xy);
	
	float gray = dot(texColor.rgb, NTSC_WEIGHTS);
	vec3 sepiaColor = vec3(gray) * SEPIA;
	
	texColor.rgb = mix(texColor.rgb, sepiaColor, percent);
	
	//gl_FragColor = texColor * vertColor;
	//gl_FragColor = texColor;
	gl_FragColor = texColor;
}