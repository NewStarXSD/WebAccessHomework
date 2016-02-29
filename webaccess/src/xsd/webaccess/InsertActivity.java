package xsd.webaccess;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class InsertActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.insert);
		Button add = (Button) findViewById(R.id.ok);

		add.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				EditText et1 = (EditText) findViewById(R.id.name);
				EditText et2 = (EditText) findViewById(R.id.date);
				String str1 = et1.getText().toString();
				String str2 = et2.getText().toString();
				BookService b1 = new BookService(InsertActivity.this);
				Book book = new Book();
				book.name = str1;
				book.date = str2;
				b1.addBook(book);
				Toast.makeText(InsertActivity.this, "Ìí¼Ó³É¹¦", Toast.LENGTH_SHORT).show();
				Intent intent = new Intent();
	            Bundle myBundle = new Bundle();
	            myBundle.putSerializable("book",book); 
	            intent.putExtras(myBundle);
                setResult(1,intent);
                InsertActivity.this.finish();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.insert, menu);
		return true;
	}

}
