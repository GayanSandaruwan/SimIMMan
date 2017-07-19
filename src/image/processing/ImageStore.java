/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package image.processing;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 *
 * @author Gayan Sandaruwan
 */
public class ImageStore {

    private int index = 0;
    private ArrayList<BufferedImage> imageStore;
    private BufferedImage initialImage;

    public ImageStore(BufferedImage image) {
        System.out.println("An new Image Store Was created");
        initialImage = image;
        imageStore = new ArrayList<BufferedImage>();
        imageStore.add(image);
    }

    public void addImage(BufferedImage image) {

        System.out.println("Image Added to the Image Store");
        imageStore.add(index, image);
        index++;
    }

    public BufferedImage getImageUndo() {

        try {
            index--;
            System.out.println("Successfully Image Is Being changed to backward version");

            return imageStore.get(index);

        } catch (Exception e) {
            index++;
            System.out.println("Undoing reached the last Set image");

            return initialImage;
        }

    }

    public BufferedImage getImageRedo() {

        try {
            System.out.println("Successfully Image is being changed to a forward version");

            index++;
            return imageStore.get(index);

        } catch (Exception e) {
            index--;
            System.out.println("Forwarding Image has reached the limit "+index);
            return imageStore.get(imageStore.lastIndexOf(imageStore)+1);
        }
    }

    public BufferedImage getInitialImage() {
        System.out.println("Initial Image is Set");
        return initialImage;
    }
}
