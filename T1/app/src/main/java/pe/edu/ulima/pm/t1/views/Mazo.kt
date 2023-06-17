package pe.edu.ulima.pm.t1.views

import android.content.Context
import android.graphics.*
import android.view.View
import pe.edu.ulima.pm.t1.R

class Mazo: View {
    var width : Int? = null
    var height : Int? = null
    var paint : Paint? = null
    var numero: String? = null

    constructor(context: Context, num : String) : super(context) {
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
        drawCarta(canvas)
    }

    private fun drawCarta(canvas: Canvas?) {
        var foto: Bitmap?=null
        when (numero!![numero!!.length-1].toString()) {
            "1" -> foto = BitmapFactory.decodeResource(resources, R.drawable.diamante)
            "2" -> foto = BitmapFactory.decodeResource(resources, R.drawable.trebol)
            "3" -> foto = BitmapFactory.decodeResource(resources, R.drawable.espada)
            "4" -> foto = BitmapFactory.decodeResource(resources, R.drawable.corazon)
        }
        canvas?.drawBitmap(foto!!, null, Rect(width!!/4,+height!!/5, width!!*3/4, height!!/2),paint)
        var text = Paint(Paint.ANTI_ALIAS_FLAG)
        text.color = Color.BLACK
        text.textSize = 100f
        canvas!!.drawText(numero!!.dropLast(1),width!!/15f,height!!/6f,text)
    }

    private fun drawFondo(canvas: Canvas?) {
        var color= Color.argb(255,255,255,255)
        canvas?.drawColor(color)
        paint!!.color = Color.BLACK
        paint!!.style = Paint.Style.STROKE
        paint!!.setStrokeWidth(width!!/15f)
        canvas!!.drawRect(Rect(0, height!!, width!!, 0),paint!!)
    }
}