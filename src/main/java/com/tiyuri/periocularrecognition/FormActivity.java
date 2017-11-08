package com.tiyuri.periocularrecognition;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.tiyuri.periocularrecognition.DAO.User;
import com.tiyuri.periocularrecognition.DAO.UserDAO;
import com.tiyuri.periocularrecognition.eyeRecognition.FaceRecognition;
import com.tiyuri.periocularrecognition.eyeRecognition.LBP;

import java.io.File;
import java.util.Arrays;
import java.util.concurrent.Semaphore;


public class FormActivity extends AppCompatActivity {
    static final int REQUEST_IMAGE_CAPTURE_1 = 1;
    static final int REQUEST_IMAGE_CAPTURE_2 = 2;
    static final int REQUEST_IMAGE_CAPTURE_3 = 3;
    public String photo1Path;
    public String photo2Path;
    public String photo3Path;
    public String histogram1 = "";
    public String histogram2 = "";
    public String histogram3 = "";
    private FormHelper helper;
    private Semaphore semaforo = new Semaphore(1, true);


    //-----when form created -----------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);
        helper = new FormHelper(this);

        //--- save button photo 1 ----
        Button photo1Button = (Button) findViewById(R.id.form_button_photo1);
        photo1Button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent photo = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                photo1Path = getExternalFilesDir(null) + "/" + "photo1.jpg";
                Log.d("tag", photo1Path);
                File photo1File = new File(photo1Path);
                photo.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photo1File));
                startActivityForResult(photo, REQUEST_IMAGE_CAPTURE_1);
            }
        });
        //----------------------------

        //--- save button photo 2 ----
        Button photo2Button = (Button) findViewById(R.id.form_button_photo2);
        photo2Button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent photo = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                photo2Path = getExternalFilesDir(null) + "/" + "photo2.jpg";
                File photo2File = new File(photo2Path);
                photo.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photo2File));
                startActivityForResult(photo, REQUEST_IMAGE_CAPTURE_2);
            }
        });
        //----------------------------

        //--- save button photo 3 ----
        Button photo3Button = (Button) findViewById(R.id.form_button_photo3);
        photo3Button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent photo = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                photo3Path = getExternalFilesDir(null) + "/" + "photo3.jpg";
                File photo3File = new File(photo3Path);
                photo.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photo3File));
                startActivityForResult(photo, REQUEST_IMAGE_CAPTURE_3);
            }
        });
        //----------------------------
    }


    //---actualize photos on form ----
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        int result = 1;
        if(resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case REQUEST_IMAGE_CAPTURE_1:
                    result = FaceRecognition.detectEyes(this, photo1Path);
                    if(result == 1) {
                        ImageView photo1 = (ImageView) findViewById(R.id.form_photo1);
                        Bitmap bitmap = BitmapFactory.decodeFile(photo1Path);
                        Bitmap reducedBitmap = Bitmap.createScaledBitmap(bitmap, 600, 600, true);
                        photo1.setImageBitmap(reducedBitmap);
                        final Bitmap lbpBitmap1 = reducedBitmap.copy(reducedBitmap.getConfig(), true);

                        new Thread(new Runnable() {
                            public void run() {
                                try {
                                    semaforo.acquire();
                                    histogram1 = Arrays.toString(LBP.returnLbpHistogram(lbpBitmap1));
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                semaforo.release();
                                Log.d("tag","foto 1 terminada");
                            }
                        }).start();

                    } else{
                        FaceRecognition.errorHandler(this, result);
                    }


                    break;
                case REQUEST_IMAGE_CAPTURE_2:
                    result = FaceRecognition.detectEyes(this, photo2Path);
                    if(result == 1){
                        ImageView photo2 = (ImageView) findViewById(R.id.form_photo2);
                        Bitmap bitmap2 = BitmapFactory.decodeFile(photo2Path);
                        Bitmap reducedBitmap2 = Bitmap.createScaledBitmap(bitmap2, 600, 600, true);
                        photo2.setImageBitmap(reducedBitmap2);
                        final Bitmap lbpBitmap2 = reducedBitmap2.copy(reducedBitmap2.getConfig(), true);

                        new Thread(new Runnable() {
                            public void run() {
                                try {
                                    semaforo.acquire();
                                    histogram2 = Arrays.toString(LBP.returnLbpHistogram(lbpBitmap2));
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                semaforo.release();
                                Log.d("tag","foto 2 terminada");
                            }
                        }).start();

                    } else{
                        FaceRecognition.errorHandler(this, result);
                    }

                    break;
                case REQUEST_IMAGE_CAPTURE_3:
                    result = FaceRecognition.detectEyes(this, photo3Path);
                    if(result == 1) {
                        ImageView photo3 = (ImageView) findViewById(R.id.form_photo3);
                        Bitmap bitmap3 = BitmapFactory.decodeFile(photo3Path);
                        Bitmap reducedBitmap3 = Bitmap.createScaledBitmap(bitmap3, 600, 600, true);
                        photo3.setImageBitmap(reducedBitmap3);
                        final Bitmap lbpBitmap3 = reducedBitmap3.copy(reducedBitmap3.getConfig(), true);

                        new Thread(new Runnable() {
                            public void run() {
                                try {
                                    semaforo.acquire();
                                    histogram3 = Arrays.toString(LBP.returnLbpHistogram(lbpBitmap3));
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                semaforo.release();
                                Log.d("tag","foto 3 terminada");
                            }
                        }).start();

                    } else{
                        FaceRecognition.errorHandler(this, result);
                    }

                    break;
            }
        }
    }
    //------------------------------



    //-----make save button---------
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_form, menu);
        return super.onCreateOptionsMenu(menu);
    }
    //------------------------------


    //----- save form --------------
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.menu_form_ok:
                try {
                    semaforo.acquire();
                    int verifier = verifica();
                    if(verifier == 1) {
                        User user = helper.getUser(this);
                        UserDAO dao = new UserDAO(this);
                        dao.insert(user);
                        dao.close();
                        semaforo.release();
                        finish();
                    }else{
                        formErrorHandler(verifier);
                        semaforo.release();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
        }
        return super.onOptionsItemSelected(item);
    }
    //------------------------------

    private int verifica(){
        EditText mEdit = (EditText) findViewById(R.id.form_name);
        String name = mEdit.getText().toString();
        if(name != null) {
            Log.d("tag", "here:" + name);
        }
        if(name == ""){
            return 2;
        }

        if(histogram1 == ""){
            return 3;
        }

        if(histogram2 == ""){
            return 4;
        }

        if(histogram3 == ""){
            return 5;
        }

        return 1;
    }

    private void formErrorHandler(int verifier){
        switch (verifier){
            case 2:
                Toast.makeText(this, "Preencha o nome", Toast.LENGTH_SHORT).show();
                break;
            case 3:
                Toast.makeText(this, "Tire a foto 1", Toast.LENGTH_SHORT).show();
                break;
            case 4:
                Toast.makeText(this, "Tire a foto 2", Toast.LENGTH_SHORT).show();
                break;
            case 5:
                Toast.makeText(this, "Tire a foto 3", Toast.LENGTH_SHORT).show();
                break;
        }
    }

}
