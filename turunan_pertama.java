package FINAL;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class turunan_pertama {
	public static void main(String[] args) throws IOException{
        File file = new File("image/input.jpg");
        int[][] input = imageToMatrix(file);
        int[][] output = new int[input.length-2][input[0].length-2];

        // SOBEL
        int[][] mask_x = {
                {-1, 0, 1},
                {-2, 0, 2},
                {-1, 0, 1}
        };
        
        int[][] mask_y = {
                { 1, 2, 1},
                { 0, 0, 0},
                {-1,-2,-1}
        };
        
        // Prewitt
//      int[][] mask_x = {
//              {-1, 0, 1},
//              {-1, 0, 1},
//              {-1, 0, 1}
//      };
//      
//      int[][] mask_y = {
//              { 1, 1, 1},
//              { 0, 0, 0},
//              {-1,-1,-1}
//      };
        
		
        conv(input, output, mask_x, mask_y, input.length, input[0].length);
        matrixToImage(output);

    }


    public static void conv (int[][] input, int[][] output, int[][] mask_x, int[][] mask_y, int N, int M) {
        for (int i = 1; i <= N-3; i++){
            for(int j = 1; j <= M-3; j++) {
                int output_x = ((input[i-1][j-1]* mask_x[0][0]) +
                				(input[i-1][j]	* mask_x[0][1]) +
                				(input[i-1][j+1]* mask_x[0][2]) +
                                (input[i][j-1]	* mask_x[1][0]) +
                                (input[i][j]	* mask_x[1][1]) +
                                (input[i][j+1]	* mask_x[1][2]) +
                                (input[i+1][j-1]* mask_x[2][0]) +
                                (input[i+1][j]	* mask_x[2][1]) +
                                (input[i+1][j+1]* mask_x[2][2]));
                
                int output_y = ((input[i-1][j-1]* mask_y[0][0]) +
        						(input[i-1][j]	* mask_y[0][1]) +
        						(input[i-1][j+1]* mask_y[0][2]) +
        						(input[i][j-1]	* mask_y[1][0]) +
        						(input[i][j]	* mask_y[1][1]) +
        						(input[i][j+1]	* mask_y[1][2]) +
        						(input[i+1][j-1]* mask_y[2][0]) +
        						(input[i+1][j]	* mask_y[2][1]) +
        						(input[i+1][j+1]* mask_y[2][2]));
                
               output[i][j] = (int)Math.sqrt((output_x * output_x) + (output_y * output_y));
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

        File output = new File("image/output_edge_detection_turunan_pertama.jpg");
        ImageIO.write(image, "jpg", output);
    }

}
