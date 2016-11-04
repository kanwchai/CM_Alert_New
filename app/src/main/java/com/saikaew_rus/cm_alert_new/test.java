//import android.content.DialogInterface;
//import android.content.Intent;
//import android.support.v7.app.AlertDialog;
//import android.support.v7.app.AppCompatActivity;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Button;
//import android.widget.Toast;
//
//public class MainActivity extends AppCompatActivity {
//
//    private Button mButtonDialog;
//    private static final String[] Choice =
//            {"Travel", "Maintenance", "History", "Delete"};//ใส่ตัวเลือก
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//
//        mButtonDialog = (Button) findViewById(R.id.button_open_dialog);
//        mButtonDialog.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                AlertDialog.Builder builder =
//                        new AlertDialog.Builder(MainActivity.this);
//                builder.setTitle("Car register");
//                builder.setItems(Choice, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        String selected = Choice[which];
//                        Toast.makeText(getApplicationContext(), selected, Toast.LENGTH_SHORT).show();
//
//                        if (which == 0) {
//                            Intent intent = new Intent(MainActivity.this, Travel.class);
//                            startActivity(intent);
//                        } else if (which == 1) {
//                            Intent intent = new Intent(MainActivity.this, Manternance.class);
//                            startActivity(intent);
//                        } else if (which == 2) {
//                            Intent intent = new Intent(MainActivity.this, History.class);
//                            startActivity(intent);
//
//                        } else if (which == 3) {
//                            Intent intent = new Intent(MainActivity.this, Delete.class);
//                            startActivity(intent);
//                        }
//                    }
//                });//คลิกเพื่อเปลี่ยนหน้า
//                // สุดท้ายอย่าลืม show() ด้วย
//                builder.show();
//            }
//        });
//
//
//    }
//
//
//}