#version 150
uniform sampler2D InSampler;
in vec2 texCoord;
out vec4 fragColor;

void main() {
    vec4 source = texture(InSampler, texCoord);
    // DIAGNOSTIC ONLY: visually unmistakable magenta-tinted grayscale.
    // No timing, no randomization, no restoration, no player death.
    float gray = dot(source.rgb, vec3(0.299, 0.587, 0.114));
    fragColor = vec4(gray * 0.85 + 0.15, gray * 0.12, gray * 0.85 + 0.15, source.a);
}
