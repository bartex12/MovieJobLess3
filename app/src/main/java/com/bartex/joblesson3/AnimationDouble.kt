package com.bartex.joblesson3

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager

class AnimationDouble (context: Context): View(context){
    companion object{
        const val TAG = "33333"
    }
    private val matrixCar = Matrix()
    private var car: Bitmap =  BitmapFactory.decodeResource(resources, R.drawable.car1)
    private var background: Bitmap = BitmapFactory.decodeResource(resources, R.drawable.map5gray)
    private  var source: Rect
    private  var destination: Rect
    private val maxAnimationStep = 400 //animation step
    private var stepNumber = 0
    private var paint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val path: Path = Path() //curve
    private var pathMeasure: PathMeasure //path Measure
    private var segmentLen = 0f  //segment length

    init {
        val display =(context.getSystemService(Context.WINDOW_SERVICE) as WindowManager).defaultDisplay
        destination = Rect(0, 0, display.width, display.height)
        source = Rect(0, 0, background.width, background.height)//source растянем до destination

        with(paint){
            style = Paint.Style.STROKE
            strokeWidth = 5f
            color = Color.rgb(255, 0, 0)
        }
        with(path){
            moveTo(55f, 33f)
            lineTo(437f, 248f)
            lineTo(222f, 828f)
            lineTo(386f, 912f)
            lineTo(149f, 1525f)
            lineTo(display.width.toFloat(), display.height.toFloat())
        }
        pathMeasure = PathMeasure(path, false)
        segmentLen = pathMeasure.length / maxAnimationStep
    }

    override fun onDraw(canvas: Canvas) {
        canvas.drawBitmap(background, source, destination, null) //фон
        canvas.drawPath(path, paint) //путь

        if (stepNumber <= maxAnimationStep - 0.5 * car.width/segmentLen) {
            pathMeasure.getMatrix(
                segmentLen * stepNumber, matrixCar,
                PathMeasure.POSITION_MATRIX_FLAG + PathMeasure.TANGENT_MATRIX_FLAG
            )
            matrixCar.preTranslate(
                (-car.width/2).toFloat(),
                (-car.height/2).toFloat()
            )
            canvas.drawBitmap(car, matrixCar, null)
            stepNumber++
            invalidate()
        } else {
            stepNumber = 0
            matrixCar.reset() //если закоментировать, машина будет оставаться внизу
            canvas.drawBitmap(car, matrixCar, null)
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN) {
            invalidate()
            return true
        }
        return false
    }
}