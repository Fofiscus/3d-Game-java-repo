/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg3dgame.graphics;



/**
 *
 * @author rreid
 */
public class Render {

    private Render test;

    public final int width;
    public final int height;
    public final int[] pixels;
    /*late night thought (LNT), this pixel array solution would make an awesome
     way to handle a dungeon generator made of pixels*/
  

    public Render(int width, int height) {
        this.width = width;
        this.height = height;
        pixels = new int[width * height];
        //FYI the result is 480,000 pixels
    }

    public void draw(Render render, int xOffset, int yOffset) {
        for (int y = 0; y < render.height; y++) {
            int yPix = y + yOffset;
            if (yPix <0 || yPix >= height) {
            continue;
            }
            
            
            
            for (int x = 0; x < render.width; x++) {
                int xPix = x + xOffset;
                if (xPix <0 || xPix >= width) {
                    continue;
                }
                
                int alpha = render.pixels[ x + y * render.width];
                if (alpha >0){
                pixels[xPix + yPix * width] = alpha;
                }
            }
        }
    }

}
