
precision mediump float;
uniform sampler2D textureSampler;
varying vec4 frag_position;
varying vec2 frag_uv;

void main() {
    gl_FragColor = texture2D(textureSampler, frag_uv);
}
