/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package image.processing;

import UI.ControlFrame;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import static java.lang.Math.ceil;
import java.util.Arrays;

/**
 *
 * @author Gayan Sandaruwan
 */
public class ImageProcessor {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        ControlFrame cf = new ControlFrame();
        cf.setVisible(true);
        // TODO code application logic here
    }

    public BufferedImage rotateClock(BufferedImage image) {

        System.out.println("Rotating Iamge Clock Wise... ");
        System.out.println("Recieced Image  : " + image.getData());
        int length = image.getWidth();
        int height = image.getHeight();
        BufferedImage nImage = new BufferedImage(height, length, image.getType());

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < length; x++) {
                nImage.setRGB(height - y - 1, x, image.getRGB(x, y));
            }
        }
        System.out.println("Returning Image  : " + nImage.getData());

        return nImage;
    }

    public BufferedImage rotateAntiClock(BufferedImage image) {
        System.out.println("Rotating Iamge Anti Clock Wise.. ");
        System.out.println("Recieced Image  : " + image.getData());
        int length = image.getWidth();
        int height = image.getHeight();
        BufferedImage nImage = new BufferedImage(height, length, image.getType());

        for (int x = 0; x < length; x++) {
            for (int y = 0; y < height; y++) {
                nImage.setRGB(y, length - x - 1, image.getRGB(x, y));
            }
        }

        System.out.println("Returning Image   : " + nImage.getData());
        return nImage;

    }

    public BufferedImage flipImage(BufferedImage image) {

        System.out.println("Flipping Iamge...");
        System.out.println("Recieced Image  : " + image.getData());
        int length = image.getWidth();
        int height = image.getHeight();
        BufferedImage nImage = new BufferedImage(length, height, image.getType());

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < length; x++) {
                nImage.setRGB(length - x - 1, y, image.getRGB(x, y));
            }
        }

        System.out.println("Returning Image   : " + nImage.getData());
        return nImage;

    }

    public BufferedImage zoomInNN(int value, BufferedImage image) {
        System.out.println("Zooming IN x 2 ^" + value + " ...");

        BufferedImage nImage = image;
        for (int i = 0; i < value; i++) {

            int length = nImage.getWidth();
            int height = nImage.getHeight();
            BufferedImage nImageCopy = new BufferedImage(nImage.getWidth(), nImage.getHeight(), nImage.getType()); // Clonong nImage into nImageCopy
            Graphics g = nImageCopy.getGraphics();
            g.drawImage(nImage, 0, 0, null);
            g.dispose();

            nImage = new BufferedImage(length * 2, height * 2, nImage.getType());
            int counter1 = 0;
            int counter2 = 0;
            for (int x = 0; x < length; x++) {
                for (int y = 0; y < height; y++) {
                    nImage.setRGB(x + counter1, y + counter2, nImageCopy.getRGB(x, y));
                    nImage.setRGB(x + counter1, y + 1 + counter2, nImageCopy.getRGB(x, y));
                    nImage.setRGB(x + 1 + counter1, y + counter2, nImageCopy.getRGB(x, y));
                    nImage.setRGB(x + 1 + counter1, y + 1 + counter2, nImageCopy.getRGB(x, y));
                    counter2++;
                }
                counter2 = 0;
                counter1++;
            }
        }
        return nImage;
    }

    public BufferedImage zoomOutNN(int value, BufferedImage image) {
        System.out.println("Zooming Out x 2 ^" + value + " ...");
        BufferedImage nImage = image;
        for (int i = 0; i < -value; i++) {

            int length = nImage.getWidth();
            int height = nImage.getHeight();
            BufferedImage nImageCopy = new BufferedImage(nImage.getWidth(), nImage.getHeight(), nImage.getType()); // Clonong nImage into nImageCopy
            Graphics g = nImageCopy.getGraphics();
            g.drawImage(nImage, 0, 0, null);
            g.dispose();

            nImage = new BufferedImage((int) ceil(length / 2), (int) ceil(height / 2), image.getType());
            int counter1 = 0;
            int counter2 = 0;
            for (int x = 0; x < (int) ceil(length / 2); x++) {
                for (int y = 0; y < (int) ceil(height / 2); y++) {
                    nImage.setRGB(x, y, nImageCopy.getRGB(counter1, counter2));
                    counter2 = counter2 + 2;
                }
                counter2 = 0;
                counter1 = counter1 + 2;
            }
        }
        return nImage;
    }

    public BufferedImage negotiveImg(BufferedImage image) {

        int length = image.getWidth();
        int height = image.getHeight();
        BufferedImage nImage = new BufferedImage(length, height, image.getType());
        for (int x = 0; x < length; x++) {
            for (int y = 0; y < height; y++) {
                Color color = new Color(image.getRGB(x, y));
                int R, G, B;
                R = checkColorRange(255 - color.getRed());
                G = checkColorRange(255 - color.getGreen());
                B = checkColorRange(255 - color.getBlue());
                color = new Color(R, G, B);
                nImage.setRGB(x, y, color.getRGB());
            }
        }
        return nImage;
    }

    private int checkColorRange(int newColor) {
        if (newColor > 255) {
            newColor = 255;
        } else if (newColor < 0) {
            newColor = 0;
        }
        return newColor;
    }

    public BufferedImage toGrayScale(BufferedImage image) {

        int length = image.getWidth();
        int height = image.getHeight();
        BufferedImage nImage = new BufferedImage(length, height, image.getType());
        for (int x = 0; x < length; x++) {
            for (int y = 0; y < height; y++) {
                int rgb = image.getRGB(x, y);
                int A = (rgb >> 24) & 0xFF;
                int R = (rgb >> 16) & 0xFF;
                int G = (rgb >> 8) & 0xFF;
                int B = (rgb & 0xFF);
                int gray = (R + G + B) / 3;
                rgb = (A << 24) | (gray << 16) | (gray << 8) | gray;
                nImage.setRGB(x, y, rgb);
            }
        }

        return nImage;
    }

    public BufferedImage brightnessInc(int slideValue, BufferedImage image) {

        int length = image.getWidth();
        int height = image.getHeight();
        BufferedImage nImage = image;
        BufferedImage nImageCopy = new BufferedImage(nImage.getWidth(), nImage.getHeight(), nImage.getType()); // Clonong nImage into nImageCopy
        Graphics g = nImageCopy.getGraphics();
        g.drawImage(nImage, 0, 0, null);
        g.dispose();
        for (int i = 0; i < slideValue; i++) {

            for (int x = 0; x < length; x++) {
                for (int y = 0; y < height; y++) {
                    Color color = new Color(nImage.getRGB(x, y));

                    int R, G, B;

                    R = checkColorRange(color.getRed() + 10);
                    G = checkColorRange(color.getGreen() + 10);
                    B = checkColorRange(color.getBlue() + 10);

                    color = new Color(R, G, B);
                    nImageCopy.setRGB(x, y, color.getRGB());

                }
                nImage = nImageCopy;
            }
        }
        return nImage;
    }

    public BufferedImage brightnessDec(int slideValue, BufferedImage image) {
        int length = image.getWidth();
        int height = image.getHeight();
        BufferedImage nImage = image;
        BufferedImage nImageCopy = new BufferedImage(nImage.getWidth(), nImage.getHeight(), nImage.getType()); // Clonong nImage into nImageCopy
        Graphics g = nImageCopy.getGraphics();
        g.drawImage(nImage, 0, 0, null);
        g.dispose();
        for (int i = 0; i < -slideValue; i++) {

            for (int x = 0; x < length; x++) {
                for (int y = 0; y < height; y++) {
                    Color color = new Color(nImage.getRGB(x, y));

                    int R, G, B;

                    R = checkColorRange(color.getRed() - 10);
                    G = checkColorRange(color.getGreen() - 10);
                    B = checkColorRange(color.getBlue() - 10);

                    color = new Color(R, G, B);
                    nImageCopy.setRGB(x, y, color.getRGB());
                }

            }
            nImage = nImageCopy;
        }
        return nImage;
    }

    public BufferedImage contrastInc(int slideValue, BufferedImage image) {

        int length = image.getWidth();
        int height = image.getHeight();
        BufferedImage nImage = image;
        BufferedImage nImageCopy = new BufferedImage(nImage.getWidth(), nImage.getHeight(), nImage.getType()); // Clonong nImage into nImageCopy
        Graphics g = nImageCopy.getGraphics();
        g.drawImage(nImage, 0, 0, null);
        g.dispose();
        for (int i = 0; i < slideValue; i++) {

            int min = nImage.getRGB(0, 0);
            int max = nImage.getRGB(0, 0);
            for (int x = 0; x < length; x++) {
                for (int y = 0; y < height; y++) {
                    int rgb = nImage.getRGB(x, y);
                    if (min > rgb) {
                        min = rgb;
                    }
                    if (max < rgb) {
                        max = rgb;
                    }
                }
            }
            int avg = (int) (min + max) / 2;
            for (int x = 0; x < length; x++) {
                for (int y = 0; y < height; y++) {
                    if (nImage.getRGB(x, y) < avg) {
                        Color color = new Color(nImage.getRGB(x, y));
                        int R, G, B;
                        R = checkColorRange(color.getRed() - 10);
                        G = checkColorRange(color.getGreen() - 10);
                        B = checkColorRange(color.getBlue() - 10);
                        color = new Color(R, G, B);
                        nImageCopy.setRGB(x, y, color.getRGB());
                    }
                    if (image.getRGB(x, y) > avg) {
                        Color color = new Color(nImage.getRGB(x, y));
                        int R, G, B;
                        R = checkColorRange(color.getRed() + 10);
                        G = checkColorRange(color.getGreen() + 10);
                        B = checkColorRange(color.getBlue() + 10);
                        color = new Color(R, G, B);
                        nImageCopy.setRGB(x, y, color.getRGB());
                    }
                }
            }
            nImage = nImageCopy;
        }
        return nImage;
    }

    public BufferedImage contrastDec(int slideValue, BufferedImage image) {

        int length = image.getWidth();
        int height = image.getHeight();
        BufferedImage nImage = image;
        BufferedImage nImageCopy = new BufferedImage(nImage.getWidth(), nImage.getHeight(), nImage.getType()); // Clonong nImage into nImageCopy
        Graphics g = nImageCopy.getGraphics();
        g.drawImage(nImage, 0, 0, null);
        g.dispose();
        for (int i = 0; i < -slideValue; i++) {

            int min = nImage.getRGB(0, 0);
            int max = nImage.getRGB(0, 0);
            for (int x = 0; x < length; x++) {
                for (int y = 0; y < height; y++) {
                    int rgb = nImage.getRGB(x, y);
                    if (min > rgb) {
                        min = rgb;
                    }
                    if (max < rgb) {
                        max = rgb;
                    }
                }
            }
            int avg = (int) (min + max) / 2;
            for (int x = 0; x < length; x++) {
                for (int y = 0; y < height; y++) {
                    if (nImage.getRGB(x, y) < avg) {
                        Color color = new Color(nImage.getRGB(x, y));
                        int R, G, B;
                        R = checkColorRange(color.getRed() + 10);
                        G = checkColorRange(color.getGreen() + 10);
                        B = checkColorRange(color.getBlue() + 10);
                        color = new Color(R, G, B);
                        nImageCopy.setRGB(x, y, color.getRGB());
                    }
                    if (image.getRGB(x, y) > avg) {
                        Color color = new Color(nImage.getRGB(x, y));
                        int R, G, B;
                        R = checkColorRange(color.getRed() - 10);
                        G = checkColorRange(color.getGreen() - 10);
                        B = checkColorRange(color.getBlue() - 10);
                        color = new Color(R, G, B);
                        nImageCopy.setRGB(x, y, color.getRGB());
                    }
                }
            }
            nImage = nImageCopy;
        }
        return nImage;
    }

    public BufferedImage MeanFilter(BufferedImage image) {

        double[][] mask = new double[][]{{1, 1, 1}, {1, 2, 1}, {1, 1, 1}};
        double[][] mask_gaussian = new double[][]{{1, 2, 1}, {2, 4, 2}, {1, 2, 1}};
        int factor = 10;

        int length = image.getWidth();
        int height = image.getHeight();
        BufferedImage nImage = new BufferedImage(length, height, image.getType());
        Graphics g = nImage.createGraphics();
        g.dispose();

        for (int x = 0; x < height - 2; x++) {
            for (int y = 0; y < length - 2; y++) {

                int R = 0;
                int G = 0;
                int B = 0;
                for (int n = 0; n < 3; n++) {
                    for (int m = 0; m < 3; m++) {
                        Color color = new Color(image.getRGB(y + n, x + m));
                        R += mask[m][n] * color.getRed();
                        G += mask[m][n] * color.getGreen();
                        B += mask[m][n] * color.getBlue();
                    }
                }
                R = checkColorRange((int) (R / factor));
                G = checkColorRange((int) (G / factor));
                B = checkColorRange((int) (B / factor));
                Color color = new Color((int) R, (int) G, (int) B);
                nImage.setRGB(y + 1, x + 1, color.getRGB());
            }
        }
        return nImage;
    }

    public BufferedImage medianFilter(BufferedImage image) {

        int length = image.getWidth();
        int height = image.getHeight();
        BufferedImage nImage = new BufferedImage(length, height, image.getType());
        for (int x = 0; x < length - 2; x++) {
            for (int y = 0; y < height - 2; y++) {
                int[] R = new int[9];
                int[] G = new int[9];
                int[] B = new int[9];
                int counter = 0;
                for (int m = 0; m < 3; m++) {
                    for (int n = 0; n < 3; n++) {
                        Color color = new Color(image.getRGB(x + m, y + n));
                        R[counter] = color.getRed();
                        G[counter] = color.getGreen();
                        B[counter] = color.getBlue();
                        counter++;
                    }
                }
                Arrays.sort(R);
                Arrays.sort(G);
                Arrays.sort(B);
                int nR = checkColorRange(R[4]);
                int nG = checkColorRange(G[4]);
                int nB = checkColorRange(B[4]);
                Color nColor = new Color(nR, nG, nB);
                nImage.setRGB(x + 1, y + 1, nColor.getRGB());

            }
        }
        return nImage;
    }

    public BufferedImage GaussianFilter(BufferedImage image) {

        double[][] mask = new double[][]{{1, 2, 1}, {2, 4, 2}, {1, 2, 1}};
        int factor = 16;
        int length = image.getWidth();
        int height = image.getHeight();
        BufferedImage nImage = new BufferedImage(length, height, image.getType());
        for (int x = 0; x < height - 2; x++) {
            for (int y = 0; y < length - 2; y++) {

                int R = 0;
                int G = 0;
                int B = 0;
                for (int n = 0; n < 3; n++) {
                    for (int m = 0; m < 3; m++) {
                        Color color = new Color(image.getRGB(y + n, x + m));
                        R += mask[m][n] * color.getRed();
                        G += mask[m][n] * color.getGreen();
                        B += mask[m][n] * color.getBlue();
                    }
                }
                R = checkColorRange((int) (R / factor));
                G = checkColorRange((int) (G / factor));
                B = checkColorRange((int) (B / factor));
                Color color = new Color((int) R, (int) G, (int) B);
                nImage.setRGB(y + 1, x + 1, color.getRGB());
            }
        }
        return nImage;
    }

    public BufferedImage sobelFilter(BufferedImage image) {

        double[][] horizontal = new double[][]{{1, 0, -1}, {2, 0, -2}, {1, 0, -1}};
        double[][] vertical = new double[][]{{1, 2, 1}, {0, 0, 0}, {-1, -2, -1}};

        int length = image.getWidth();
        int height = image.getHeight();
        BufferedImage nImage = new BufferedImage(length, height, image.getType());
        for (int x = 0; x < length - 2; x++) {
            for (int y = 0; y < height - 2; y++) {

                int R1 = 0, R2 = 0;
                int G1 = 0, G2 = 0;
                int B1 = 0, B2 = 0;
                for (int n = 0; n < 3; n++) {
                    for (int m = 0; m < 3; m++) {
                        Color color = new Color(image.getRGB(x + n, y + m));
                        R1 += horizontal[m][n] * color.getRed();
                        G1 += horizontal[m][n] * color.getGreen();
                        B1 += horizontal[m][n] * color.getBlue();
                    }
                }
                R1 = checkColorRange(R1);
                G1 = checkColorRange(G1);
                B1 = checkColorRange(B1);

                for (int n = 0; n < 3; n++) {
                    for (int m = 0; m < 3; m++) {
                        Color color = new Color(image.getRGB(x + n, y + m));
                        R2 += vertical[m][n] * color.getRed();
                        G2 += vertical[m][n] * color.getGreen();
                        B2 += vertical[m][n] * color.getBlue();
                    }
                }
                R2 = checkColorRange(R2);
                G2 = checkColorRange(G2);
                B2 = checkColorRange(B2);

                int nR = (int) Math.pow(Math.pow(R1, 2.0) + Math.pow(R2, 2.0), 0.5);
                int nG = (int) Math.pow(Math.pow(G1, 2.0) + Math.pow(G2, 2.0), 0.5);
                int nB = (int) Math.pow(Math.pow(B1, 2.0) + Math.pow(B2, 2.0), 0.5);

                nR = checkColorRange(nR);
                nG = checkColorRange(nG);
                nB = checkColorRange(nB);

                Color color = new Color(nR, nG, nB);
                nImage.setRGB(x + 1, y + 1, color.getRGB());
            }
        }
        return nImage;
    }

    public BufferedImage robertFilter(BufferedImage image) {
        double[][] horizontal = new double[][]{{0, 0, 0}, {0, 1, 0}, {0, 0, -1}};
        double[][] vertical = new double[][]{{0, 0, 0}, {0, 0, 1}, {0, -1, 0}};

        int length = image.getWidth();
        int height = image.getHeight();
        BufferedImage nImage = new BufferedImage(length, height, image.getType());
        for (int x = 0; x < length - 2; x++) {
            for (int y = 0; y < height - 2; y++) {

                int R1 = 0, R2 = 0;
                int G1 = 0, G2 = 0;
                int B1 = 0, B2 = 0;
                for (int n = 0; n < 3; n++) {
                    for (int m = 0; m < 3; m++) {
                        Color color = new Color(image.getRGB(x + n, y + m));
                        R1 += horizontal[m][n] * color.getRed();
                        G1 += horizontal[m][n] * color.getGreen();
                        B1 += horizontal[m][n] * color.getBlue();
                    }
                }
                R1 = checkColorRange(R1);
                G1 = checkColorRange(G1);
                B1 = checkColorRange(B1);

                for (int n = 0; n < 3; n++) {
                    for (int m = 0; m < 3; m++) {
                        Color color = new Color(image.getRGB(x + n, y + m));
                        R2 += vertical[m][n] * color.getRed();
                        G2 += vertical[m][n] * color.getGreen();
                        B2 += vertical[m][n] * color.getBlue();
                    }
                }
                R2 = checkColorRange(R2);
                G2 = checkColorRange(G2);
                B2 = checkColorRange(B2);

                int nR = (int) Math.pow(Math.pow(R1, 2.0) + Math.pow(R2, 2.0), 0.5);
                int nG = (int) Math.pow(Math.pow(G1, 2.0) + Math.pow(G2, 2.0), 0.5);
                int nB = (int) Math.pow(Math.pow(B1, 2.0) + Math.pow(B2, 2.0), 0.5);

                nR = checkColorRange(nR);
                nG = checkColorRange(nG);
                nB = checkColorRange(nB);

                Color color = new Color(nR, nG, nB);
                nImage.setRGB(x + 1, y + 1, color.getRGB());
            }
        }
        return nImage;
    }
    
    
public BufferedImage bilinearInterpolation(int newWidth, int newHeight, BufferedImage image) {

        BufferedImage nImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB); // creating the output_image
        int A, B, C, D, x, y, blue, green, red, p;
        float x_ratio = ((float) (image.getWidth() - 1)) / newWidth;
        float y_ratio = ((float) (image.getHeight() - 1)) / newHeight;
        float x_diff, y_diff;
        for (int i = 0; i < newHeight; i++) {
            for (int j = 0; j < newWidth; j++) {
                x = (int) (x_ratio * j);
                y = (int) (y_ratio * i);
                x_diff = (x_ratio * j) - x;
                y_diff = (y_ratio * i) - y;

                // range is 0 to 255 thus bitwise AND with 0xff
                A = image.getRGB(x, y);
                B = image.getRGB(x + 1, y);
                C = image.getRGB(x, y + 1);
                D = image.getRGB(x + 1, y + 1);

                // Y = A(1-w)(1-h) + B(w)(1-h) + C(h)(1-w) + Dwh
                blue = (int) ((A & 0xff) * (1 - x_diff) * (1 - y_diff) + (B & 0xff) * (x_diff) * (1 - y_diff)
                        + (C & 0xff) * (y_diff) * (1 - x_diff) + (D & 0xff) * (x_diff * y_diff));

                green = (int) (((A >> 8) & 0xff) * (1 - x_diff) * (1 - y_diff) + ((B >> 8) & 0xff) * (x_diff) * (1 - y_diff)
                        + ((C >> 8)& 0xff) * (y_diff) * (1 - x_diff) + ((D >> 8)& 0xff) * (x_diff * y_diff));

                red = (int) (((A >> 16) & 0xff) * (1 - x_diff) * (1 - y_diff) + ((B >> 16) & 0xff) * (x_diff) * (1 - y_diff)
                        + ((C >> 16) & 0xff) * (y_diff) * (1 - x_diff) + ((D >> 16) & 0xff) * (x_diff * y_diff));

                int a = (A >> 24) & 0xff;
                p = (a << 24) | (red << 16) | (green << 8)| blue;

                nImage.setRGB(j, i, p);
            }
        }
        return nImage;
}

}
