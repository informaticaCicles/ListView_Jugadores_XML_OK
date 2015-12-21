package com.example.evanmartnez.listview;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Xml;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;
import org.xmlpull.v1.XmlSerializer;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String XML_PLAYERS_LIST_TAG = "PlayersList";
    private static final String XML_PLAYER_TAG = "Player";
    private static final String XML_PLAYER_NAME = "Name";
    private static final String XML_PLAYER_POSITION = "Position";
    private static final String XML_PLAYER_PHOTO = "Photo";
    private static final String XML_PLAYER_WEB = "Web";

    static final String XML_FILE_NAME = "JugadoresXML.xml";

    private ArrayList<Jugador> jugadores = new ArrayList<Jugador>();

    private ListView lstOpciones;
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        writeToXML();
        ReadFileAsXML();

        AdaptadorTitulares adaptador = new AdaptadorTitulares(this, jugadores);
        lstOpciones	= (ListView)findViewById(R.id.LstOpciones);
        lstOpciones.setAdapter(adaptador);
        webView = (WebView) findViewById(R.id.webView);

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        lstOpciones.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Jugador jugador = jugadores.get(position);
                String str = ("Ha seleccionado a " + jugador.getNombre() + " que juega de " + jugador.getPosicion());
                webView.loadUrl(jugador.getUrl());
                Toast.makeText(getBaseContext(), str, Toast.LENGTH_LONG).show();

            }
        });

    }

    private void ReadFileAsXML(){
        try{
            BufferedReader fin = new BufferedReader(new InputStreamReader(openFileInput(XML_FILE_NAME)));

            XmlPullParser parser = XmlPullParserFactory.newInstance().newPullParser(); //Creamos una instancia parser
            parser.setInput(fin); //Para leer del fichero

            String nombre = "", posicion = "", web = "";
            int foto = 0;

            int eventType = parser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT){
                switch (eventType){
                    case XmlPullParser.START_TAG:
                        if (parser.getName().equals(XML_PLAYER_TAG)) {
                            nombre = parser.getAttributeValue(null, XML_PLAYER_NAME);
                            posicion = parser.getAttributeValue(null, XML_PLAYER_POSITION);
                            foto = Integer.parseInt(parser.getAttributeValue(null, XML_PLAYER_PHOTO));
                            web = parser.getAttributeValue(null, XML_PLAYER_WEB);
                            jugadores.add(new Jugador(nombre, posicion, foto, web));
                        }
                        break;
                    default:
                        break;
                }
                parser.next();
                eventType = parser.getEventType();
            }
            fin.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void writeToXML(){

        XmlSerializer serializer = Xml.newSerializer();
        StringWriter writer = new StringWriter(); //Utilizaremos un StringWriter para la salida, después lo escribiremos en el fichero
        try {
            serializer.setOutput(writer);
            serializer.startDocument(null, null); // void startDocument(String encoding, Boolean standalone)

            serializer.startTag(null, XML_PLAYERS_LIST_TAG); //XmlSerializer startTag(String namespace, String name)
            serializer.startTag(null, XML_PLAYER_TAG);
            serializer.attribute(null, XML_PLAYER_NAME, "Gareth Bale");
            serializer.attribute(null, XML_PLAYER_POSITION, "Centrocampista");
            serializer.attribute(null, XML_PLAYER_PHOTO, String.valueOf(R.mipmap.bale));
            serializer.attribute(null, XML_PLAYER_WEB, "http://www.laliga.es/en/player/liga-bbva/bale");
            serializer.endTag(null, XML_PLAYER_TAG);

            serializer.startTag(null, XML_PLAYER_TAG);
            serializer.attribute(null, XML_PLAYER_NAME, "Cristiano Ronaldo");
            serializer.attribute(null, XML_PLAYER_POSITION, "Delantero");
            serializer.attribute(null, XML_PLAYER_PHOTO, String.valueOf(R.mipmap.cristiano));
            serializer.attribute(null, XML_PLAYER_WEB, "http://www.laliga.es/en/player/liga-bbva/cristiano-ronaldo");
            serializer.endTag(null, XML_PLAYER_TAG);

            serializer.startTag(null, XML_PLAYER_TAG);
            serializer.attribute(null, XML_PLAYER_NAME, "Karim Benzema");
            serializer.attribute(null, XML_PLAYER_POSITION, "Delantero");
            serializer.attribute(null, XML_PLAYER_PHOTO, String.valueOf(R.mipmap.benzema));
            serializer.attribute(null, XML_PLAYER_WEB, "http://www.laliga.es/en/player/liga-bbva/benzema");
            serializer.endTag(null, XML_PLAYER_TAG);

            serializer.startTag(null, XML_PLAYER_TAG);
            serializer.attribute(null, XML_PLAYER_NAME, "James Rodríguez");
            serializer.attribute(null, XML_PLAYER_POSITION, "Centrocampista");
            serializer.attribute(null, XML_PLAYER_PHOTO, String.valueOf(R.mipmap.james));
            serializer.attribute(null, XML_PLAYER_WEB, "http://www.laliga.es/en/player/liga-bbva/james-rodriguez");
            serializer.endTag(null, XML_PLAYER_TAG);

            serializer.startTag(null, XML_PLAYER_TAG);
            serializer.attribute(null, XML_PLAYER_NAME, "Keylor Navas");
            serializer.attribute(null, XML_PLAYER_POSITION, "Portero");
            serializer.attribute(null, XML_PLAYER_PHOTO, String.valueOf(R.mipmap.keylor));
            serializer.attribute(null, XML_PLAYER_WEB, "http://www.laliga.es/en/player/liga-bbva/keylor-navas");
            serializer.endTag(null, XML_PLAYER_TAG);

            serializer.startTag(null, XML_PLAYER_TAG);
            serializer.attribute(null, XML_PLAYER_NAME, "Marcelo");
            serializer.attribute(null, XML_PLAYER_POSITION, "Defensa");
            serializer.attribute(null, XML_PLAYER_PHOTO, String.valueOf(R.mipmap.marcelo));
            serializer.attribute(null, XML_PLAYER_WEB, "http://www.laliga.es/en/player/liga-bbva/marcelo");
            serializer.endTag(null, XML_PLAYER_TAG);

            serializer.startTag(null, XML_PLAYER_TAG);
            serializer.attribute(null, XML_PLAYER_NAME, "Luka Modric");
            serializer.attribute(null, XML_PLAYER_POSITION, "Centrocampista");
            serializer.attribute(null, XML_PLAYER_PHOTO, String.valueOf(R.mipmap.modric));
            serializer.attribute(null, XML_PLAYER_WEB, "http://www.laliga.es/en/player/liga-bbva/modric");
            serializer.endTag(null, XML_PLAYER_TAG);

            serializer.startTag(null, XML_PLAYER_TAG);
            serializer.attribute(null, XML_PLAYER_NAME, "Sergio Ramos");
            serializer.attribute(null, XML_PLAYER_POSITION, "Defensa");
            serializer.attribute(null, XML_PLAYER_PHOTO, String.valueOf(R.mipmap.ramos));
            serializer.attribute(null, XML_PLAYER_WEB, "http://www.laliga.es/en/player/liga-bbva/sergio-ramos");
            serializer.endTag(null, XML_PLAYER_TAG);
            serializer.endTag(null, XML_PLAYERS_LIST_TAG);

            serializer.endDocument();
            serializer.flush();
        } catch(Exception e) {
            Log.e("Ficheros XML", "Error generando contenido XML");
        }

        try {
            OutputStreamWriter fout = new OutputStreamWriter(
                    openFileOutput(XML_FILE_NAME, Context.MODE_PRIVATE));

            fout.write(writer.toString());
            fout.close();

        } catch(Exception e) {
            Log.e("Ficheros XML","Error escribiendo fichero XML");
        }
    }

    class AdaptadorTitulares extends ArrayAdapter<Jugador> {

        public AdaptadorTitulares(Context context, ArrayList<Jugador> datos)	{
            super(context, R.layout.listitem_jugador, datos);
        }

        public View getView(int	position, View convertView, ViewGroup parent)	{
            LayoutInflater inflater = LayoutInflater.from(getContext());
            View item = inflater.inflate(R.layout.listitem_jugador, null);

            ImageView imgImagen = (ImageView)item.findViewById(R.id.imageView);
            imgImagen.setImageResource(jugadores.get(position).getIdFoto());

            TextView lblNombre = (TextView)item.findViewById(R.id.txtNombre);
            lblNombre.setText(jugadores.get(position).getNombre());

            TextView lblPosicion = (TextView)item.findViewById(R.id.txtPosicion);
            lblPosicion.setText(jugadores.get(position).getPosicion());

            return(item);
        }
    }
}
