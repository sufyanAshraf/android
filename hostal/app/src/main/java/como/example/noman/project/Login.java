package como.example.noman.project;
import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;


public class Login extends Activity {

    TextView emailAddress;
    TextView passwordText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        WebService.getInstance(this).clearQueue();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailAddress = findViewById(R.id.emailText);
        passwordText = findViewById(R.id.passText);

        findViewById(R.id.NoAccount).setOnClickListener(new ClickListener(this, getApplicationContext()));
        findViewById(R.id.signinButton).setOnClickListener(new ClickListener(this, getApplicationContext()));
        findViewById(R.id.goToHome).setOnClickListener(new ClickListener(this, getApplicationContext()));
    }

    @Override
    public void onBackPressed() {
    }
}
