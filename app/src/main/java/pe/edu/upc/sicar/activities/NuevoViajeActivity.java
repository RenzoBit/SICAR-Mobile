package pe.edu.upc.sicar.activities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import pe.edu.upc.sicar.bean.Orden;
import pe.edu.upc.sicar.bean.Persona;
import pe.edu.upc.sicar.util.JSONHttpClient;
import pe.edu.upc.sicar.util.ServiceUrl;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class NuevoViajeActivity extends ListActivity {

	ArrayList<HashMap<String, String>> listaOrden;
	int idorden = 0;
	int idpersona = 0;
	int tiempo = 120;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.nuevo_viaje);
		inicializa();
		listaOrden = new ArrayList<HashMap<String, String>>();
		new listarOrden().execute();
	}

	private void inicializa() {
		idpersona = getIntent().getIntExtra(Persona.PERSONA_ID, 0);
		tiempo = getIntent().getIntExtra(Persona.PERSONA_EX, 120);
		ListView lstOrden = getListView();
		lstOrden.setOnItemClickListener(lstOrdenClick);
	}

	private class listarOrden extends AsyncTask<String, String, String> {

		private ProgressDialog progressDialog;

		@Override
		protected String doInBackground(String... params) {
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			nameValuePairs.add(new BasicNameValuePair("id", String.valueOf(idpersona)));
			JSONHttpClient jsonHttpClient = new JSONHttpClient();
			Orden[] l = jsonHttpClient.Get(ServiceUrl.ORDEN, nameValuePairs, Orden[].class);
			if (l.length > 0)
				for (Orden o : l) {
					HashMap<String, String> mapOrden = new HashMap<String, String>();
					mapOrden.put(Orden.ORDEN_ID, String.valueOf(o.getIdorden()));
					mapOrden.put(Orden.ORDEN_DI, o.getNumero());
					listaOrden.add(mapOrden);
				}
			return null;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			progressDialog = new ProgressDialog(NuevoViajeActivity.this);
			progressDialog.setMessage("Cargando órdenes. Espere un momento...");
			progressDialog.show();
		}

		@Override
		protected void onPostExecute(String s) {
			progressDialog.dismiss();
			runOnUiThread(new Runnable() {
				@Override
				public void run() {
					ListAdapter adapter = new SimpleAdapter(NuevoViajeActivity.this, listaOrden, R.layout.list_item, new String[] { Orden.ORDEN_ID, Orden.ORDEN_DI }, new int[] { R.id.textViewId, R.id.textViewDi });
					setListAdapter(adapter);
				}
			});
		}
	}

	private AdapterView.OnItemClickListener lstOrdenClick = new AdapterView.OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			idorden = Integer.parseInt(((TextView) view.findViewById(R.id.textViewId)).getText().toString());
			if (obtenerStatusGPS()) {
				if (isOnline()) {
					Intent intent = new Intent(getApplicationContext(), EnvioUbicacionActivity.class);
		            intent.putExtra(Orden.ORDEN_ID, idorden);
		            intent.putExtra(Persona.PERSONA_EX, tiempo);
					intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	                startActivity(intent);
	                finish();
				} else
					Toast.makeText(getApplicationContext(), "Sin conexión a internet", Toast.LENGTH_SHORT).show();
			} else
				Toast.makeText(getApplicationContext(), "Active el GPS del dispositivo", Toast.LENGTH_SHORT).show();
		}
	};
	
	private boolean obtenerStatusGPS() {
		ContentResolver contentResolver = getBaseContext().getContentResolver();
		return Settings.Secure.isLocationProviderEnabled(contentResolver, LocationManager.GPS_PROVIDER);
	}

	public boolean isOnline() {
		NetworkInfo netInfo = ((ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
		return (netInfo != null && netInfo.isConnectedOrConnecting());
	}

}