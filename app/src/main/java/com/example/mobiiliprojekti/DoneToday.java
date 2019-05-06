package com.example.mobiiliprojekti;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;

public class DoneToday extends AppCompatActivity {
    private Switch koulu, tyo, liikunta, elokuva, siivous, kauppa, lukeminen , peli;
    private Button Save;
    private EditText Notes;
    private RadioButton mood;
    private RadioGroup radioGroup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_done_today);
        addListenerOnButton();

    }
    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radio_1:
                if (checked)

                    break;
            case R.id.radio_2:
                if (checked)

                    break;
            case R.id.radio_3:
                if (checked)

                    break;
            case R.id.radio_4:
                if (checked)

                    break;
            case R.id.radio_5:
                if (checked)

                    break;

        }
    }

    public void addListenerOnButton() {
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        koulu = (Switch) findViewById(R.id.Koulu);
        tyo = (Switch) findViewById(R.id.Työ);
        liikunta = (Switch) findViewById(R.id.Liikunta);
        elokuva = (Switch) findViewById(R.id.Elokuva);
        siivous = (Switch) findViewById(R.id.Siivous);
        kauppa = (Switch) findViewById(R.id.Kauppa);
        lukeminen = (Switch) findViewById(R.id.Luku);
        peli = (Switch) findViewById(R.id.Peli);
        Save = (Button) findViewById(R.id.appCompatButtonRegister);
        Notes = (EditText) findViewById (R.id.addNote);

        Save.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                StringBuffer result = new StringBuffer();

                // get selected radio button from radioGroup
                int selectedId = radioGroup.getCheckedRadioButtonId();

                // find the radiobutton by returned id
                mood = (RadioButton) findViewById(selectedId);



                result.append(mood.getText());
                if (((Switch) koulu).isChecked()) result.append(" * ").append(koulu.getText());
                if (((Switch) tyo).isChecked()) result.append(" * ").append(tyo.getText());
                if (((Switch) liikunta).isChecked()) result.append(" * ").append(liikunta.getText());
                if (((Switch) elokuva).isChecked()) result.append(" * ").append(elokuva.getText());
                if (((Switch) siivous).isChecked()) result.append(" * ").append(siivous.getText());
                if (((Switch) kauppa).isChecked()) result.append(" * ").append(kauppa.getText());
                if (((Switch) lukeminen).isChecked()) result.append(" * ").append(lukeminen.getText());
                if (((Switch) peli).isChecked()) result.append(" * ").append(peli.getText());
                result.append(" * Muistiinpanot: ").append(Notes.getText());





            }
    });
}
}
