package com.tiyuri.periocularrecognition;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;

import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.tiyuri.periocularrecognition.DAO.User;
import com.tiyuri.periocularrecognition.DAO.UserDAO;
import com.tiyuri.periocularrecognition.eyeRecognition.FaceRecognition;
import com.tiyuri.periocularrecognition.eyeRecognition.LBP;
import com.tiyuri.periocularrecognition.eyeRecognition.Validator;

import java.io.File;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_IMAGE_CAPTURE = 4;
    private ListView userList;
    private String ValidatePhotoPath;
    private User validateUser;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        userList = (ListView) findViewById(R.id.main_users_list);
        loadUsers();

        userList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                validateUser = (User) userList.getItemAtPosition(i);
                Intent validate = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                ValidatePhotoPath = getExternalFilesDir(null) + "/" + "photo4.jpg";
                File photoFile = new File(ValidatePhotoPath);
                validate.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
                startActivityForResult(validate, REQUEST_IMAGE_CAPTURE);
            }
        });

        //------ listener do add button ---------
        Button buttonAdd = (Button) findViewById(R.id.main_add_button);
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentGoToForm = new Intent(MainActivity.this, FormActivity.class);
                startActivity(intentGoToForm);
            }
        });
        //--------------------------------------
        registerForContextMenu(userList);
    }

    //--- menu criado ao segurar um usuario ----
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, final ContextMenu.ContextMenuInfo menuInfo) {
        MenuItem delete = menu.add("deletar");
        delete.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
                User user = (User) userList.getItemAtPosition(info.position);
                UserDAO dao = new UserDAO(MainActivity.this);
                dao.delete(user);
                dao.close();
                loadUsers();
                return false;
            }
        });
        menu.add("modificar");
    }
    //------------------------------------------

    //----carrega lista de users ---------------
    private void loadUsers() {
        UserDAO dao = new UserDAO(this);
        List<User> users = dao.getAllUsers();
        dao.close();
        ArrayAdapter<User> adapter = new ArrayAdapter<User>(this, android.R.layout.simple_list_item_1, users);
        userList.setAdapter(adapter);
    }
    //----------------------------------


    //---carrega lista de users onResume ------
    @Override
    protected void onResume() {
        super.onResume();
        loadUsers();
    }
    //---------------------------------


    //----- faz a validação do usuário ---------
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case REQUEST_IMAGE_CAPTURE:
                    int result = FaceRecognition.detectEyes(this, ValidatePhotoPath);
                    if(result == 1) {
                        Bitmap bitmap = BitmapFactory.decodeFile(ValidatePhotoPath);
                        Bitmap reducedBitmap = Bitmap.createScaledBitmap(bitmap, 600, 600, true);
                        float[] histogram = LBP.returnLbpHistogram(reducedBitmap);
                        float equality = Validator.personValidate(validateUser, histogram);
                        if (equality < 0.06) {
                            Intent intentGoToValidated = new Intent(MainActivity.this, ValidatedActivity.class);
                            intentGoToValidated.putExtra("equality", equality);
                            startActivity(intentGoToValidated);
                        } else {
                            Intent intentGoToNotValidated = new Intent(MainActivity.this, NotValidatedActivity.class);
                            intentGoToNotValidated.putExtra("equality", equality);
                            startActivity(intentGoToNotValidated);
                        }
                    }else {
                        FaceRecognition.errorHandler(this, result);
                    }
                    break;
            }
        }
    }
    //----------------------------------------
}
