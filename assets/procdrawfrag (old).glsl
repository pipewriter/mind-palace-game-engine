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

uniform float imprintingDistance;

uniform vec3 fogFlavor;
uniform vec3 skyFlavor;
uniform vec3 watFlavor;

uniform float sinflavor[30];

uniform vec2 u_textureSize;
uniform int clickTrack;


// uniform vec4 sinDistortionFlavor;

uniform int pqimode;

int about(float a, float b){
    if(a-b<0.001 && b-a<0.001){
        return 1;
    }
    return 0;
}
float random(vec2 st) {
    return fract(sin(dot(st.xy, vec2(12.9898,78.233))) * 43758.5453);
}
void main(){

    // float spots[] = float[]{  1,1,0.5 };
    // float[] spots = float[3];
    vec2 seed = vec2(clock, clock + 1.0);
    //  const float spots[6] = float[6](1,1,0.5, -1,0,0.5);







//     float spots[9] = float[9](0);
     float spots[9];

        // random(seed+0),
        // random(seed+1),
        // random(seed+2),
        // random(seed+3),
        // random(seed+4),
        // random(seed+5),
        // random(seed+6),
        // random(seed+7),
        // random(seed+8)
        // );
    spots[0] = -0.5;
    spots[1] = -0.5;
    spots[2] = 0.7;
    for(int i = 0; i < 3; i+=3){
        spots[i+0] = 2*random(seed+i+0)-1;
        spots[i+1] = 2*random(seed+i+1)-1;
        spots[i+2] =  (random(seed+i+2));
    }
    // spots[2] = (random(seed+0+2)+1)/5;
    
    float spriteoffset = floor(so*1.0001);
    //spriteoffset = 0;
    float stx = st.x;
    float sty = st.y;
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
    }else
    if(about(m,99)==1){

        if(stx>0.98 || stx < 0.02 || sty > 0.98 || sty < 0.02){
            // colorFromPic = vec4(184/255.0,30/255.0,197/255.0, 1.0);
            colorFromPic = vec4(5/255.0,255/255.0,144/255.0, 1.0);
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
        // color += (depth/500.0);

        // color += (depth/5000.0)*(vec4(0.9) + vec4(0.1) * vec4(fogFlavor, 1.0));
        // color += (depth/5000.0)*(vec4(0.9) + vec4(0.1) * vec4(1.0,0.0,1.0,1.0));
        if(m < 999){
            color += min( depth/500.0, 0.6) * vec4(fogFlavor,1.0);
        }
        // color += depth/500.0 * vec4(fogFlavor,1.0);
        // color += depth;
        // color.r = 1;

    }
    color = min(color, 1.0);
    if(about(m, 56) == 1 && about(spriteoffset, 12) == 1){ // assumed to be water
        color *= vec4(watFlavor,1.0);
    }
    // about(m,8)==1
    if(about(m, 55) == 1){ // assumed to be water
        color *= vec4(skyFlavor,1.0);
    }

    
    // color.g = 1;
    vec4 tp = trupos/depth2;
    {
        // tp*=2;
        // tp-=0.5;
        tp/=4;
        // tp*=max(imprintingDistance, 0.1);
        tp*=imprintingDistance;
        tp.y = -tp.y;
        tp.x+= 0.5;
        tp.y+= 0.5;
        // tp.x-=0.5;
        // tp.x = max(tp.x, 0);
        // tp.x = min(tp.x, 1);
        // tp.y+=1;
        // tp.y+=0.5;
        // tp.y = max(tp.y, 0);
        // tp.y = min(tp.y, 1);

    }
    // tp-=vec4(0.5);

    // {
    //     tp*=16;
    // }
    // if(u_textureSize.x < 0.5){
    //                 color = vec4(1)-color;
    //                 color.a = 1;
        
    //         // if(!(tp.x < 0 || tp.x > 1 || tp.y < 0 || tp.y > 1)){

    //         //     vec4 inversionMask = texture(colorInversionMask, tp.xy);
    //         //     // color+=inversionMask;
    //         //     if(inversionMask.b > 0.5){
    //         //         // color = mod(vec4(0.5)+color,1);
    //         //         // color.a = 1;
    //         //     }
    //         // }
    // }
    if(m < 999){

        if(pqimode == 1) {

            /*

            WHAT THE FUCK IS THIS SHIT

            FUCK ALL THIS SHIT


            */
            // float v1 = 0 + clock/1000.0;
            // float v2 = sinflavor[0]*clock/1000.0*6.28;
            // float v3 = sinflavor[1]*clock/1000.0*6.28;
            // float v4 = sinflavor[2]*clock/1000.0*6.28;
            // float v5 = sinflavor[3]*clock/1000.0*6.28;
            // float v6 = 0.6;
            
            // tp.x += sin(6.28*(2*v1 - 1)*(0.25)* + tp.y*(1+5*v2))/(4+4*v5);

            // tp.y += sin(6.28*(2*v3 - 1)*(0.25)* + tp.x*(1+5*v4))/(4+4*v6);

            tp.x += sin(6.28*clock/1000 + tp.y*(5*sinflavor[2]) )*sinflavor[0]/3;
            tp.y += sin(6.28*clock/1000 + tp.x*(5*sinflavor[3]) )*sinflavor[1]/3;
            // tp.y += ;



            if(!(tp.x < 0 || tp.x > 1 || tp.y < 0 || tp.y > 1)){

                vec4 inversionMask = texture(colorInversionMask, tp.xy);
                // color+=inversionMask;
                if(inversionMask.b > 0.5){
                    color = vec4(1)-color;
                    color.a = 1;
                    // color = mod(vec4(0.5)+color,1);
                    // color.a = 1;
                }
            }

        }
        float aspect = u_textureSize.x/u_textureSize.y;
        tp-=0.5;
        tp.x/=aspect;
        tp+=0.5;
        // tp.x += sin(6.28*clock/1000 + tp.y*(5*sinflavor[2]) )*sinflavor[0]/5;
        // tp.y += sin(6.28*clock/1000 + tp.x*(5*sinflavor[3]) )*sinflavor[1]/5;

        if(pqimode == 0 && !(tp.x < 0 || tp.x > 1 || tp.y < 0 || tp.y > 1) ) {

            vec4 inversionMask = texture(colorInversionMask, tp.xy);
            // color+=inversionMask;
            // if(inversionMask.a > 0.01){
            //     // color = inversionMask-color;
            //     // inversionMask = min(inversionMask, 0.9);
            //     // color = color-inversionMask;
            //     color = mod(color+inversionMask,1);
                
            //     // if(color.r < 0) color.r = - color.r;
            //     // if(color.g < 0) color.g = - color.g;
            //     // if(color.b < 0) color.b = - color.b;
            //     color.a = 1;
            // }
            // color+=inversionMask;
            // if(inversionMask.a > 0.01){
            //     // color = inversionMask-color;
            //     inversionMask = min(inversionMask, 0.9);
            //     color = color-inversionMask;
            //     // color = color-inversionMask;
            //     if(color.r < 0) color.r = - color.r;
            //     if(color.g < 0) color.g = - color.g;
            //     if(color.b < 0) color.b = - color.b;
            //     color.a = 1;
            // }
            float temp = inversionMask.r;
            inversionMask.r = inversionMask.b;
            inversionMask.b = temp;
            if(inversionMask.a > 0.01){
                // color = inversionMask-color;
                // inversionMask = min(inversionMask, 0.9);
                color = (color*0.4+inversionMask*0.8);
                // color.r = 1;
                color.a = 1;
            }
            
        }
    }
    if(clickTrack == 1)
    {
        int cint = clickCol;
        if(cint == -1){
            discard;
        }
        float r = ((cint<<0 )&255)/255.0;
        float g = ((cint>>8 )&255)/255.0;
        float b = ((cint>>16)&255)/255.0;
        color = vec4(r,g,b, 1.0);
    }
    // if(color == vec4(0)){
    //     color = vec4(1);
    // }
}   