package com.dialogs.wagner.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

/*
    Para mostrar avisos ao usuário, o mais indicado é utilizar dialogs. Para
    criar um dialog no Android, o indicado é herdar a classe DialogFragment.
    Ao criar uma subclasse, você deve sobrescrever os métodos onCreate() e
    onCreateView() para criar o seu diálogo customizado. Outra opção caso você
    queira um diálogo simples, como um AlertDialog por exemplo, você pode sobrescrever
    o método onCreateDialog().
 */

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Salva o estado e a activity que será exibida.
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        //Obtém a referência dos componentes da activity.
        Button progressButton = (Button) findViewById(R.id.show_progress_dialog_button);
        Button alertButton = (Button) findViewById(R.id.show_alert_dialog_button);
        Button customButton = (Button) findViewById(R.id.show_custom_dialog_button);

        //Define as ações que serão executadas ao clicar nos botões.
        progressButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment dialog = ProgressDialogFragment.newInstance();
                dialog.show(getFragmentManager(),"progress");
            }
        });

        alertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment dialog = AlertDialogFragment.newInstance();
                dialog.show(getFragmentManager(), "alert");
            }
        });

        customButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment dialog = CustomDialogFragment.newInstance();
                dialog.show(getFragmentManager(), "custom");
            }
        });
    }

    public static class AlertDialogFragment extends DialogFragment{
        public static AlertDialogFragment newInstance(){
            AlertDialogFragment frag = new AlertDialogFragment();
            return frag;
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            AlertDialog dialog = new AlertDialog.Builder(getActivity()).create();
            dialog.setTitle(getActivity().getString(R.string.attention));
            dialog.setMessage(getActivity().getString(R.string.which_button_gonna_press));
            dialog.setButton(DialogInterface.BUTTON_POSITIVE, getActivity().getString(R.string.yes),
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(getActivity(), R.string.pressed_yes,
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
            dialog.setButton(DialogInterface.BUTTON_NEGATIVE, getActivity().getString(R.string.no),
                    new DialogInterface.OnClickListener(){
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(getActivity(), R.string.pressed_no,
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
            return dialog;
        }
    }//class AlertDialogFragment

    public static class ProgressDialogFragment extends DialogFragment {
        public static ProgressDialogFragment newInstance() {
            ProgressDialogFragment frag = new ProgressDialogFragment();
            return frag;
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            Dialog dialog = new ProgressDialog(getActivity());
            dialog.setTitle(R.string.wait);
            return dialog;
        }
    }//class ProgressDialogFragment

    public static class CustomDialogFragment extends DialogFragment {
        public static CustomDialogFragment newInstance() {
            CustomDialogFragment frag = new CustomDialogFragment();
            return frag;
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View v = inflater.inflate(R.layout.custom_dialog, container, false);
            getDialog().setTitle(R.string.k19_training);
            return v;
        }
    }//class CustomDialogFragment
}//class MainActivity
