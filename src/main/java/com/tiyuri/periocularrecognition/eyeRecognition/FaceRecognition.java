package com.tiyuri.periocularrecognition.eyeRecognition;

        import android.app.Activity;
        import android.graphics.Bitmap;
        import android.graphics.BitmapFactory;
        import android.util.Log;
        import android.widget.Toast;

        import com.tiyuri.periocularrecognition.R;
        import org.opencv.android.OpenCVLoader;
        import org.opencv.android.Utils;
        import org.opencv.core.CvType;
        import org.opencv.core.Mat;
        import org.opencv.core.MatOfRect;
        import org.opencv.core.Rect;
        import org.opencv.imgcodecs.Imgcodecs;
        import org.opencv.imgproc.Imgproc;
        import org.opencv.objdetect.CascadeClassifier;
        import java.io.File;
        import java.io.FileNotFoundException;
        import java.io.FileOutputStream;
        import java.io.IOException;
        import java.io.InputStream;

public class FaceRecognition {

    /*
    * Função: detect eyes;
    * Descrição: Localiza e recorta o olho de uma foto, sobrescrevendo a mesma após o processo;
    *
    * Entradas:
    *   activity: A atividade formulario na qual ele é executado;
    *   path: O caminho da foto que será modificada;
    * Saidas:
    *   0: foto Modificada corretamente;
    *   -1: Problema ao carregar o OpenCV;
    *   -2: Problema ao carregar o xml;
    *   -3: Problema ao carregar o xml do celular;
    */

    public static int detectEyes(Activity activity, String path) {

        // Convert the bufferedImage to a Mat type

            if (!OpenCVLoader.initDebug()) {
                Log.d("tag", "opencv not initialized");
                return -1;
            }
        //---- load image ----------------------------
        Bitmap img = BitmapFactory.decodeFile(path);
        Mat image=new Mat();
        Utils.bitmapToMat(img, image);
        Imgproc.cvtColor(image,image, Imgproc.COLOR_BGR2RGBA);
        //--------------------------------------------


       try {

           //Loads a cascade classifier to eye recognition
            InputStream is = activity.getResources().openRawResource(R.raw.haarcascade_eye_tree_eyeglasses);
            File cascadeDir = activity.getDir("cascade", activity.MODE_PRIVATE);
            File mCascadeFile = new File(cascadeDir, "haarcascade_eye_tree_eyeglasses.xml");
            FileOutputStream os = new FileOutputStream(mCascadeFile);

            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = is.read(buffer)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            is.close();
            os.close();

            CascadeClassifier faceDetector = new CascadeClassifier(mCascadeFile.getAbsolutePath());
            if(faceDetector.empty()){return -2;}
            else
            {
                // Detect eyes in the image.
                // MatOfRect is a special container class for Rect.
                MatOfRect eyeDetection = new MatOfRect();
                faceDetector.detectMultiScale(image, eyeDetection);

                Log.d("tag", String.format("Detected %s eyes", eyeDetection.toArray().length));

                if(eyeDetection.toArray().length == 1) {
                    // Make new images for the eye.
                    for (Rect rect : eyeDetection.toArray()) {
                        int x0, y0, x, y;
                        x0 = rect.x - ((rect.width / 2));
                        y0 = rect.y - ((rect.height / 2));
                        x = rect.width + (rect.width);
                        y = rect.height + (rect.height);

                        if (x0 > 0) {rect.x = x0;}
                        else {rect.x = 0;}

                        if (y0 > 0) {rect.y = y0;}
                        else {rect.y = 0;}

                        if (x < img.getWidth()) {rect.width = x;}
                        else {rect.width = img.getWidth();}

                        if (y < img.getHeight()) {rect.height = y;}
                        else {rect.height = img.getHeight();}

                        Mat cropped = new Mat(image, rect);
                        Imgcodecs.imwrite(path, cropped);
                    }
                    //---------------------------------------
                    return 1;
                } else{
                    return eyeDetection.toArray().length;
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return -3;
        } catch (IOException e) {
            e.printStackTrace();
            return -3;
        }
    }


    /*
    * Função: errorHandler;
    * Descrição: trata os erros da detecção do olho;
    * Entrada:
    *   Activity: a atividade que chamou a função;
    *   error: o numero do erro a ser tratado;
    * Saida: ---;
    *
     */

    public static void errorHandler(Activity activity, int error){
        switch (error){
            case -1:
                Toast.makeText(activity, "Problema ao carregar o OpenCV", Toast.LENGTH_SHORT).show();
                break;
            case -2:
                Toast.makeText(activity, "Problema ao carregar o xml", Toast.LENGTH_SHORT).show();
                break;
            case -3:
                Toast.makeText(activity, "Problema ao carregar o xml do celular", Toast.LENGTH_SHORT).show();
                break;
            default:
                Toast.makeText(activity, "foram detectados " + error + " olhos, por favor tire a foto novamente", Toast.LENGTH_SHORT).show();

        }
    }

}

