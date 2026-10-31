#version 150
uniform sampler2D InSampler;
uniform float GameTime;
in vec2 texCoord;
out vec4 fragColor;

float hash1(vec3 p) { return fract(sin(dot(p,vec3(127.1,311.7,74.7)))*43758.5453123); }
vec3 hsv2rgb(vec3 c) {
 vec3 p = abs(fract(c.xxx+vec3(0.,2./3.,1./3.))*6.-3.);
 return c.z*mix(vec3(1.),clamp(p-1.,0.,1.),c.y);
}
void main() {
 vec4 src=texture(InSampler,texCoord);
 vec3 rgb=src.rgb;
 float gray=dot(rgb,vec3(0.299,0.587,0.114));
 // GameTime represents the world-day fraction (24000 ticks).
 // Forty 600-tick cycles per Minecraft day, hard resetting every cycle.
 float epoch=floor(GameTime*40.0);
 float progress=fract(GameTime*40.0);
 // Coarse quantization ensures the same source color has the same color outcome.
 vec3 key=floor(rgb*12.0+0.5);
 float groupSeed=hash1(key+vec3(epoch*2.3));
 bool original=groupSeed<0.45;
 vec3 randomColor=hsv2rgb(vec3(hash1(key+vec3(epoch*3.7+11.0)),0.88,max(gray,0.25)));
 // Spatial coverage (not number of groups) determines which screen pixels colorize.
 // Jittered uniform coverage; this is an estimate, not exact whole-world coverage.
 float coverage=hash1(vec3(floor(gl_FragCoord.xy/2.0),epoch));
 bool colored=coverage<progress;
 vec3 result=colored?(original?rgb:randomColor):vec3(gray);
 fragColor=vec4(result,src.a);
}
