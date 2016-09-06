package auth0.com.socialloginsample;

import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.concurrent.Callable;

public class CallbackActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_callback);
        checkForAuth0Data();
    }

    private void checkForAuth0Data() {
        final Uri data = this.getIntent().getData();
        final TextView tv = (TextView) findViewById(R.id.callback_message);

        tv.setText(tv.getText() + System.getProperty("line.separator") + "starting- " + data.getScheme() + " -- " + data.getAuthority() + " -- " + data.getQuery());

        if (data != null && data.getScheme().equals("oauthlogin") && data.getAuthority().equals("redirect")) {
            final Uri.Builder uriBuilder = new Uri.Builder();
            uriBuilder.scheme("http")
                    .authority("192.168.2.54")
                    .appendPath("Callback")
                    .query(data.getQuery());

            StringRequest stringRequest = new StringRequest(Request.Method.GET, uriBuilder.toString(),
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            tv.setText(tv.getText() + System.getProperty("line.separator") + response);
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    tv.setText(tv.getText() + System.getProperty("line.separator") + error.getMessage());
                }
            });

            Volley.newRequestQueue(getApplicationContext()).add(stringRequest);
        }
    }
}
