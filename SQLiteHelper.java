public class SQLiteHelper extends SQLiteOpenHelper{
	
	private static final int DATABASE_VERSION = 1;
	public static final String DATABASE_NAME = "SQLiteDatabase.db";
	public static final String TABLE_NAME = "PEOPLE";
	public static final String COLUMN_ID = "ID";
	public static final String COLUMN_FIRST_NAME = "FIRST_NAME";
	public static final String COLUMN_LAST_NAME = "LAST_NAME";
	private SQLiteDatabase database;
	
	public SQLiteHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("create table " + TABLE_NAME + " ( " + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_FIRST_NAME + " VARCHAR, " + COLUMN_LAST_NAME + " VARCHAR);");
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
		onCreate(db);
	}
	
	public void insertRecord(ContactModel contact) {
    database = this.getReadableDatabase();
    ContentValues contentValues = new ContentValues();
    contentValues.put(COLUMN_FIRST_NAME, contact.getFirstName());
    contentValues.put(COLUMN_LAST_NAME, contact.getLastName());
    database.insert(TABLE_NAME, null, contentValues);
    database.close();
	}
	
	public void updateRecord(ContactModel contact) {
    database = this.getReadableDatabase();
    ContentValues contentValues = new ContentValues();
    contentValues.put(COLUMN_FIRST_NAME, contact.getFirstName());
    contentValues.put(COLUMN_LAST_NAME, contact.getLastName());
    database.update(TABLE_NAME, contentValues, COLUMN_ID + " = ?", new String[]{contact.getID()});
    database.close();
	}
	
	public void deleteRecord(ContactModel contact) {
    database = this.getReadableDatabase();
    database.delete(TABLE_NAME, COLUMN_ID + " = ?", new String[]{contact.getID()});
    database.close();
	}
	
		public ArrayList<ContactModel> getAllRecords() {
		database = this.getReadableDatabase();
	Cursor cursor = database.query(TABLE_NAME, null, null, null, null, null, null);
	ArrayList<ContactModel> contacts = new ArrayList<ContactModel>();
		ContactModel contactModel;
		if (cursor.getCount() > 0) {
			for (int i = 0; i < cursor.getCount(); i++) {
				cursor.moveToNext();
				contactModel = new ContactModel();
				contactModel.setID(cursor.getString(0));
				contactModel.setFirstName(cursor.getString(1));
				contactModel.setLastName(cursor.getString(2));
				contacts.add(contactModel);
			}
		}
		cursor.close();
		database.close();
		return contacts;
	}
	
	public ArrayList<ContactModel> getAllRecordsAlternate() {
    database = this.getReadableDatabase();
    Cursor cursor = database.rawQuery("SELECT * FROM " + TABLE_NAME, null);
    ArrayList<ContactModel> contacts = new ArrayList<ContactModel>();
    ContactModel contactModel;
    if (cursor.getCount() > 0) {
        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToNext();
            contactModel = new ContactModel();
            contactModel.setID(cursor.getString(0));
            contactModel.setFirstName(cursor.getString(1));
            contactModel.setLastName(cursor.getString(2));
            contacts.add(contactModel);
        }
    }
    cursor.close();
    database.close();
    return contacts;
	}
	
	
}

           
