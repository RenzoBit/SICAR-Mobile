package pe.edu.upc.sicar.activities;

import pe.edu.upc.sicar.bean.Orden;
import pe.edu.upc.sicar.bean.Persona;
import pe.edu.upc.sicar.bean.Ubicacion;
import pe.edu.upc.sicar.util.JSONHttpClient;
import pe.edu.upc.sicar.util.ServiceUrl;
import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class EnvioUbicacionActivity extends Activity {

	Button btnFinalizarEnvio;
	boolean enviando = false;
	int idorden = 0;
	int tiempo = 0;
	LocationManager locManager;
	LocationListener locListener;
	Location loca;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.envio_ubicacion);
		inicializa();
		//INICIO
		locManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		//loca = locManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		locListener = new LocationListener() {
			public void onLocationChanged(Location location) {
				loca = location;
				if (!enviando)
					new guardarUbicacion().execute();
			}
			public void onProviderDisabled(String provider) {
			}
			public void onProviderEnabled(String provider) {
			}
			public void onStatusChanged(String provider, int status, Bundle extras) {
			}
		};
		locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, tiempo * 1000, 0, locListener);
		//FIN
	}

	private void inicializa() {
		idorden = getIntent().getIntExtra(Orden.ORDEN_ID, 0);
		tiempo = getIntent().getIntExtra(Persona.PERSONA_EX, 120);
		btnFinalizarEnvio = (Button) findViewById(R.id.btnFinalizarEnvio);
		btnFinalizarEnvio.setOnClickListener(btnFinalizarEnvioClick);
	}

	private View.OnClickListener btnFinalizarEnvioClick = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			finish();
			locManager.removeUpdates(locListener);
			locManager = null;
		}
	};
	
	class guardarUbicacion extends AsyncTask<String, String, String> {
		@Override
		protected void onPostExecute(String s) {
			if (s.equals("0"))
				Toast.makeText(getApplicationContext(), "Error de conexión--", Toast.LENGTH_LONG).show();
			else
				Toast.makeText(getApplicationContext(), "Posición enviada", Toast.LENGTH_SHORT).show();
			enviando = false;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		protected String doInBackground(String... params) {
			enviando = true;
			Ubicacion o = new Ubicacion();
			o.setIdorden(idorden);
			o.setLatitud(String.valueOf(loca.getLatitude()));
			o.setLongitud(String.valueOf(loca.getLongitude()));
			JSONHttpClient jsonHttpClient = new JSONHttpClient();
			o = (Ubicacion) jsonHttpClient.PostObject(ServiceUrl.UBICACION, o, Ubicacion.class);
			if (o == null)
	            return "0";
			return o.getIdorden() + "";
		}
	}

}
