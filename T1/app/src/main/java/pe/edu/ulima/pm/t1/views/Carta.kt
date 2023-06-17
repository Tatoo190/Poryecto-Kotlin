package pe.edu.ulima.pm.t1.views

import android.content.Context
import android.graphics.*
import android.view.View
import pe.edu.ulima.pm.t1.R

class Carta: View {
    var size : Int? = null
    var paint : Paint? = null
    var numero : String ?= null

    constructor(context : Context, num : String) : super(context) {
        numero = num
        paint = Paint()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val width = View.MeasureSpec.getSize(widthMeasureSpec)
        val height = View.MeasureSpec.getSize(heightMeasureSpec)
        size = Math.min(width, height)
        setMeasuredDimension(size!!, (size!!*1.5f).toInt())
    }
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        drawFondo(canvas)
        drawBorde(canvas)
        drawCarta(canvas)
    }

    fun drawCarta(canvas: Canvas?) {
        var foto: Bitmap?=null
    //when para hacer un switch
        when (numero!![numero!!.length-1].toString()) {
            //BTMAP para pegar imagenes
            "1" -> foto = BitmapFactory.decodeResource(resources, R.drawable.diamante)
            "2" -> foto = BitmapFactory.decodeResource(resources, R.drawable.trebol)
            "3" -> foto = BitmapFactory.decodeResource(resources, R.drawable.espada)
            "4" -> foto = BitmapFactory.decodeResource(resources, R.drawable.corazon)
        }
        //tamaños
        canvas?.drawBitmap(foto!!, null, Rect(width/4,+height/5, width*3/4, height*3/5),paint)
 //PARA EL TEXTO --> en vez de paint , la variable text para el color de carta size etc
        var text = Paint(Paint.ANTI_ALIAS_FLAG)
        text.color = Color.BLACK
        text.textSize = size!!/4f
        //DRAWtEXT PARA EL TEXTO
        //dropLast : (1) para que bote el ultimo numero que representa el tipo de carta

        canvas!!.drawText(numero!!.dropLast(1),size!!/15f,size!!/4f,text)
    }

    fun drawFondo(canvas: Canvas?) {
        var color= Color.argb(255,255,255,255)
        canvas?.drawColor(color)

    }

    fun drawBorde(canvas: Canvas?) {
        paint!!.color = Color.BLACK
        paint!!.style = Paint.Style.STROKE
        paint!!.setStrokeWidth(size!!/15f)
        canvas!!.drawRect(Rect(0, size!!, size!!, 0),paint!!)
    }

}