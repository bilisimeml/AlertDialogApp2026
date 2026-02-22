package com.example.alertdialogapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.alertdialogapp.databinding.ActivityMainBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    fun dialogAc(view : View){
        // Uygulamanın modern görğnmesi için MaterialAlertDialogBuilder kullanıyoruz
        // Context Seçimi: AlertDialog oluştururken applicationContext yerine
        // mutlaka Activity'nin this referansını kullanmalısın; aksi takdirde uygulama hata verir.
        val builder = MaterialAlertDialogBuilder(this@MainActivity)
        builder.setTitle("Kaydet ve Git")
        builder.setMessage("Kaydetmek ve Gitmek istediğinize emin misiniz?")
        // olumlu buton
        builder.setPositiveButton("Evet") { dialog, which ->
            val mesaj = binding.etMesaj.text.toString()
            // edittext boş bırakılmadıysa bir sonraki aktiviteye geç
            if (mesaj.isNotEmpty()) {
                val intent = Intent(this@MainActivity, ResultActivity::class.java)
                intent.putExtra("mesaj", mesaj)
                startActivity(intent)
            }else{
                // edittext boş ise uyarı ver
                Toast.makeText(
                    this@MainActivity,
                    "Mesaj boş bırakılamaz",
                    Toast.LENGTH_SHORT).show()
                return@setPositiveButton
            }
        }
        // kullanıcı diyaloğun dışına tıkladığında diyalog kapanmaz
        builder.setCancelable(false)
        // olumsuz buton
        builder.setNegativeButton("Hayır") { dialog, which ->
            dialog.dismiss() // diyalog penceresini kapat
        }
        // diyalog penceresinin ikonunu ayarla
        builder.setIcon(android.R.drawable.btn_star_big_on)
        // diyalog penceresini göster
        val alertDialog = builder.create()
        alertDialog.show()

    }
}