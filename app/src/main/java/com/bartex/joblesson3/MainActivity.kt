package com.bartex.joblesson3

import android.content.Context
import android.graphics.*
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import android.widget.ImageButton
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider

class MainActivity : AppCompatActivity(){

    private lateinit var imageButton : ImageButton
    private var isClick = false
    private val viewModel by lazy{
        ViewModelProvider(this).get(MainViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isClick = viewModel.getIsClick()
        if(!isClick){
            setContentView(R.layout.activity_main)
            imageButton = findViewById(R.id.ib)

            imageButton.setOnClickListener {
                isClick = true
                viewModel.saveIsClick(isClick)
                recreate()
            }
        }else{
            setContentView(DrawView(this))
        }
    }

    inner class DrawView(context: Context): View(context) {
//        private var background: Bitmap = BitmapFactory.decodeResource(resources, R.drawable.map4)
        private var car  =  BitmapFactory.decodeResource(resources, R.drawable.car1)
//        private val destination: Rect
//        private val source: Rect
        private val path : Path = Path()
        private var paint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
        private  var pathMeasure: PathMeasure
        private var pathLength = 0f
        private var stepNumber = 0
        private var maxAnimationStep = 400
        private var segmentLen = 0f
        private var matrixCar  = Matrix()
        private var paintText  =  Paint(Paint.ANTI_ALIAS_FLAG)
        var pos: FloatArray = FloatArray(2)
        var tan: FloatArray= FloatArray(2)

        init {
            val display =(context.getSystemService(Context.WINDOW_SERVICE) as WindowManager).defaultDisplay
            with(paint){
                style = Paint.Style.STROKE
                strokeWidth = 20f
                color = Color.argb(80, 0, 0, 255)
            }
            with(path){
                moveTo(55f, 33f)
                lineTo(437f, 248f)
                lineTo(222f, 828f)
                lineTo(386f, 912f)
                lineTo(149f, 1525f)
                lineTo(display.width.toFloat(), display.height.toFloat())
            }
            paintText.textSize = 50F;

            pathMeasure = PathMeasure(path, false)
            pathLength = pathMeasure.length
            segmentLen = pathLength/maxAnimationStep
        }

        override fun onDraw(canvas: Canvas?) {
            super.onDraw(canvas)
            if (stepNumber < maxAnimationStep-0.5*car.width/segmentLen){
                pathMeasure.getMatrix(
                        segmentLen * stepNumber, matrixCar,
                        PathMeasure.POSITION_MATRIX_FLAG
                                + PathMeasure.TANGENT_MATRIX_FLAG
                )
                matrixCar.preTranslate(
                        (-car.width / 2).toFloat(),
                        (-car.height / 2).toFloat()
                )
                pathMeasure.getPosTan(segmentLen * stepNumber, pos, tan)
                canvas?.drawText(String.format(getString(R.string.display),
                         display.width, display.height ), 100f, 100f, paintText)
                canvas?.drawText(String.format(getString(R.string.coord),
                         pos[0], pos[1] ),100f, 150f, paintText)

                canvas?.drawBitmap(car, matrixCar, null)
                stepNumber++
                invalidate()
            }else {
                stepNumber =0
                viewModel.saveIsClick(false)
                recreate()
            }
        }
    }
}