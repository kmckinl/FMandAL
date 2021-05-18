package com.yelink.fmandal.font;

import java.util.Map;

import com.yelink.fmandal.rendering.Texture;

public class Font {
    /*
     * An individual font to be used for text rendering
     *
     * Stores a Map of all the characters in the Font and the glyph sheet
     */

    private static final String STANDARD_FONT = "res/bangers.fnt";
    private static final String STANDARD_GLYPH = "res/bangers.png";

    private String fontName;

    private FontLoader font;
    private Texture glyph;

    public Font(String name, String fontPath, String glyphPath) {
        this.fontName = name;

        if (fontPath.contentEquals(" ")) { this.fontName = STANDARD_FONT; }
        else { font = new FontLoader(fontPath); }

        glyph = new Texture(glyphPath);
    }

    /* -- -- -- GETTERS / SETTERS -- -- -- */
    public Map<Integer, FontChar> getCharMap() {
        return font.getCharacters();
    }

    public String getName() {
        return this.fontName;
    }

    public Texture getTexture() {
        return this.glyph;
    }
}
