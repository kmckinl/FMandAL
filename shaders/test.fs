#version 330 core

layout (location = 0) out vec4 color;

in DATA
{
	vec2 tc;
	vec3 position;
} fs_in;

uniform sampler2D tex;


void main()
{
	color = vec4(0.5, 0.5, 0.5, 1.0);
	if (color.w < 1.0f) 
		discard;
}