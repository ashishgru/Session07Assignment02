package ashishrpa.session07assignment02;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.MultiAutoCompleteTextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private AutoCompleteTextView autoCompleteTextView;
    //MultiAutoCompleteTextView text1;
    private DBHelper mydb ;
    //String[] languages={"Android ","java","IOS","SQL","JDBC","Web services"};
    String[] productsName={"HP inkjet Printer","Cannon Camera","Bravia TV","Apple 7","Samsung Note 2","I Watch"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mydb = new DBHelper(this);

        AsyncTaskRunner runner = new AsyncTaskRunner();
        //String sleepTime = "5";
        runner.execute(productsName);


        ArrayList array_list = mydb.getAllProducts();
        Log.e("products Size ", String.valueOf(array_list.size()));
        ArrayAdapter arrayAdapter=new ArrayAdapter(this,android.R.layout.simple_list_item_1, array_list);

        autoCompleteTextView = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView1);
        autoCompleteTextView.setAdapter(arrayAdapter);
        autoCompleteTextView.setThreshold(1);

        //text1=(MultiAutoCompleteTextView)findViewById(R.id.multiAutoCompleteTextView1);
        //ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,languages);



        //text1.setAdapter(adapter);
        //text1.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
    }

    //AsynTask Start
    private class AsyncTaskRunner extends AsyncTask<String, String, String> {

        private String resp;
        ProgressDialog progressDialog;

        @Override
        protected String doInBackground(String... params) {
            publishProgress("Fetching Products..."); // Calls onProgressUpdate()
            try {
                for(int i=0;i<productsName.length;i++){
                    mydb.insertProduct(productsName[i]);
                    Log.e("productsName ",productsName[i]);
                }
            } catch (Exception e) {
                e.printStackTrace();
                resp = e.getMessage();
            }
            return resp;
        }


        @Override
        protected void onPostExecute(String result) {
            // execution of result of Long time consuming operation
            progressDialog.dismiss();
            autoCompleteTextView.setText(result);
        }


        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(MainActivity.this,
                    "ProgressDialog",
                    "Wait for Products loading...");
        }


        @Override
        protected void onProgressUpdate(String... text) {
            autoCompleteTextView.setText(text[0]);

        }
    }

    //AsynTask End

}
