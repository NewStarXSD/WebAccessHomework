package xsd.webaccess;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * ��Person�����sql����(��ɾ�Ĳ�)
 * 
 * @author Administrator
 * 
 */
public class BookService {

	private MySql dbOpenHelper;

	public BookService(Context context) {
		dbOpenHelper = new MySql(context);
	}

	/**
	 * ���Person
	 * 
	 * @param person
	 */
	public void addBook(Book book) {
		// �Զ���д�����ķ���
		// ��������Ƕ��ε���������ݿⷽ��,���ǵ��õ���ͬһ�����ݿ����,������ķ������������ݵ��ö������õ�ͬһ������
		SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
		db.execSQL("insert into Book(�ڿ�����,����) values(?,?)", new Object[] {
				book.name, book.date });
	}

	/**
	 * �޸�Person
	 * 
	 * @param person
	 */
	public void modifyBook(Book book) {
		SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
		db.execSQL("update Book set �ڿ�����=? where ����=?", new Object[] {
				book.name, book.date });
	}

	/**
	 * ɾ��Person
	 * 
	 * @param person
	 */
	public void deleteBook(String name) {
		SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
		db.execSQL("delete from Book where �ڿ�����=?", new Object[] { name });
	}

	/**
	 * ����person��Id��ѯPerson����
	 * 
	 * @param id
	 *            Person��ID
	 * @return Person
	 */
	public Book findBook(String name) {
		// ֻ�Զ��Ĳ����ķ���
		SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
		// Cursor�α��λ��,Ĭ����0,�����ڲ���ʱһ��Ҫ��cursor.moveToFirst()һ��,��λ����һ����¼
		// Cursor cursor =
		// db.rawQuery("select * from person Where personid=?",new
		// String[]{id.toString()});
		Cursor cursor = db.query("Book", new String[] { "�ڿ�����", "����" },
				"�ڿ�����=?", new String[] { name }, null, null, null);
		if (cursor.moveToFirst()) {
			String ansname = cursor.getString(cursor.getColumnIndex("�ڿ�����"));
			String date = cursor.getString(cursor.getColumnIndex("����"));
			return new Book(ansname, date);
		}
		return null;
	}

	public List<Book> findBookList(Integer start, Integer length) {
		List<Book> books = new ArrayList<Book>();
		// ֻ�Զ��Ĳ����ķ���
		SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
		Cursor cursor = db.rawQuery("select * from Book limit ?,?",
				new String[] { start.toString(), length.toString() });
		cursor = db.query("Book", null, null, null, null, null, null, start
				+ "," + length);
		while (cursor.moveToNext()) {
			String name = cursor.getString(cursor.getColumnIndex("�ڿ�����"));
			String date = cursor.getString(cursor.getColumnIndex("����"));
			books.add(new Book(name, date));
		}
		return books;
	}

	/**
	 * ����Person�ļ�¼�ܸ���
	 * 
	 * @return
	 */
	public int getCount() {
		SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
		Cursor cursor = db.rawQuery("select count(0) from Book ", null);
		// ����ض���һ����¼.���в����ж�,ֱ���Ƶ���һ��.
		cursor.moveToFirst();
		// ����ֻ��һ���ֶ�ʱ�� ����
		return cursor.getInt(0);
	}

}
