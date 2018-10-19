package com.example.contextmenu;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class SecActivity extends AppCompatActivity implements View.OnCreateContextMenuListener, AdapterView.OnItemClickListener{
    int x; double y;
    Intent r;
    TextView tvX;
    ListView lv;
    int type1;
    double first1;
    int n=0;
    double dom1;
    String [] series = new String[20];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sec);

        tvX= (TextView)findViewById(R.id.tvx);
        lv=(ListView) findViewById(R.id.lv);

        if (getIntent()!=null) {
            r = getIntent();
            type1 = r.getIntExtra("type", x);
            first1=r.getDoubleExtra("first",y);
            dom1=r.getDoubleExtra("dom",y);
            series[0] = Double.toString(first1);
            if (type1 == 1) {
                for (int i = 1; i < 20; i++) {
                    series[i] = Double.toString(Double.parseDouble(series[i - 1]) + dom1);
                }
            } else {
                for (int i = 1; i < 20; i++) {
                    series[i] = Double.toString(Double.parseDouble(series[i - 1]) * dom1);
                }
            }
            lv.setOnItemClickListener(this);
            lv.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
            ArrayAdapter<String> adp = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, series);
            lv.setAdapter(adp);
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }


    public boolean onOptionsItemSelected(MenuItem item){
        int id=item.getItemId();
        if (id==R.id.menucreds){
            Toast.makeText(this, "Creator - Maya HbibAlla", Toast.LENGTH_LONG).show();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        adapterView.setOnCreateContextMenuListener(this);
        n=i+1;
    }

    @Override

    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        menu.setHeaderTitle("functions");
        menu.add("first number");
        menu.add("difference / multiplier");
        menu.add("sum");
        menu.add("n");
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected (MenuItem item) {
        String oper = item.getTitle().toString();
        double sum;
        if (oper.equals("first number")) {
            tvX.setText(Double.toString(first1));
            return true;
        }
        if (oper.equals("sum")){
            if (type1 == 1)
                sum = ((n * ((2 * first1) + dom1 * (n - 1))) / 2);
            else {
                if ((first1 != 0) || (dom1 != 0) || (dom1 != 1))
                    sum = (first1 * (Math.pow(dom1, n) - 1)) / (dom1 - 1);
                else {
                    if ((first1 == 0) || (dom1 == 0))
                        sum = 0;
                    else sum = Double.parseDouble(series[n]);
                }
            }
            tvX.setText(Double.toString(sum));
            return true;
        }
        if (oper.equals("difference / multiplier")){
            tvX.setText(Double.toString(dom1));
            return true;
        }
        if (oper.equals("n")){
            tvX.setText(Integer.toString(n));
            return true;
        }
        return super.onContextItemSelected(item);
    }


    public void go1(View view) {
        Intent t = new Intent(this, MainActivity.class);
        startActivity(t);
    }
}
