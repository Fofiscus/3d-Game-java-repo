/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg3dgame.graphics;

import java.util.Random;

/**
 *
 * @author rreid
 */
public class Screen extends Render {

    private Render test;

    public Screen(int width, int height) {
        super(width, height);
        Random random = new Random();
        test = new Render(256, 256);
        for (int i = 0; i < 256 * 256; i++) {
            test.pixels[i] = random.nextInt() *(random.nextInt(5)/4);
        }
    }

    public void render() {
        for (int i = 0; i < width * height; i++){
            pixels[i] = 0;
        }
        
        
        int anim = 0;
        int anim2 = 0;

        for (int i = 0; i < 50; i++) {
            anim = (int) (Math.sin((System.currentTimeMillis()+i * 2.5 )% 2000.0 / 2000 * Math.PI * 2) * 100);
            anim2 = (int) (Math.cos((System.currentTimeMillis()+i * 2.5 )% 2000.0 / 2000 * Math.PI * 2) * 100);
        
        draw(test, (width - 256) / 2 + anim, (height - 256) / 2 - anim2);
    }
    }
}
