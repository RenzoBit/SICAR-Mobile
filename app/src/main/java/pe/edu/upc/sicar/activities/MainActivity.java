package pe.edu.upc.sicar.activities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;

import pe.edu.upc.sicar.bean.Persona;
import pe.edu.upc.sicar.util.JSONHttpClient;
import pe.edu.upc.sicar.util.ServiceUrl;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class MainActivity extends ListActivity {

	ArrayList<HashMap<String, String>> listaPersona;
	int idpersona = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		inicializa();
		listaPersona = new ArrayList<HashMap<String, String>>();
		new listarPersona().execute();
	}
	
	private void inicializa() {
		ListView lstPersona = getListView();
		lstPersona.setOnItemClickListener(lstPersonaClick);
	}
	
	private class listarPersona extends AsyncTask<String, String, String> {

		private ProgressDialog progressDialog;

		@Override
		protected String doInBackground(String... params) {
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			JSONHttpClient jsonHttpClient = new JSONHttpClient();
			Persona[] l = jsonHttpClient.Get(ServiceUrl.PERSONA, nameValuePairs, Persona[].class);
			if (l.length > 0)
				for (Persona o : l) {
					HashMap<String, String> mapPersona = new HashMap<String, String>();
					mapPersona.put(Persona.PERSONA_ID, String.valueOf(o.getIdpersona()));
					mapPersona.put(Persona.PERSONA_DI, o.getNombre());
					mapPersona.put(Persona.PERSONA_EX, String.valueOf(o.getTiempo()));
					listaPersona.add(mapPersona);
				}
			return null;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			progressDialog = new ProgressDialog(MainActivity.this);
			progressDialog.setMessage("Cargando transportistas. Espere un momento...");
			progressDialog.show();
		}

		@Override
		protected void onPostExecute(String s) {
			progressDialog.dismiss();
			runOnUiThread(new Runnable() {
				@Override
				public void run() {
					ListAdapter adapter = new SimpleAdapter(MainActivity.this, listaPersona, R.layout.list_item, new String[] { Persona.PERSONA_ID, Persona.PERSONA_DI, Persona.PERSONA_EX }, new int[] { R.id.textViewId, R.id.textViewDi, R.id.textViewEx });
					setListAdapter(adapter);
				}
			});
		}
	}
	
	private AdapterView.OnItemClickListener lstPersonaClick = new AdapterView.OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			Intent intent = new Intent(getApplicationContext(), NuevoViajeActivity.class);
    		intent.putExtra(Persona.PERSONA_ID, Integer.parseInt(((TextView) view.findViewById(R.id.textViewId)).getText().toString()));
    		intent.putExtra(Persona.PERSONA_EX, Integer.parseInt(((TextView) view.findViewById(R.id.textViewEx)).getText().toString()));
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
		}
	};

}