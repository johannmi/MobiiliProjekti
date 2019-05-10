package com.example.mobiiliprojekti;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Add new journal entry activity
 */

public class DoneToday extends AppCompatActivity {
    private Switch koulu, tyo, liikunta, elokuva, siivous, kauppa, lukeminen , peli;
    private Button save;
    private EditText notes, muuta;
    private RadioButton mood;
    private RadioGroup radioGroup;
    private String userNotes = "", userMood = "3";  // Default values
    private DatabaseHelper dbHelper;
    private DateAndTime dateAndTime;
    private Context mCtx;
    private TextView textViewMoodName;

    /**
     * onCreate()
     * @param savedInstanceState Bundle
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_done_today);
        dbHelper = new DatabaseHelper(this);
        dateAndTime = new DateAndTime();
        addListenerOnButton();
        mCtx = this;
        textViewMoodName = findViewById(R.id.textViewMoodName);

    }

    /**
     * Called when a radio button is clicked
     * @param view View
     */

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked and sets mood name and value
        switch(view.getId()) {
            case R.id.radio_1:
                if (checked)
                    userMood = "5";
                    textViewMoodName.setText("Erinomainen");
                    break;
            case R.id.radio_2:
                if (checked)
                    userMood = "4";
                    textViewMoodName.setText("Hyvä");
                    break;
            case R.id.radio_3:
                if (checked)
                    userMood = "3";
                    textViewMoodName.setText("Neutraali");
                    break;
            case R.id.radio_4:
                if (checked)
                    userMood = "2";
                textViewMoodName.setText("Huono");
                    break;
            case R.id.radio_5:
                if (checked)
                    userMood = "1";
                    textViewMoodName.setText("Kamala");
                    break;
        }
    }

    /**
     * Finds views and sets save button on click -method
     */

    public void addListenerOnButton() {
        radioGroup = findViewById(R.id.radioGroup);
        koulu = findViewById(R.id.Koulu);
        tyo = findViewById(R.id.Tyo);
        liikunta = findViewById(R.id.Liikunta);
        elokuva = findViewById(R.id.Elokuva);
        siivous = findViewById(R.id.Siivous);
        kauppa = findViewById(R.id.Kauppa);
        lukeminen = findViewById(R.id.Luku);
        peli = findViewById(R.id.Peli);
        muuta = findViewById(R.id.Muuta);
        save = findViewById(R.id.appCompatButtonRegister);
        notes = findViewById (R.id.addNote);

        save.setOnClickListener(new View.OnClickListener() {

            /**
             * Checks all the information entered and saves them into the database
             * @param v View
             */

            @Override
            public void onClick(View v) {

                StringBuffer result = new StringBuffer();

                // get selected radio button from radioGroup
                int selectedId = radioGroup.getCheckedRadioButtonId();

                // find the radiobutton by returned id
                mood = findViewById(selectedId);

                // Adds data from switches
                result.append(mood.getText());
                if ((koulu).isChecked()) result.append(" • ").append(koulu.getText());
                if ((tyo).isChecked()) result.append(" • ").append(tyo.getText());
                if ((liikunta).isChecked()) result.append(" • ").append(liikunta.getText());
                if ((elokuva).isChecked()) result.append(" • ").append(elokuva.getText());
                if ((siivous).isChecked()) result.append(" • ").append(siivous.getText());
                if ((kauppa).isChecked()) result.append(" • ").append(kauppa.getText());
                if ((lukeminen).isChecked()) result.append(" • ").append(lukeminen.getText());
                if ((peli).isChecked()) result.append(" • ").append(peli.getText());

                // Checks if user entered any extra data, if yes -> add after switches
                if (TextUtils.isEmpty(muuta.getText().toString())) {
                    // Do nothing
                } else {
                    result.append(" • ").append(muuta.getText());
                }

                // User's own notes
                userNotes = notes.getText().toString();

                // Adds data to database
                boolean insertData = dbHelper.addData(
                        dateAndTime.getFullDate(),
                        dateAndTime.getDayName() + " kello " + dateAndTime.getTime(),
                        userMood,
                        result.toString().substring(3),
                        userNotes
                );

                // Displays toast message that says if data was added successfully
                if (insertData) {
                    toastMessage("Merkintä lisätty");
                } else {
                    toastMessage("Jotain meni pieleen");
                }

                // Switch back to MainActivity
                Intent intent = new Intent(mCtx, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    /**
     * Displays a toast message
     * @param message Message to be displayed in the toast
     */
    private void toastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
