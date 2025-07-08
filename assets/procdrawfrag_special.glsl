#version 410 core

layout (location = 0) in float so;
layout (location = 2) in vec2 st;
layout (location = 1) in vec3 j1;
layout (location = 3) in vec3 j2;
layout (location = 4) in vec3 j3;
layout (location = 5) in vec3 j4;
layout (location = 6) in vec3 j5;
layout (location = 7) in vec3 j6;
layout (location = 8) in vec3 j7;
layout (location = 9) in vec3 j8;
layout (location = 10) in float transparency;
layout (location = 11) in float m;
layout (location = 12) in float depth;
layout (location = 13) in vec4 trupos;
layout (location = 14) in float depth2;
layout (location = 15) flat in int clickCol;


layout (location = 0) out vec4 color;

uniform sampler2D colorInversionMask;
uniform sampler2D ourTexture;
uniform int sideLength;

uniform float buttx;
uniform float butty;
uniform float buttz;

uniform float cutout;
uniform float disable2d;
uniform int clock;


int about(float a, float b){
    if(a-b<0.001 && b-a<0.001){
        return 1;
    }
    return 0;
}
float random(vec2 st) {
    return fract(sin(dot(st.xy, vec2(12.9898,78.233))) * 43758.5453);
}
// float random(vec2 st) {
//     return fract(sin(dot(st.xy, vec2(12.9898,78.233))) * 43758.5453123);
// }
// float rand(vec2 co){
//     return fract(sin(dot(co, vec2(12.9898, 78.233))) * 43758.5453);
// }
vec2 dragPoint(vec2 point, vec2 circle1Center, float circle1Radius, vec2 circle2Center, float circle2Radius)
{
    vec2 dirFromCircle1 = point - circle1Center;
    float distFromCircle1 = length(dirFromCircle1);

    // If the point is inside the first circle
    if (distFromCircle1 <= circle1Radius)
    {
        // Calculate a direction vector from circle1 to circle2
        vec2 dirBetweenCircles = normalize(circle2Center - circle1Center);

        // Drag the point in the direction of the second circle
        // Here, a fraction (like 0.5) will determine how much the point is dragged towards circle2
        point += dirBetweenCircles * (circle1Radius * 0.5);
    }

    return point;
}
void main(){

    // float spots[] = float[]{  1,1,0.5 };
    // float[] spots = float[3];
    vec2 seed = vec2(clock, clock + 1.0);
    //  const float spots[6] = float[6](1,1,0.5, -1,0,0.5);





//     float spots[9] = float[9](0);
     float spots[9];

    spots[0] = -0.5;
    spots[1] = -0.5;
    spots[2] = 0.7;
    for(int i = 0; i < 3; i+=3){
        spots[i+0] = 2*random(seed+i+0)-1;
        spots[i+1] = 2*random(seed+i+1)-1;
        spots[i+2] =  (random(seed+i+2))*0.6+0.4;
    }
    spots[3] = random(seed+3+0);
    spots[4] = random(seed+3+1);
    spots[5] = random(seed+3+2);
    spots[6] = random(seed+6+0);
    spots[7] = random(seed+6+1);
    spots[8] = random(seed+6+2);
    // spots[2] = (random(seed+0+2)+1)/5;



    // vec2 tp = dragPoint(trupos.xy, vec2(spots[0],spots[1]),spots[2],vec2(spots[3],spots[4]),spots[5]);
    vec2 tp = trupos.xy;

    // tp.x += sin(6.28*(2*spots[5] - 1)*(0.25)* + tp.y*(1+5*spots[3]))/(4+4*spots[4]);

    // tp.y += sin(6.28*(2*spots[6] - 1)*(0.25)* + tp.x*(1+5*spots[7]))/(4+4*spots[8]);

    vec2 d = tp-trupos.xy;



    float spriteoffset = floor(so*1.0001);
    // spriteoffset = 1;
    float stx = st.x + d.x;
    float sty = st.y + d.y;
    stx = min(stx, 0.999);
    stx = max(stx, 0.001);
    sty = min(sty, 0.999);
    sty = max(sty, 0.001);
    int sideLengthValue = sideLength;
    // sideLengthValue = 1;
    float test = mod(spriteoffset*1.00001, sideLengthValue*1.0);
    vec4 colorFromPic = vec4(0);
    if(about(m,8)==1){

        if(stx>0.98 || stx < 0.02 || sty > 0.98 || sty < 0.02){
            colorFromPic = vec4(184/255.0,30/255.0,197/255.0, 1.0);
            // colorFromPic = vec4(30/255.0,197/255.0,184/255.0, 1.0);
        }else{
            vec2 adjustedTexelCoord = vec2((1-stx)+mod(spriteoffset, sideLengthValue), sty + floor(spriteoffset*1.0001/sideLengthValue))/sideLengthValue;
            colorFromPic = texture(ourTexture, adjustedTexelCoord);
        }
    }
    else if(m > 1000){
        // 0 to 1 -> it actually comes from the inside fyi
        // if(cutout == ){ 
        if(stx>0.93 || stx < 0.07 || sty > 0.93 || sty < 0.07){
            colorFromPic = vec4(184/255.0,30/255.0,197/255.0, 1.0);
            // colorFromPic = vec4(30/255.0,197/255.0,184/255.0, 1.0);
        }else{
            vec2 adjustedTexelCoord = vec2((1-stx)+mod(spriteoffset, sideLengthValue), sty + floor(spriteoffset*1.0001/sideLengthValue))/sideLengthValue;
            colorFromPic = texture(ourTexture, adjustedTexelCoord);
        }
    }
    else{
        { // thanks cahtgpt
            // cutout = 1.0-cutout;
            float ct = (1.0 - cutout) / 2;
            float marginX = ct;
            float marginY = ct;

            // Calculate the cutout area
            float cutoutLeft = marginX;
            float cutoutRight = 1.0 - marginX;
            float cutoutTop = marginY;
            float cutoutBottom = 1.0 - marginY;

            // Check if the stx and sty are within the cutout area
            if (stx > cutoutLeft && stx < cutoutRight && sty > cutoutTop && sty < cutoutBottom) {
                discard;
            }
        }

        vec2 adjustedTexelCoord = vec2((1-stx)+mod(spriteoffset, sideLengthValue), sty + floor(spriteoffset*1.000001/sideLengthValue))/sideLengthValue;
        colorFromPic = texture(ourTexture, adjustedTexelCoord);
    }

    // colorFromPic.a = 255;
    if(colorFromPic.a == 0){
        discard;
    }else
    {
        color = vec4(colorFromPic.bgr, transparency);
        if(
            floor(color.r*255.0)-j1.r<0.001 && j1.r-floor(color.r*255.0)<0.001 &&
            floor(color.g*255.0)-j1.g<0.001 && j1.g-floor(color.g*255.0)<0.001 &&
            floor(color.b*255.0)-j1.b<0.001 && j1.b-floor(color.b*255.0)<0.001
            ){
            color = vec4(j5/255.0, transparency);
        }
        else if(
            floor(color.r*255.0)-j2.r<0.001 && j2.r-floor(color.r*255.0)<0.001 &&
            floor(color.g*255.0)-j2.g<0.001 && j2.g-floor(color.g*255.0)<0.001 &&
            floor(color.b*255.0)-j2.b<0.001 && j2.b-floor(color.b*255.0)<0.001
            ){
            color = vec4(j6/255.0, transparency);
        }
        else if(
            floor(color.r*255.0)-j3.r<0.001 && j3.r-floor(color.r*255.0)<0.001 &&
            floor(color.g*255.0)-j3.g<0.001 && j3.g-floor(color.g*255.0)<0.001 &&
            floor(color.b*255.0)-j3.b<0.001 && j3.b-floor(color.b*255.0)<0.001
            ){
            color = vec4(j7/255.0, transparency);
        }
        else if(
            floor(color.r*255.0)-j4.r<0.001 && j4.r-floor(color.r*255.0)<0.001  &&
            floor(color.g*255.0)-j4.g<0.001 && j4.g-floor(color.g*255.0)<0.001  &&
            floor(color.b*255.0)-j4.b<0.001 && j4.b-floor(color.b*255.0)<0.001 
            ){
            color = vec4(j8/255.0, transparency);
        }
        // color += depth/5000.0;

    }

    // DON'T INVERT IT!
    // for(int i = 0; i < 3; i+=3){
    //     float x = spots[i+0];
    //     float y = spots[i+1];
    //     float size = spots[i+2];
    //     if(distance(tp,vec2(x,y)) < size){
    //         color = vec4(1)-color;
    //         color.a = 1;
    //     }
    // }
    // if(trupos.y < 0.002){
    //     color = vec4(1)-color;
    //     color.a = 1;
    // }
}   