package FINAL;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class turunan_kedua {
	public static void main(String[] args) throws IOException{
        File file = new File("image/input.jpg");
        int[][] input = imageToMatrix(file);
        int[][] output = new int[input.length-2][input[0].length-2];

//        int[][] kernel = {
//                { 0,  1,  0},
//                { 1, -4,  1},  // Laplace 1
//                { 0,  1,  0}
//        };
        
//        int[][] kernel = {
//                {-1, -1, -1},
//                {-1,  8, -1},  // Laplace 2
//                {-1, -1, -1}
//        };
        
//        int[][] kernel = {
//                { 1, -2,  1},
//                {-2,  4, -2},  // Laplace 3
//                { 1, -2,  1}
//        };
        
//        int[][] kernel = {
//                { 1,  4,  1},
//                { 4,-20,  4},  // Laplace Bobot Lebih
//                { 1,  4,  1}
//        };

        int[][] kernel = {
                { 0,  0, -1,  0,  0},
                { 0, -1, -2, -1,  0},  
                {-1, -2, 16, -2, -1},	// Laplacian of Gaussian
                { 0, -1, -2, -1,  0},
                { 0,  0, -1,  0,  0}
        };

        conv(input, output, kernel, input.length, input[0].length);
        matrixToImage(output);

    }

    public static void conv (int[][] input, int[][] output, int[][] mask, int N, int M) {
    	if(mask.length == 3 ) { // Jika meggunakna kernel/filter/mask berukuran 5x5
    		for (int i = 1; i <= N-3; i++){
                for(int j = 1; j <= M-3; j++) {
                    output[i][j] = ((input[i-1][j-1]* mask[0][0]) +
                    				(input[i-1][j]	* mask[0][1]) +
                    				(input[i-1][j+1]* mask[0][2]) +
                                    (input[i][j-1]	* mask[1][0]) +
                                    (input[i][j]	* mask[1][1]) +
                                    (input[i][j+1]	* mask[1][2]) +
                                    (input[i+1][j-1]* mask[2][0]) +
                                    (input[i+1][j]	* mask[2][1]) +
                                    (input[i+1][j+1]* mask[2][2]));
                }
            }
    	} else if (mask.length == 5) { // Jika meggunakna kernel/filter/mask berukuran 5x5
    		for (int i = 1; i <= N-5; i++){
                for(int j = 1; j <= M-5; j++) {
                    output[i][j] = ((input[i-1][j-1]* mask[0][0]) +
                    				(input[i-1][j]	* mask[0][1]) +
                    				(input[i-1][j+1]* mask[0][2]) +
                    				(input[i-1][j+2]* mask[0][3]) +
                    				(input[i-1][j+3]* mask[0][4]) +
                                    (input[i][j-1]	* mask[1][0]) +
                                    (input[i][j]	* mask[1][1]) +
                                    (input[i][j+1]	* mask[1][2]) +
                                    (input[i][j+2]	* mask[1][3]) +
                                    (input[i][j+3]	* mask[1][4]) +
                                    (input[i+1][j-1]* mask[2][0]) +
                                    (input[i+1][j]	* mask[2][1]) +
                                    (input[i+1][j+1]* mask[2][2]) +
                                    (input[i+1][j+2]* mask[2][3]) +
                                    (input[i+1][j+3]* mask[2][4]) +
                                    (input[i+2][j-1]* mask[3][0]) +
                                    (input[i+2][j]	* mask[3][1]) +
                                    (input[i+2][j+1]* mask[3][2]) +
                                    (input[i+2][j+2]* mask[3][3]) +
                                    (input[i+2][j+3]* mask[3][4]) +
                                    (input[i+3][j-1]* mask[4][0]) +
                                    (input[i+3][j]	* mask[4][1]) +
                                    (input[i+3][j+1]* mask[4][2]) +
                                    (input[i+3][j+2]* mask[4][3]) +
                                    (input[i+3][j+3]* mask[4][4]));
                }
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

        File output = new File("image/output_edge_detection_turunan_kedua.jpg");
        ImageIO.write(image, "jpg", output);
    }

}
