package com.example.base;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText edit1, edit2, edit3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edit1 = findViewById(R.id.edit1);
        edit2 = findViewById(R.id.edit2);
        edit3 = findViewById(R.id.edit3);
    }

    // Metodo de Registrar

    public void registrar(View view) {
        AdminBase admin = new AdminBase(this, "administracion",null, 1);
        SQLiteDatabase base = admin.getWritableDatabase();

        String codigo = edit1.getText().toString();
        String descripcion = edit2.getText().toString();
        String precio = edit3.getText().toString();

        if (!codigo.isEmpty() && !descripcion.isEmpty() && !precio.isEmpty()){

            ContentValues registro = new ContentValues();
            registro.put("codigo", codigo);
            registro.put("descripcion", descripcion);
            registro.put("precio", precio);

            base.insert("articulos", null, registro);

            base.close();

            edit1.setText("");
            edit2.setText("");
            edit3.setText("");

            Toast.makeText(this, "Registro guardado con exito", Toast.LENGTH_SHORT).show();

        }else{
            if (edit1.length()==0){
                edit1.setError("Debe llenar este campo");
            }else if(edit2.length()==0){
                edit2.setError("Debe llenar este campo");
            }else if (edit3.length()==0){
                edit3.setError("Debe llenar este campo");
            }
            Toast.makeText(this, "Debe llenar los campos", Toast.LENGTH_SHORT).show();
        }

    }

    // Metodo de Buscar

    public void buscar(View view) {
        AdminBase admin = new AdminBase(this, "administracion", null, 1);
        SQLiteDatabase base = admin.getWritableDatabase();

        String codigo = edit1.getText().toString();

        if (!codigo.isEmpty()){
            Cursor fila =  base.rawQuery
                    ("select descripcion, precio from articulos where codigo = " + codigo, null);

            if(fila.moveToFirst()){
                edit2.setText(fila.getString(0));
                edit3.setText(fila.getString(1));

                base.close();
            }else {
                Toast.makeText(this, "No existe ese articulo", Toast.LENGTH_SHORT).show();
                base.close();
            }
        }else{
            Toast.makeText(this, "El campo codigo no puede quedar vacio", Toast.LENGTH_SHORT).show();
        }

    }

    // Metodo de Modificar

    public void modificar(View view) {
        AdminBase admin = new AdminBase
                (this, "administracion", null,1);
        SQLiteDatabase base = admin.getWritableDatabase();

        String codigo = edit1.getText().toString();
        String descripcion = edit2.getText().toString();
        String precio = edit3.getText().toString();

        if (!codigo.isEmpty() && !descripcion.isEmpty() && !precio.isEmpty()){

        ContentValues registro = new ContentValues();

        registro.put("codigo", codigo);
        registro.put("descripcion", descripcion);
        registro.put("precio", precio);

        int cantidad = base.update
                ("articulos", registro, "codigo=" + codigo, null);

        base.close();

        if (cantidad==1){
            Toast.makeText(this, "Articulado modificado", Toast.LENGTH_SHORT).show();
            edit1.setText("");
            edit2.setText("");
            edit3.setText("");
        }else{
            Toast.makeText(this, "Articulo no existe", Toast.LENGTH_SHORT).show();
        }

        }else {
            Toast.makeText(this, "Debes llenar los campos", Toast.LENGTH_SHORT).show();
        }

    }

   // Metodo de eliminar

    public void eliminar(View view) {
        AdminBase admin = new AdminBase(this, "administracion", null,1);
        SQLiteDatabase base = admin.getWritableDatabase();

        String codigo = edit1.getText().toString();

        if (!codigo.isEmpty()){
            int cantidad = base.delete
                    ("articulos", "codigo=" + codigo, null);

            base.close();

            edit1.setText("");
            edit2.setText("");
            edit3.setText("");

            if (cantidad ==1){
                Toast.makeText(this, "Articulo Eliminado", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, "El articulo no existe", Toast.LENGTH_SHORT).show();
            }

        }else{
            Toast.makeText(this, "Debes introducir el codigo del proudcto", Toast.LENGTH_SHORT).show();
        }

    }
}