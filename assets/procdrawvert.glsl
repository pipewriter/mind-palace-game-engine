#version 410 core

layout (location = 0) in float spriteoffset;
layout (location = 1) in vec3 position;
layout (location = 2) in vec2 textureMap;

layout (location = 3) in vec3 c1;
layout (location = 4) in vec3 c2;
layout (location = 5) in vec3 c3;
layout (location = 6) in vec3 c4;
layout (location = 7) in vec3 c5;
layout (location = 8) in vec3 c6;
layout (location = 9) in vec3 c7;
layout (location = 10) in vec3 c8;
layout (location = 11) in float trans;
layout (location = 12) in float m;
layout (location = 13) in int cc1;

layout (location = 0) out float so;
layout (location = 2) out vec2 st;
layout (location = 1) out vec3 j1;
layout (location = 3) out vec3 j2;
layout (location = 4) out vec3 j3;
layout (location = 5) out vec3 j4;
layout (location = 6) out vec3 j5;
layout (location = 7) out vec3 j6;
layout (location = 8) out vec3 j7;
layout (location = 9) out vec3 j8;
layout (location = 10) out float transparency;
layout (location = 11) out float mode;
layout (location = 12) out float depth;
layout (location = 13) out vec4 trupos;
layout (location = 14) out float depth2;
layout (location = 15) flat out int clickCol;


uniform vec2 screenDimensions;
uniform vec2 offset;

uniform float buttx;
uniform float butty;
uniform float buttz;
uniform float neck;
uniform float chin;
uniform float uishrinker;
uniform float disable2d;
uniform float aspect;
uniform int clock;
uniform float zoom;

void main()
{
    j1 = c1;
    j2 = c2;
    j3 = c3;
    j4 = c4;
    j5 = c5;
    j6 = c6;
    j7 = c7;
    j8 = c8;
    transparency = trans;
    so = spriteoffset;
    clickCol = cc1;

    vec3 pos = position ;
    if(m != 5.0){
        pos -= vec3(buttx,butty,buttz);
    }
    {
        float dist = sqrt(pos.x*pos.x+pos.z*pos.z);
        float angle = atan(pos.z, pos.x);
        angle += neck;
        pos.x = dist*cos(angle);
        pos.z = dist*sin(angle);
        depth = dist;
    }
    {
        float dist = sqrt(pos.y*pos.y+pos.z*pos.z);
        float angle = atan(pos.y, pos.z);
        angle += chin;
        pos.y = dist*sin(angle);
        pos.z = dist*cos(angle);
    }
    
    st = textureMap;
    mode = m;
    float zfar = 200000;
    float znear = 0.1;
    float z= pos.z; 
    float fovAngle = 120; // initial field of view angle in degrees
    // float zoomFactor = 1 + clock/1000.0; // adjust this to zoom in or out
    float zoomFactor = 1.0 + zoom; // adjust this to zoom in or out
    float fov = tan(3.14159 * (fovAngle * zoomFactor) / 180 / 2);
    float idk = ((z*zfar-zfar*znear)/(zfar-znear));
    if(mode >= 1000){
        depth = 1.0;
        depth2 = 0.0;
        vec3 pos2 = position;
        pos2.x += 1000*disable2d;
        gl_Position = vec4(pos2/uishrinker, 1.0);
    }else{
        gl_Position = vec4(pos.x/aspect*fov, pos.y*fov, idk, pos.z);
        depth2 = pos.z;
    }
    trupos = gl_Position;
    
}