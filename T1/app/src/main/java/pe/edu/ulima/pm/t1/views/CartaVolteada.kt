package pe.edu.ulima.pm.t1.views

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import pe.edu.ulima.pm.t1.R

class CartaVolteada: View {
    var width : Int? = null
    var height : Int? = null
    var paint : Paint? = null
    var numero : Int? = null
    constructor(context: Context, attrs: AttributeSet):super(context,attrs){
        numero= 0
        paint=Paint()
    }
    //crearlo en el xml
    constructor(context : Context, num : Int) : super(context) {
        numero = num
        paint = Paint()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        width = View.MeasureSpec.getSize(widthMeasureSpec)
        height = View.MeasureSpec.getSize(heightMeasureSpec)
        setMeasuredDimension((width!!/7.5).toInt(), (height!! /1.5).toInt())
    }
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        drawFondo(canvas)
    }

    private fun drawFondo(canvas: Canvas?) {
        var carta = BitmapFactory.decodeResource(resources, R.drawable.fondo)
        canvas?.drawBitmap(carta!!,null, Rect(0,0,width!!,2*height!!/3),paint!!)
        var text = Paint(Paint.ANTI_ALIAS_FLAG)
        text.color = Color.BLACK
        text.textSize = 100f
        if(numero!=0){
            canvas!!.drawText(numero!!.toString(), (1.5*width!!/4f).toFloat(), (1.5*height!!/4f).toFloat(),text)

        }

    }


}