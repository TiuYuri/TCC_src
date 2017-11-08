package com.tiyuri.periocularrecognition;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class NotValidatedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_not_validated);
        Bundle extras = getIntent().getExtras();
        float equality = extras.getFloat("equality");
        String number = String.valueOf(equality);

        TextView notValidated_number = (TextView)findViewById(R.id.notValidated_number);
        notValidated_number.setText(number);

        //--- botão voltar ----
        Button backButton = (Button) findViewById(R.id.notValidated_back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        //----------------------------
    }
}
