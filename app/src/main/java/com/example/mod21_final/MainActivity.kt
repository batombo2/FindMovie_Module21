package com.example.mod21_final

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.os.Bundle
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)


        val topAppBar : MaterialToolbar = findViewById<MaterialToolbar>(R.id.topAppBar)

        topAppBar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.tool_item_settings -> {
                    Toast.makeText(this, "Настройки", Toast.LENGTH_SHORT).show()
                    true
                }
                else -> false
            }
        }

        val bottom_navigation : BottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)

        bottom_navigation.setOnNavigationItemSelectedListener {

            when (it.itemId) {
                R.id.menu_item_favorite -> {
                    Toast.makeText(this, "Избранное", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.menu_item_later -> {
                    Toast.makeText(this, "Посмотреть похже", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.menu_item_selections -> {
                    Toast.makeText(this, "Подборки", Toast.LENGTH_SHORT).show()
                    true
                }
                else -> false
            }
        }

        // ************************************************************
        val image2 = findViewById<ImageView>(R.id.image_poster2)
        val scaleAnimator = AnimationUtils.loadAnimation( this , R.anim.hyperspace_jump)
        scaleAnimator.duration = 500

        image2.setOnClickListener(){
            image2.startAnimation(scaleAnimator)
        }

        // *************************************************************
        val image3 = findViewById<ImageView>(R.id.image_poster3)
        val objectAnimator = ObjectAnimator.ofFloat( image3 ,"rotation" , 0f , 360f)
        objectAnimator.duration = 1000
        image3.setOnClickListener(){
            objectAnimator.start()
        }

        // ************************************************************
        val image4 = findViewById<ImageView>(R.id.image_poster4)
        val valueAnimator : ValueAnimator = ValueAnimator.ofFloat( 1f , 0f)
        valueAnimator.duration = 1000
        //valueAnimator.startDelay = 1000
        valueAnimator.addUpdateListener { updatedAnimation ->
            image4.alpha = updatedAnimation.animatedValue as Float
            if (image4.alpha == 0f) {
                image4.alpha = 1f
            }
        }
        image4.setOnClickListener(){
            valueAnimator.start()
        }

        // *************************************************************

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}