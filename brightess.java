package FINAL;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class brightess {
	public static void main(String[] args) throws IOException{
		File file = new File("image/input.jpg");
        int[][] input = imageToMatrix(file);
        int[][] output = new int[input.length-2][input[0].length-2];

        int[][] kernel = {
                {0, 0, 0},
                {0, 1, 0},
                {0, 0, 0}
        };
		
        conv(input, output, kernel, input.length, input[0].length);
        matrixToImage(output);

    }


    public static void conv (int[][] input, int[][] output, int[][] mask, int N, int M) {
        int level = 10;
        for (int i = 0; i < mask.length; i++) {
            for(int j = 0; j < mask[0].length; j++) {
                level += mask[i][j];
            }
        }
        for (int i = 1; i <= N-3; i++){
            for(int j = 1; j <= M-3; j++) {
                output[i][j] = ((input[i-1][j-1]* mask[0][0] + level) +
                				(input[i-1][j]	* mask[0][1] + level) +
                				(input[i-1][j+1]* mask[0][2] + level) +
                                (input[i][j-1]	* mask[1][0] + level) +
                                (input[i][j]	* mask[1][1] + level) +
                                (input[i][j+1]	* mask[1][2] + level) +
                                (input[i+1][j-1]* mask[2][0] + level) +
                                (input[i+1][j]	* mask[2][1] + level) +
                                (input[i+1][j+1]* mask[2][2] + level));
            }
        }
    }

    public static int[][] imageToMatrix (File file) throws IOException {
        BufferedImage input = ImageIO.read(file);
        Color[][] pixel = new Color[input.getWidth()][input.getHeight()];
        int[][] output = new int [input.getWidth()][input.getHeight()];

        for (int x = 0; x < input.getWidth(); x++) {
            for (int y = 0; y < input.getHeight(); y++) {
            	pixel[y][x] = new Color(input.getRGB(x,y));
            }
        }

        for (int i = 0; i < pixel.length; i++) {
            for (int j = 0; j < pixel[i].length; j++) {
            	output[i][j] = (pixel[i][j].getRed() + pixel[i][j].getGreen() + pixel[i][j].getBlue())/3;
            }
        }

        return output;
    }

    public static void matrixToImage (int[][] input) throws IOException {
        BufferedImage image = new BufferedImage(input.length, input[0].length, BufferedImage.TYPE_BYTE_GRAY);
        for (int i = 0; i < input.length; i++) {
            for (int j = 0; j < input[0].length; j++) {
                int a = input[i][j];               
                if (a > 255) {
                	a = 255;
                } else if (a < 0) {
                	a = 0;
                }
                Color newColor = new Color (a,a,a);
                image.setRGB(j ,i, newColor.getRGB());
            }
        }

        File output = new File("image/output_birghtned.jpg");
        ImageIO.write(image, "jpg", output);
    }

}
