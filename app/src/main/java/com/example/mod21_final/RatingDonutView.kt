package com.example.mod21_final

import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.View

class RatingDonutView @JvmOverloads
    constructor(context: Context, attributeSet: AttributeSet? = null)
    : View(context, attributeSet){


    private val oval = RectF()          //Овал для рисования сегментов прогресс бара

    private var radius: Float = 0f      //Координаты центра View, а также Radius
    private var centerX: Float = 0f
    private var centerY: Float = 0f

    private var stroke = 10f            //Толщина линии прогресса
    private var progress = 50           //Значение прогресса от 0 - 100
    private var scaleSize = 60f         //Значения размера текста внутри кольца

    private lateinit var strokePaint: Paint     //Краски для наших фигур
    private lateinit var digitPaint: Paint
    private lateinit var circlePaint: Paint

    private var animateFlag :Boolean = false
    private var animateDuration:Long  = 2000L

    private fun getPaintColor(progress: Int): Int =
        when(progress) {
            in 0 .. 25 -> Color.parseColor("#e84258")
            in 26 .. 50 -> Color.parseColor("#fd8060")
            in 51 .. 75 -> Color.parseColor("#fee191")
            else -> Color.parseColor("#b0d8a4")
    }

    private fun initPaint() {
        strokePaint = Paint().apply {       //Краска для колец
            style = Paint.Style.STROKE
            strokeWidth = stroke            //Сюда кладем значение из поля класса, потому как у нас краски будут видоизменяться
            color = getPaintColor(progress) //Цвет мы тоже будем получать в специальном методе, потому что в зависимости от рейтинга мы будем менять цвет нашего кольца
            isAntiAlias = true
        }
        digitPaint = Paint().apply {        //Краска для цифр
            style = Paint.Style.FILL_AND_STROKE
            strokeWidth = 2f
            setShadowLayer(5f, 0f, 0f, Color.DKGRAY)
            textSize = scaleSize
            typeface = Typeface.SANS_SERIF
            color = getPaintColor(progress)
            isAntiAlias = true
        }
        circlePaint = Paint().apply {        //Краска для заднего фона
            style = Paint.Style.FILL
            color = Color.DKGRAY
        }
    }

    private fun chooseDimension(mode: Int, size: Int) =
        when (mode) {
            MeasureSpec.AT_MOST, MeasureSpec.EXACTLY -> size
            else -> 300
        }

    // Function to animate the progress
    //  Благодарность умному ChatGPT
    private fun animateProgress(targetProgress: Int, duration: Long) {
        val animator = ValueAnimator.ofInt( 0 , targetProgress) //.ofFloat(progress.toFloat() , targetProgress)
        animator.duration = duration
        animator.addUpdateListener { animation ->
            progress = animation.animatedValue as Int
            // Invalidate or redraw your view here to update the animation
            invalidate() // This should be called within a custom View class
        }
        animator.start()
    }


    private fun convertProgressToDegrees(progress: Int): Float = progress * 3.6f

    private fun drawRating(canvas: Canvas) {
        val scale = radius * 0.8f                       // размер нашего кольца
        canvas.save()                                   // Сохраняем канвас
        canvas.translate(centerX, centerY)              // Перемещаем нулевые координаты канваса в центр

        oval.set(0f - scale, 0f - scale, scale , scale)     //Устанавливаем размеры под наш овал
        canvas.drawCircle(0f, 0f, radius, circlePaint)          //Рисуем задний фон
        canvas.drawArc(
            oval,
            -90f,
            convertProgressToDegrees(progress),
            false,
            strokePaint)  //Рисуем "арки", из них и будет состоять наше кольцо + у нас тут специальный метод

        canvas.restore()                                                //Восстанавливаем канвас
    }

    @SuppressLint("DefaultLocale")
    private fun drawText(canvas: Canvas) {
        val message = String.format("%.1f", progress / 10f)              //  дробное -> с одной цифрой после точки
        //Получаем ширину и высоту текста, чтобы компенсировать их при отрисовке, чтобы текст был
        //точно в центре
        val widths = FloatArray(message.length)
        digitPaint.getTextWidths(message, widths)
        var advance = 0f
        for (width in widths) advance += width

        canvas.drawText(message,                        //Рисуем наш текст
            centerX - advance / 2,
            centerY  + advance / 4,
            digitPaint)
    }

  // ****************************************************************************************************

    init {
        val a = context.theme.obtainStyledAttributes(                                   // Получаем атрибуты из  Context
                attributeSet, R.styleable.RatingDonutView, 0, 0)
        try {
            stroke = a.getFloat(
                R.styleable.RatingDonutView_stroke, stroke)
            progress = a.getInt(R.styleable.RatingDonutView_progress, progress)
        } finally {
            a.recycle()                                                                  // free TypedArray resources
        }

        initPaint()                             //Инициализируем первоначальные краски
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        radius = if (width > height) {
            height.div(2f)
        } else {
            width.div(2f)
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)

        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)

        val chosenWidth = chooseDimension(widthMode, widthSize)
        val chosenHeight = chooseDimension(heightMode, heightSize)

        val minSide = Math.min(chosenWidth, chosenHeight)
        centerX = minSide.div(2f)
        centerY = minSide.div(2f)

        setMeasuredDimension(minSide, minSide)
    }

    override fun onDraw(canvas: Canvas) {
        drawRating(canvas)                                  //Рисуем кольцо и задний фон
        if (!animateFlag) {
            animateFlag = true
            animateProgress( progress , animateDuration)
        }
        drawText(canvas)                                    //Рисуем цифры
    }


    fun setProgress(pr: Int) {
        progress = pr                                        //Кладем новое значение в наше поле класса
        initPaint()                                          //Создаем краски с новыми цветами
        invalidate()                                         //вызываем перерисовку View
       //animateProgress( progress , animateDuration)
    }

}