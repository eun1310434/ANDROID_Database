/*==================================================================================================
□ INFORMATION
  ○ Data : Dienstag - 05/06/18
  ○ Mail : eun1310434@naver.com
  ○ WebPage : https://eun1310434.github.io/
  ○ Reference
     - Do it android app Programming


□ Function
   ○ Process
      01) MainActivity : onCreate() -> DBManager : DBManager()
                                    -> DBManager : insertRecord()
                                    -> DBManager : deleteRecordParam()
                                    -> DBManager : searchTable()

      02) DBManager : DBManager() -> DatabaseHelper : DatabaseHelper()
          DBManager : insertRecord() -> SQLiteDatabase : execSQL()
          DBManager : deleteRecordParam() -> SQLiteDatabase : execSQL()
          DBManager : searchTable() -> SQLiteDatabase : execSQL()

      03) DatabaseHelper : DatabaseHelper() -> DatabaseHelper : onCreate()
          DatabaseHelper : onCreate() -> DatabaseHelper : CreateTable()
          DatabaseHelper : CreateTable() -> SQLiteDatabase : execSQL()
          DatabaseHelper : DeleteTable() -> SQLiteDatabase : execSQL()


   ○ Unit
      - public class MainActivity
        01) protected void onCreate(Bundle savedInstanceState)

      - public class DBManager
        01) public DBManager(String _name, Context _context, OnListener _listener)
        02) public int getVersion()
        03) public String getPath()
        04) public void insertRecord(String _tableName, String _userName, int _age, String _phoneNumber)
        05) public int updateRecordParam_PhoneNumber(String _tableName, String _userName, int _age, String _phoneNumber)
        06) public int updateRecordParam_Age(String _tableName, String _id, int _age)
        07) public int deleteRecordParam(String _tableName, String _id)
        08) public void searchTable(String _tableName)

      - public interface  DBManager.OnListener
        01) public void printLog(String tag, String msg)

      - public class DatabaseHelper extends SQLiteOpenHelper
        01) public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version)
        02) public void onCreate(SQLiteDatabase db)
        03) public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
        04) public void CreateTable(SQLiteDatabase db)
        05) public void DeleteTable(SQLiteDatabase db, int _newVersion)

  ○ SQLiteOpenHelper
    - 기능
      01) 데이터베이스를 만들거나 열기 위해 필요한 일들을 도와주는 역할을 함
      02) 앱 DB의 업데이트 시 배포되어진 단말기들의 업데이트를 도와줌
            -> onUpgrade()메소드 호출
      03) 새롭게 설치하는 사용자에게는 이전의 DB가 아닌 업데이트된 DB를 설치
            -> onCreate()메소드 호출
    - 메소드
      01) public SQLiteOpenHelper (Context context, String name, SQLiteDatabase.CursorFactory factory, int version)
      02) public abstract void onCreate(SQLiteDatavase db)
      03) public abstract void onOpen(SQLiteDatabase db)
      04) public abstract void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)

□ Study
  ○ DataBase
    - 데이터를 관리하기 위해 만들어진 프로그램 (Oracle, MS-AQL, MySAL, Postgres, SQLite 등)
    - 데이터를 잘 관리(입력, 수정, 삭제, 검색 등) 하려고 만든 프로그램
    - 질의문(Query) : 데이터베이스에서 데이터를 관리 할 때 사용하는 명령문
    - 데이터베이스 생성
       01) 데이터베이스 접속
       02) show databases
       03) create database 데이터베이스명
       04) drop database 데이터베이스명
       05) use 데이터베이스명
    - 테이블 생성
       01) create table 테이블명(필드1 자료형, 필드2 자료형, 필드3 자료형,...)
            : 자바와 반대의 순서로 필드 선언
       02) show tables
       03) desc 테이블명
    - 자바 자료형 / MySql 자료형
       01) boolean / tinyint
       02) byte / tinyint
       03) char / char, varchar
       04) short / smallint
       05) int / int, integer
       06) long / bigint
       07) float / float
       08) double / double
       09) String / char(n), varchar(n), BLOB, text
       10) Date / date
       11) DateTime / datetime
       12) Timestamp / timestamp
       13) Time / time
    - 질의문
      01) 입력 질의문
            : insert into 테이블이름 values (데이터1, 데이터2, 데이터3, 데이터4, ...)
      02) 출력(검색) 질의문
            : select * from 테이블이름
      03) 수정 질의문
            : update 테이블이름 set 타이틀1 = 값1, 타이틀2 = 값2, ...
      04) 삭제 질의문
            : delete from 테이블이름

  ○ Mobile DataBase
    - SQLite는 1개의 파일로 구성
       * 대용량 DB는 여러개의 파일을 1개로 구성하여 관리함
    - 데이터베이스 활용 순서
       01) 데이터베이스 생성
       02) 테이블 생성
       03) 레코드 추가하기
       04) 데이터 조회하기
    - 데이터베이스를 열거나 삭제할 수 있는 메소드
       01) public abstract SQLiteDatabase openOrCreateDatabase(String name, int mode, SQLiteDatabase.CursorFactory factory)
       02) public abstract boolean deleteDatabase(String name)
    - SQL을 실행할 수 있는 메소드
       01) public void execSQL(String sql) throws SQLException
            * create, insert, delete 등 결과데이터가 없는 SQL문
       02) public Cursor rawQuery(String sql) throws SQLException
            * select와 같이 조회에 따른 결과 데이터가 있는 SQL문
    - 테이블을 만들기 위한 Query
       : CREATE TABLE [IF NOT EXISTS] table_name(col_name column_definition, ...) [table_option] ...
    - 레코드를 추가하기 위한 Query
       : INSERT INTO table_name<column list> VALUES (value, ...)

==================================================================================================*/
package com.eun1310434.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

class DatabaseHelper extends SQLiteOpenHelper {

    public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        Log.d("DatabaseHelper", "DatabaseHelper()");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("DatabaseHelper", "onCreate()");
        //DB 설치 시 호출
        CreateTable(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d("DatabaseHelper", "onUpgrade()");
        //DB를 변경하기 원할 때 호출됨

        if(newVersion == 1){
            //이전 데이터 정보를 최신 DB에 재 전송 후 작업

        }else if(newVersion > 1){
            //이전 데이터 정보를 최신 DB에 재 전송 후 작업
            DeleteTable(db, oldVersion);
        }
        CreateTable(db);
    }

    public void CreateTable(SQLiteDatabase db){
        int Version = 1;
        //테이브 생성
        Log.d("DatabaseHelper", "CreateTable()");
        String query =
                "create table if not exists DBManager"+Version+" ("
                + " _id integer PRIMARY KEY autoincrement, "
                + " name text, "
                + " age integer, "
                + " phone text);";
        db.execSQL(query);
    }

    public void DeleteTable(SQLiteDatabase db, int _oldVersion){
        //테이블 삭제
        //DBManager_1 , DBManager_2 ,DBManager_3
        Log.d("DatabaseHelper", "DeleteTable()");
        String query = "drop table if exists DBManager"+Integer.toString(_oldVersion);
        db.execSQL(query);
    }
}