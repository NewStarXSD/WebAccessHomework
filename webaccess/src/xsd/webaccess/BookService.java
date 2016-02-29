package xsd.webaccess;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * 对Person对象的sql操作(增删改查)
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
	 * 添加Person
	 * 
	 * @param person
	 */
	public void addBook(Book book) {
		// 对读和写操作的方法
		// 如果当我们二次调用这个数据库方法,他们调用的是同一个数据库对象,在这里的方法创建的数据调用对象是用的同一个对象
		SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
		db.execSQL("insert into Book(期刊名称,日期) values(?,?)", new Object[] {
				book.name, book.date });
	}

	/**
	 * 修改Person
	 * 
	 * @param person
	 */
	public void modifyBook(Book book) {
		SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
		db.execSQL("update Book set 期刊名称=? where 日期=?", new Object[] {
				book.name, book.date });
	}

	/**
	 * 删除Person
	 * 
	 * @param person
	 */
	public void deleteBook(String name) {
		SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
		db.execSQL("delete from Book where 期刊名称=?", new Object[] { name });
	}

	/**
	 * 根据person的Id查询Person对象
	 * 
	 * @param id
	 *            Person的ID
	 * @return Person
	 */
	public Book findBook(String name) {
		// 只对读的操作的方法
		SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
		// Cursor游标的位置,默认是0,所有在操作时一定要先cursor.moveToFirst()一下,定位到第一条记录
		// Cursor cursor =
		// db.rawQuery("select * from person Where personid=?",new
		// String[]{id.toString()});
		Cursor cursor = db.query("Book", new String[] { "期刊名称", "日期" },
				"期刊名称=?", new String[] { name }, null, null, null);
		if (cursor.moveToFirst()) {
			String ansname = cursor.getString(cursor.getColumnIndex("期刊名称"));
			String date = cursor.getString(cursor.getColumnIndex("日期"));
			return new Book(ansname, date);
		}
		return null;
	}

	public List<Book> findBookList(Integer start, Integer length) {
		List<Book> books = new ArrayList<Book>();
		// 只对读的操作的方法
		SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
		Cursor cursor = db.rawQuery("select * from Book limit ?,?",
				new String[] { start.toString(), length.toString() });
		cursor = db.query("Book", null, null, null, null, null, null, start
				+ "," + length);
		while (cursor.moveToNext()) {
			String name = cursor.getString(cursor.getColumnIndex("期刊名称"));
			String date = cursor.getString(cursor.getColumnIndex("日期"));
			books.add(new Book(name, date));
		}
		return books;
	}

	/**
	 * 返回Person的记录总个数
	 * 
	 * @return
	 */
	public int getCount() {
		SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
		Cursor cursor = db.rawQuery("select count(0) from Book ", null);
		// 这里必定有一条记录.所有不用判断,直接移到第一条.
		cursor.moveToFirst();
		// 这里只有一个字段时候 返回
		return cursor.getInt(0);
	}

}
