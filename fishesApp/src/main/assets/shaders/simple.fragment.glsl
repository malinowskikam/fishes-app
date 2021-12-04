
precision mediump float;
uniform vec4 color;
varying vec4 frag_position;
varying vec2 frag_uv;

void main() {
    gl_FragColor = color + vec4(abs(frag_uv.x), abs(frag_uv.y), 0.5, 0.0);
}
