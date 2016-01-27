package com.httpandjson.wagner.httpandjson;

import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {

    //Método onCreate é executado quando a Activity e criada.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        /*
            Cria a activity que será exibida para o usuário. O método setContentView serve para
            informar qual a activity que será executada.
         */
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        //Obtém a referência dos componentes da activity.
        TextView loginText = (TextView) findViewById(R.id.login_text);
        TextView nameText = (TextView) findViewById(R.id.name_text);
        TextView companyText = (TextView) findViewById(R.id.company_text);
        TextView cityText = (TextView) findViewById(R.id.city_text);

        //TODO
        //Verificar este método.
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        //Obtém a resposta da requisição retornado pelo método makeRequest.
        String response = makeRequest("https://api.github.com/users/wdmeida");

        try {
            //Faz o parser do json recebido e obtém os atributos.
            JSONObject json = new JSONObject(response);
            String login = json.getString("login");
            String name = json.getString("name");
            String company = json.getString("company");
            String city = json.getString("location");

            //Seta os atributos recebidos nos componentes da activity.
            nameText.setText(getString(R.string.name_label,name));
            loginText.setText(login);
            companyText.setText(getString(R.string.company_label, company));
            cityText.setText(getString(R.string.city_label, city));

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }//onCreate()

    //Faz a requisição a url recebida e retorna a resposta da requisição.
    private String makeRequest(String urlAdress){
        HttpsURLConnection con = null;
        URL url = null;
        String response = null;

        try {
            //Inicia um objeto do tipo URL para realizar a conexão com o endereço recebido e inicializa a conexão.
            url = new URL(urlAdress);
            con = (HttpsURLConnection) url.openConnection();

            //Obtém a resposta da conexão através do método readStream.
            response = readStream(con.getInputStream());
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            con.disconnect();
        }
        return response;
    }//makeRequest()

    //Obtém as informações recebidas no fluxo de dados recebido por parãmetro.
    private String readStream(InputStream in){
        BufferedReader reader = null;
        StringBuilder builder = new StringBuilder();
        try {
            reader = new BufferedReader(new InputStreamReader(in));
            String line = null;
            while ((line = reader.readLine()) != null){
                builder.append(line + "\n");
            }
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            if (reader != null){
                try {
                    reader.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
        return builder.toString();
    }//readStream()
}//class MainActivity
