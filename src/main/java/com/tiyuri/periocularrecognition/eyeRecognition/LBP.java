package com.tiyuri.periocularrecognition.eyeRecognition;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.Log;

import java.util.concurrent.Semaphore;


public class LBP {
    private static int[][] matrix;
    private static int width;
    private static int height;

    /*
    * Função: returnLbpHistogram;
    * Descrição: Interface para o acesso da classe;
    * Entrada:
    *   img: uma imagem em formato bitmap;
    * Saida: Array de 255 contendo o histograma da imagem;
    *
     */

    public static float[] returnLbpHistogram(Bitmap img){
        makeGray(img);
        return doLBP(matrix);
    }

    /*
    * Função: makeGray;
    * Descrição: preenche uma matrix com os tons de cinza da imagem;
    * Entrada:
    *   img: uma imagem em formato bitmap;
    * Saida: ---;
    *
     */

    private static void makeGray(Bitmap img)
    {
        width = img.getWidth() - 1;
        height = img.getHeight() - 1;
        int R;
        matrix = new int[width][height];

        for(int x = 0; x < width; ++x)
        {
            for(int y = 0; y < height; ++y)
            {
                int pixel = img.getPixel(x, y);
                R = (int)(0.299 * Color.red(pixel) + 0.587 * Color.green(pixel) + 0.114 * Color.blue(pixel));
                matrix[x][y] = R;
            }
        }
    }

    /*
    * Função: doLBP;
    * Descrição: Aplica o processo de LBP na matris;
    * Entrada:
    *   img: uma matrix que contem os tons de cinza de uma imagem;
    * Saida: Array de 255 contendo o histograma da imagem;;
    *
     */

    private static float[] doLBP(int[][] img){
        final int[][] img2 = img;
        final Semaphore semaphore1 = new Semaphore(0, true);
        final Semaphore semaphore2 = new Semaphore(0, true);
        final Semaphore semaphore3 = new Semaphore(0, true);
        final Semaphore semaphore4 = new Semaphore(0, true);
        final int relativeWidth = width/4;
		/*
		 * transform the center of image
		 */
        long t1 = System.currentTimeMillis();
        new Thread(new Runnable() {
            public void run() {
                String binary = "";
                int mainPixel;
                int[] neighbours = new int[8];
                for (int x = 1; x < relativeWidth ; ++x){
                    for (int y = 1; y < height - 1; ++y){

                        mainPixel = matrix[x][y];
                        neighbours[0] = matrix[x-1][y-1];
                        neighbours[1] = matrix[x][y-1];
                        neighbours[2] = matrix[x+1][y-1];
                        neighbours[3] = matrix[x+1][y];
                        neighbours[4] = matrix[x+1][y+1];
                        neighbours[5] = matrix[x][y+1];
                        neighbours[6] = matrix[x-1][y+1];
                        neighbours[7] = matrix[x-1][y];

                        for (int i = 0; i < 8; i++){
                            if(neighbours[i] >= mainPixel){
                                binary = binary + "1";
                            } else{
                                binary = binary + "0";
                            }
                        }
                        int grayLevel = Integer.parseInt(binary, 2);
                        img2[x][y] = grayLevel;
                        binary = "";
                    }
                }
                semaphore1.release();
            }
        }).start();

        new Thread(new Runnable() {
            public void run() {
                String binary = "";
                int mainPixel;
                int[] neighbours = new int[8];
                for (int x = width/4; x < 2*relativeWidth ; ++x){
                    for (int y = height; y < height - 1; ++y){

                        mainPixel = matrix[x][y];
                        neighbours[0] = matrix[x-1][y-1];
                        neighbours[1] = matrix[x][y-1];
                        neighbours[2] = matrix[x+1][y-1];
                        neighbours[3] = matrix[x+1][y];
                        neighbours[4] = matrix[x+1][y+1];
                        neighbours[5] = matrix[x][y+1];
                        neighbours[6] = matrix[x-1][y+1];
                        neighbours[7] = matrix[x-1][y];

                        for (int i = 0; i < 8; i++){
                            if(neighbours[i] >= mainPixel){
                                binary = binary + "1";
                            } else{
                                binary = binary + "0";
                            }
                        }
                        int grayLevel = Integer.parseInt(binary, 2);
                        img2[x][y] = grayLevel;
                        binary = "";
                    }
                }
                semaphore2.release();
            }
        }).start();

        new Thread(new Runnable() {
            public void run() {
                String binary = "";
                int mainPixel;
                int[] neighbours = new int[8];
                for (int x = 2*relativeWidth; x < 3*relativeWidth ; ++x){
                    for (int y = height; y < height - 1; ++y){

                        mainPixel = matrix[x][y];
                        neighbours[0] = matrix[x-1][y-1];
                        neighbours[1] = matrix[x][y-1];
                        neighbours[2] = matrix[x+1][y-1];
                        neighbours[3] = matrix[x+1][y];
                        neighbours[4] = matrix[x+1][y+1];
                        neighbours[5] = matrix[x][y+1];
                        neighbours[6] = matrix[x-1][y+1];
                        neighbours[7] = matrix[x-1][y];

                        for (int i = 0; i < 8; i++){
                            if(neighbours[i] >= mainPixel){
                                binary = binary + "1";
                            } else{
                                binary = binary + "0";
                            }
                        }
                        int grayLevel = Integer.parseInt(binary, 2);
                        img2[x][y] = grayLevel;
                        binary = "";
                    }
                }
                semaphore3.release();
            }
        }).start();


        new Thread(new Runnable() {
            public void run() {
                String binary = "";
                int mainPixel;
                int[] neighbours = new int[8];
                for (int x = 3*relativeWidth; x < width -1 ; ++x){
                    for (int y = height; y < height - 1; ++y){

                        mainPixel = matrix[x][y];
                        neighbours[0] = matrix[x-1][y-1];
                        neighbours[1] = matrix[x][y-1];
                        neighbours[2] = matrix[x+1][y-1];
                        neighbours[3] = matrix[x+1][y];
                        neighbours[4] = matrix[x+1][y+1];
                        neighbours[5] = matrix[x][y+1];
                        neighbours[6] = matrix[x-1][y+1];
                        neighbours[7] = matrix[x-1][y];

                        for (int i = 0; i < 8; i++){
                            if(neighbours[i] >= mainPixel){
                                binary = binary + "1";
                            } else{
                                binary = binary + "0";
                            }
                        }
                        int grayLevel = Integer.parseInt(binary, 2);
                        img2[x][y] = grayLevel;
                        binary = "";
                    }
                }
                semaphore4.release();
            }
        }).start();


        try {
            semaphore1.acquire();
            semaphore2.acquire();
            semaphore3.acquire();
            semaphore4.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


		/*
		 * transform the first line
		 */
        for (int x = 0; x <= width - 1; ++x){
            int grayLevel = img2[x][1];
            img2[x][0] = grayLevel;
        }

		/*
		 * transform last line
		 */
        for (int x = 0; x <= width - 1; ++x){
            int grayLevel = img2[x][height - 2];
            img2[x][height - 1] = grayLevel;
        }

		/*
		 * transform first column
		 */
        for (int y = 0; y <= height - 1; ++y){
            int grayLevel = img2[1][y];
            img2[0][y] = grayLevel;
        }

		/*
		 * transform last column
		 */
        for (int y = 0; y <= height - 1; ++y){
            int grayLevel = img2[width - 2][y];
            img2[width - 1][y] = grayLevel;
        }
        long t2 = System.currentTimeMillis();
        Log.d("tag", "tempo: " + (t2 - t1));

        return(histogram(img2));
    }

    /*
    * Função: histogram;
    * Descrição: cria um histograma a partir de uma matris;
    * Entrada:
    *   img: uma matrix que contem o LBP de uma imagem;
    * Saida: Array de 255 contendo o histograma da imagem;;
    *
     */

    private static float[] histogram(int[][] img){
        int gray;
        float[] histogram = new float[256];
        for(int i = 0; i < 256; i++){
            histogram[i] = 0;
        }

        for (int x = 0; x < width; ++x)
        {
            for (int y = 0; y < height; ++y)
            {
                gray = img[x][y];
                histogram[gray] = histogram[gray] + 1;
            }
        }

        for (int x = 0; x < 256; ++x){
            histogram[x] = histogram[x]/(height*width);
        }

        return(histogram);
    }


}