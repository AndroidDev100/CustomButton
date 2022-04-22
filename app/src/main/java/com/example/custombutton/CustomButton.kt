package com.example.custombutton

import android.content.Context
import android.content.res.TypedArray
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import androidx.appcompat.widget.AppCompatButton

class CustomButton : AppCompatButton {
    private var defaultStroke: Float = 0.0f
    private var defaultStrokeColor: Int = -1
    private var defaultPadding: Float = 0.0f
    private var defaultElevation: Float = 0.0f
    private var defaultBackgroundColor: Int = -1
    var ctx: Context? = null
    var attributeSet: AttributeSet? = null
    var scale: Float? = null
    var clickListner1: ((View) -> Unit)? = null
    var buttonType: ButtonType? = null

    enum class ButtonType {
        normal, rounded
    }

    constructor(context: Context, attributeSet: AttributeSet?) : super(
        context,
        attributeSet,
        androidx.appcompat.R.style.Base_TextAppearance_AppCompat_Button
    ) {
        this.ctx = context
        this.attributeSet = attributeSet
        init(context, attributeSet, androidx.appcompat.R.style.Base_TextAppearance_AppCompat_Button)
    }

    constructor(context: Context) : super(
        context
    ) {
        this.ctx = context
        init(context, null, androidx.appcompat.R.style.Base_TextAppearance_AppCompat_Button)
    }

    constructor(context: Context, style: Int) : super(
        context,
        null,
        androidx.appcompat.R.style.Base_TextAppearance_AppCompat_Button
    ) {
        this.ctx = context
        init(context, null, androidx.appcompat.R.style.Base_TextAppearance_AppCompat_Button)
    }

    inline fun <reified T : Enum<T>> TypedArray.getEnum(index: Int, default: T) =
        getInt(index, -1).let {
            if (it >= 0) enumValues<T>()[it] else default
        }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val desiredWidth = (100 * scale!! + 0.5f).toInt()
        val desiredHeight = (50 * scale!! + 0.5f).toInt()
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)
        val width: Int
        val height: Int

        //Measure Width
        width = if (widthMode == MeasureSpec.EXACTLY) {
            //Must be this size
            widthSize
        } else if (widthMode == MeasureSpec.AT_MOST) {
            //Can't be bigger than...
            Math.min(desiredWidth, widthSize)
        } else {
            //Be whatever you want
            desiredWidth
        }

        //Measure Height
        height = if (heightMode == MeasureSpec.EXACTLY) {
            //Must be this size
            heightSize
        } else if (heightMode == MeasureSpec.AT_MOST) {
            //Can't be bigger than...
            Math.min(desiredHeight, heightSize)
        } else {
            //Be whatever you want
            desiredHeight
        }

        //MUST CALL THIS
        setMeasuredDimension(width, height)
    }

    fun setClickListner(clickListner: (View) -> (Unit)) {
        clickListner1 = clickListner
        setOnClickListener {
            clickListner1!!.invoke(it)
        }
    }

    var styledAttributes: TypedArray? = null
    private fun init(context: Context, attributeSet: AttributeSet?, style: Int) {
        styledAttributes =
            context.theme.obtainStyledAttributes(attributeSet, R.styleable.CustomButton, 0, 0)
        styledAttributes!!.apply {
            try {
                defaultBackgroundColor = getColor(R.styleable.CustomButton_backgroundColor, 777)
                defaultStrokeColor = getColor(R.styleable.CustomButton_defaultStrokeColor, 777)
                defaultElevation = getDimension(R.styleable.CustomButton_defaultElevation, 1.0f)
                defaultPadding = getDimension(R.styleable.CustomButton_defaultPadding, 1.0f)
                defaultStroke = getDimension(R.styleable.CustomButton_defaultStroke, 1.0f)
                buttonType = styledAttributes!!.getEnum(
                    R.styleable.CustomButton_buttonType,
                    ButtonType.normal
                )
            } finally {
                recycle()
            }
        }
        scale = context.resources.displayMetrics.density
        setTextColor(context.resources.getColor(R.color.white))
        gravity = Gravity.CENTER
        if (defaultElevation != 1.0f) {
            elevation = defaultElevation
        } else {
            elevation = 10 * scale!! + 0.5f
        }
        if (defaultPadding != 1.0f) {
            setPadding(
                defaultPadding.toInt(), defaultPadding.toInt(),
                defaultPadding.toInt(), defaultPadding.toInt()
            )
        } else {
            setPadding(
                (10 * scale!! + 0.5f).toInt(),
                (10 * scale!! + 0.5f).toInt(),
                (10 * scale!! + 0.5f).toInt(),
                (10 * scale!! + 0.5f).toInt()
            )
        }

        if (buttonType == ButtonType.normal) {
            if (defaultBackgroundColor != -1) {
                this.setBackgroundColor(defaultBackgroundColor)
            } else {
                this.setBackgroundColor(context.resources.getColor(R.color.blue))
            }
        } else {
            val shape = GradientDrawable()
            shape.setCornerRadius((10 * scale!! + 0.5f))
            if (defaultStroke != 1.0f) {
                if (defaultStrokeColor != 777) {
                    shape.setStroke(defaultStroke.toInt(), defaultStrokeColor)
                } else {
                    shape.setStroke(
                        defaultStroke.toInt(),
                        context.resources.getColor(R.color.black)
                    )
                }
            } else {
                if (defaultStrokeColor != 777) {
                    shape.setStroke((3 * scale!! + 0.5f).toInt(), defaultStrokeColor)
                } else {
                    shape.setStroke(
                        (3 * scale!! + 0.5f).toInt(),
                        context.resources.getColor(R.color.black)
                    )
                }
            }
            if (defaultBackgroundColor != 777) {
                shape.setColor(defaultBackgroundColor)
            } else {
                shape.setColor(context.resources.getColor(R.color.blue))
            }
            this.background = shape
        }
    }
}