package xsd.webaccess;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class MainActivity extends Activity {

	String del;
	SimpleAdapter adapter;
	ListView listView;
	List<HashMap<String, Object>> data;
	Book b2;
	int pos;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		BookService db = new BookService(MainActivity.this);
		List<Book> bl = db.findBookList(0, db.getCount());
		data = new ArrayList<HashMap<String, Object>>();
		for (Book bs : bl) {
			HashMap<String, Object> item = new HashMap<String, Object>();
			item.put("期刊名称", bs.name);
			item.put("日期", bs.date);
			data.add(item);
		}
		adapter = new SimpleAdapter(this, data, R.layout.item, new String[] {
				"期刊名称", "日期" }, new int[] { R.id.qkname, R.id.dhdate });
		listView = (ListView) this.findViewById(R.id.listView);
		Button insert = (Button) findViewById(R.id.insert);
		insert.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				Intent toinsert = new Intent();
				toinsert.setClass(MainActivity.this, InsertActivity.class);
				startActivityForResult(toinsert, 1);
			}
		});

		Button delete = (Button) findViewById(R.id.delete);
		delete.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				BookService db = new BookService(MainActivity.this);
				db.deleteBook(del);
				Toast.makeText(MainActivity.this, del + "已经被删除",
						Toast.LENGTH_SHORT).show();
				data.remove(pos);
				adapter.notifyDataSetChanged();
			}
		});
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new ItemClickListener());
	}

	private final class ItemClickListener implements OnItemClickListener {
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			ListView listView = (ListView) parent;
			HashMap<String, Object> data = (HashMap<String, Object>) listView
					.getItemAtPosition(position);
			String name = data.get("期刊名称").toString();
			del = name;
			pos = position;
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent da) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, da);
		if (requestCode == 1) {
			if (resultCode == 1) {
				Bundle bundle = da.getExtras();
				b2 = (Book) bundle.getSerializable("book");
				HashMap<String, Object> item = new HashMap<String, Object>();
				item.put("期刊名称", b2.name);
				item.put("日期", b2.date);
				data.add(item);
				adapter.notifyDataSetChanged();
			}
		}
	}

}
