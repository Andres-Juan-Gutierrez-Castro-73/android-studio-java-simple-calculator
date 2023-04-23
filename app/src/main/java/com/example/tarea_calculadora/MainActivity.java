package com.example.tarea_calculadora;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    //CREAMOS LAS PROPIEDADES DE LOS BOTONES DE LA CALCULADORA
    TextView resultTv, solutionTv;
    MaterialButton buttonC, buttonBrackOpen, buttonBrackClose;
    MaterialButton buttonPlus, buttonMinus, buttonMultiply, buttonDivide, buttonEquals;
    MaterialButton button0, button1, button2, button3, button4, button5, button6, button7, button8, button9;
    MaterialButton buttonAC, buttonDot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*
        * EN ESTE METODO VAMOS A ASOCIAR TODOS LOS COMPONENTES DEL XML CON LAS PROPIEDADES DE LA CLASE
        */

        resultTv = findViewById(R.id.result_tv);
        solutionTv = findViewById(R.id.solution_tv);

        //IMPLEMENTAMOS EL METODO QUE HEMOS CREADO PARA ASOCIAR LOS COMPONENTES XML
        assingId(buttonC, R.id.button_c);
        assingId(buttonBrackOpen, R.id.button_open_bracket);
        assingId(buttonBrackClose, R.id.button_close_bracket);
        assingId(buttonPlus, R.id.button_plus);
        assingId(buttonMinus, R.id.button_minus);
        assingId(buttonMultiply, R.id.button_multiply);
        assingId(buttonDivide, R.id.button_divide);
        assingId(buttonEquals, R.id.button_equals);
        assingId(button0, R.id.button_0);
        assingId(button1, R.id.button_1);
        assingId(button2, R.id.button_2);
        assingId(button3, R.id.button_3);
        assingId(button4, R.id.button_4);
        assingId(button5, R.id.button_5);
        assingId(button6, R.id.button_6);
        assingId(button7, R.id.button_7);
        assingId(button8, R.id.button_8);
        assingId(button9, R.id.button_9);
        assingId(buttonAC, R.id.button_ac);
        assingId(buttonDot, R.id.button_dot);
    }

    //CREAMOS UN METODO QUE VA A ASOCIAR EL ID CON CON EL COMPONENTE
    public void assingId(MaterialButton btn, int id){
        btn = findViewById(id);
        btn.setOnClickListener(this);
    }

    //ESTE METODO EXTRAE LOS VALORES DE TEXTO QUE ESTAN EN LOS BOTONES
    @Override
    public void onClick(View view) {
        MaterialButton button = (MaterialButton) view;
        String buttonText =  button.getText().toString();
        String dataToCalculate = solutionTv.getText().toString();

        //VAMOS A DEFINIR LOS QUE VAN A HACER ALGUNOS BOTONES
        if (buttonText.equals("AC")) {
            solutionTv.setText("");
            resultTv.setText("0");
            return;
        } else if (buttonText.equals("=")) {
            solutionTv.setText(resultTv.getText());
            return;
        } else if (buttonText.equals("C")) {
            dataToCalculate = dataToCalculate.substring(0, dataToCalculate.length()-1);
        } else {
            dataToCalculate = dataToCalculate + buttonText;
        }

        solutionTv.setText(dataToCalculate);

        String finalResult = getResult(dataToCalculate);

        //CONTROLAMOS PARA QUE NO NOS SALGA NINGUN ERROR
        if (!finalResult.equals("Err")) {
            resultTv.setText(finalResult);
        }
    }

    public String getResult(String data) {
        //MANEJAMOS LAS EXCEPCIONES QUE NOS PUEDA DAR EL PROGRAMA
        try {
            Context context = Context.enter();
            context.setOptimizationLevel(-1);
            Scriptable scriptable = context.initStandardObjects();
            String finalResult = context.evaluateString(scriptable, data, "Javascript", 1, null).toString();

            if (finalResult.endsWith(".0")) {
                finalResult = finalResult.replace(".0", "");
            }

            return finalResult;
        } catch (Exception e) {
            return "Err";
        }
    }
}