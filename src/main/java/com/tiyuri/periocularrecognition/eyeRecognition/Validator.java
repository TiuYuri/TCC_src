package com.tiyuri.periocularrecognition.eyeRecognition;

        import android.util.Log;

        import com.tiyuri.periocularrecognition.DAO.User;

        import java.util.Arrays;
        import java.util.Locale;
        import java.util.Scanner;
        import java.util.concurrent.Semaphore;

public class Validator {

    /*
    * Função: validate;
    * Descrição: Aplica a distância euclidiana em 2 histogramas e retorna o numero resultante;
    * Entrada: histogram1 e histogram2 são vetores de tamanho 255 e comtem histogramas de imagens;
    * Saida: distancia euclidiana entre os dois vetores;
    *
     */

    public static float validate(float[] histogram1, float[] histogram2){
        double sum = 0;
        for(int x = 0; x < 256; ++x){
            sum = sum + Math.pow((histogram1[x] - histogram2[x]), 2);
        }
        Log.d("tag", String.valueOf(Math.sqrt(sum)));
        return (float) Math.sqrt(sum);
    }

    /*
    * Função: personValidate;
    * Descrição: Compara os histogramas de um usuario com o de uma foto e retorna o melhor resultado;
    * Entrada:
    *   user: um User que contem 3 histogramas;
    *   histogram: histograma de 1 foto;
    * Saida: melhor distancia euclidiana encontrada;
    *
     */

    public static float personValidate(final User user, final float[] histogram){
        final Semaphore semaphore1 = new Semaphore(0, true);
        final Semaphore semaphore2 = new Semaphore(0, true);
        final Semaphore semaphore3 = new Semaphore(0, true);
        final float[] sum = {0,0,0};
        float finalSum = -1;

        new Thread(new Runnable() {
            public void run() {

                sum[0] = validate(returnArray(user.getPhoto1Lbp()), histogram);
                semaphore1.release();
            }
        }).start();

        new Thread(new Runnable() {
            public void run() {
                sum[1] = validate(returnArray(user.getPhoto2Lbp()), histogram);
                semaphore2.release();
            }
        }).start();

        new Thread(new Runnable() {
            public void run() {
                sum[2] = validate(returnArray(user.getPhoto3Lbp()), histogram);
                semaphore3.release();
            }
        }).start();

        try {
            semaphore1.acquire();
            semaphore2.acquire();
            semaphore3.acquire();
            finalSum = sum[0];
            if(sum[1] < finalSum){finalSum = sum[1];}
            if(sum[2] < finalSum){finalSum = sum[2];}
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return finalSum;
    }

    /*
    * Função: returnArray;
    * Descrição: Recebe uma string de numeros e retorna um array de floats;
    * Entrada: arrayString uma string contendo 255 floats;
    * Saida: histogram um array de 255 floats;
    *
     */

    public static float[] returnArray(String arrayString){
        float[] histogram = new float[256];
        arrayString = arrayString.replace("[", "");
        arrayString = arrayString.replace("]", "");
        String[] numbers = arrayString.split(",");

        for(int x = 0; x < numbers.length; x++){
            histogram[x] = Float.valueOf(numbers[x]);
        }

        return histogram;
    }
}