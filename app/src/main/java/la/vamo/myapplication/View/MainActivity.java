package la.vamo.myapplication.View;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import la.vamo.myapplication.R;
import la.vamo.myapplication.WebServices.WebServiceControle;
import la.vamo.myapplication.WebServices.content.Item;
import la.vamo.myapplication.WebServices.content.TesteSquidexInfo;

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_REGISTRO = "a.vamo.myapplication.View.MainActivity.EXTRA_REGISTRO";
    private ListView lvTeste;
    private SwipeRefreshLayout srTeste;
    private FloatingActionButton fabConfirmar;
    private TesteSquidexInfo listTeste;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //
        inicializaComponenetes();
        criaAdapterLista();
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        carregaListaTeste();
    }

    private void inicializaComponenetes()
    {
        lvTeste = findViewById(R.id.lvTeste);
        srTeste = findViewById(R.id.srTeste);
        fabConfirmar = findViewById(R.id.fabConfirmar);
        srTeste.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener()
        {
            @Override
            public void onRefresh()
            {
                carregaListaTeste();           }
        });
        //
        fabConfirmar.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(MainActivity.this, CadastroActivity.class));
            }
        });
        //
        lvTeste.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                Item item = listTeste.getItems()[position];
                Intent intent = new Intent(MainActivity.this, CadastroActivity.class);
                intent.putExtra(EXTRA_REGISTRO, item);
                startActivity(intent);
            }
        });
    }

    private void criaAdapterLista()
    {
        lvTeste.setAdapter(new ArrayAdapter<Object>(this, 0)
                                   {
                                       class ViewHolder
                                       {
                                           TextView tvNome;
                                           TextView tvCidade;
                                           TextView tvEstado;
                                           TextView tvBairro;
                                       }

                                       @Override
                                       public int getCount()
                                       {
                                           if (listTeste != null)
                                               return listTeste.getTotal().intValue();
                                           else
                                               return 0;
                                       }

                                       @NonNull
                                       @Override
                                       public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
                                       {
                                           ViewHolder viewHolder;
                                           Item item = listTeste.getItems()[position];
                                           //
                                           if (convertView == null)
                                           {
                                               convertView = getLayoutInflater().inflate(R.layout.item_listagem, null);
                                               viewHolder = new ViewHolder();
                                               convertView.setTag(viewHolder);
                                               //
                                               viewHolder.tvNome = convertView.findViewById(R.id.tvNome);
                                               viewHolder.tvCidade = convertView.findViewById(R.id.tvCidade);
                                               viewHolder.tvEstado = convertView.findViewById(R.id.tvEstado);
                                               viewHolder.tvBairro = convertView.findViewById(R.id.tvBairro);
                                           }
                                           else
                                               viewHolder = (ViewHolder) convertView.getTag();
                                           //
                                           viewHolder.tvNome.setText(item.getData().getNome().getIv());
                                           viewHolder.tvCidade.setText(item.getData().getCidade().getIv());
                                           viewHolder.tvEstado.setText(item.getData().getEstado().getIv());
                                           viewHolder.tvBairro.setText(item.getData().getEstado().getIv());
                                           //
                                           return convertView;
                                       }
                                   }
        );
    }

    private void carregaListaTeste()
    {
        srTeste.setRefreshing(true);
        new WebServiceControle().carregaListaTeste(this, new WebServiceControle.CarregaListaTesteListener()
        {
            @Override
            public void onResultOk(TesteSquidexInfo teste)
            {
                listTeste = teste;
                ((ArrayAdapter) lvTeste.getAdapter()).notifyDataSetChanged();
                srTeste.setRefreshing(false);
            }

            @Override
            public void onErro()
            {
                listTeste = null;
                ((ArrayAdapter) lvTeste.getAdapter()).notifyDataSetChanged();
                srTeste.setRefreshing(false);
            }
        });

    }
}
