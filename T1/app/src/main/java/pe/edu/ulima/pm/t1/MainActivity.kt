package pe.edu.ulima.pm.t1

import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import pe.edu.ulima.pm.t1.views.Carta
import pe.edu.ulima.pm.t1.views.CartaVolteada
import pe.edu.ulima.pm.t1.views.Mazo

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        //PARA VOLTEAR LA PANTALLA , ASI SE VEN MEJOR LAS CARTAS
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE);
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        InicioJuego()
    }
    private fun InicioJuego() {
        cartasusadas = cartas
        repartirCartas()
        ponercartaenmesa()
    }
    var cartas = arrayOf(
        "11","12","13","14","21","22","23","24",
        "31","32","33","34","41","42","43","44",
        "51","52","53","54","61","62","63","64",
        "71","72","73","74","81","82","83","84",
        "91","92","93","94","101","102","103","104",
        "J1","J2","J3","J4","Q1","Q2","Q3","Q4",
        "K1","K2","K3","K4"
    )
    var cartasusadas = cartas
    var jugador1 = ArrayList<String>()
    var jugador2 = ArrayList<String>()
    var jugador3 = ArrayList<String>()
    var cartamesa : String ?= null
    var turno = 0
    var jugarJ = 0
    var jugarK = 0


    private fun ponercartaenmesa() {

        var baraja = CartaVolteada(this, 0)
        var Relative = findViewById<RelativeLayout>(R.id.RelativeBaraja)
        val params = RelativeLayout.LayoutParams(
            RelativeLayout.LayoutParams.WRAP_CONTENT,
            RelativeLayout.LayoutParams.WRAP_CONTENT
        )
        params.setMargins(500, 0,0, 0)
        baraja.setLayoutParams(params)
        Relative.addView(baraja)

        var estado = true
        while(estado){
            var random=(0..51).random()
            if(cartasusadas[random]!="0"){
                cartamesa = cartasusadas[random]
                var mazo = Mazo(this, cartasusadas[random])
                val params2 = RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT
                )
                params2.setMargins(0, 0,500, 0)
                mazo.setLayoutParams(params2)
                Relative.addView(mazo)
                cartasusadas[random]= "0"
                estado = false
            }
        }


    }

    private fun repartirCartas() {
//8 cartas a repartir
        for (i in 0..7){
            var estado=true
            while(estado){
                //son 52 cartas en el arrayOf
                var random=(0..51).random()
                //PARA ASIGNAR CARTAS A JUGADOR 1
                if(cartasusadas[random]!="0"){
                    jugador1.add(cartasusadas[random])
                    cartasusadas[random]= "0"
                    estado=false
                }
            }
            while(estado!=true){
                var random=(0..51).random()
                //PARA ASIGNAR CARTAS A JUGADOR 2
                if(cartasusadas[random]!="0"){
                    jugador2.add(cartasusadas[random])
                    cartasusadas[random]= "0"
                    estado=true
                }
            }
            while(estado){
                var random=(0..51).random()
                //PARA ASIGNAR CARTAS A JUGADOR 3
                if(cartasusadas[random]!="0"){
                    jugador3.add(cartasusadas[random])
                    cartasusadas[random]= "0"
                    estado=false
                }
            }
        }
        mostrarCartas(jugador1)
    }
    //CAMBIAR DE TURNO , ESTO SE EJECUTA AL INICIAR EL TURNO DEL NUEVO JUGADOR
    private fun mostrarCartas(lista: ArrayList<String>) {
        turno = 0
        jugarJ = 0
        jugarK = 0

        var AreaBotonesJugador = findViewById<LinearLayout>(R.id.AreaBotonesJugador)
        var botonrobar = findViewById<Button>(R.id.botonjugar)
        botonrobar.setText("Roba 1")
        //remover todas las views para poner el del jugador correspondiente
        AreaBotonesJugador.removeAllViews()
        for (i in lista){
            var carta = Carta(this, i)
            //SOLO SI LE DOY CLICK SE ACTIVA
            carta.setOnClickListener{
                var rpta = jugar(i)
                if(rpta == true){
                    //PRIMERO LA BORRO DEL MAZO Y LA SACO DE LA LISTA PARA QUE NO SE VUELVA A MOSTRAR
                    AreaBotonesJugador.removeView(carta)
                    lista.remove(i)
                    //Y PONGO EL BOTON A TERMINAR PORQUE YA JUGUE UNA CARTA
                    botonrobar.setText("Terminar")
                    cambiarboton(lista)
                }
            }
            //aca muestro las cartas de todos los jugadores (del q esta en turno pero)
            AreaBotonesJugador.addView(carta)
        }

        botonrobar.setOnClickListener{

            var estado=true
            var cartaadd :String ?=null
            while(estado){
                var random=(0..51).random()
                if(cartasusadas[random]!="0"){
                    lista.add(cartasusadas[random])
                    cartaadd = cartasusadas[random]
                    cartasusadas[random]= "0"
                    estado=false
                }
            }
            var carta = Carta(this, cartaadd!!)
            carta.setOnClickListener{
                var rpta = jugar(cartaadd!!)
                if(rpta == true){
                    AreaBotonesJugador.removeView(carta)
                    lista.remove(cartaadd!!)
                    botonrobar.setText("Terminar")
                    cambiarboton(lista)
                }
            }
            AreaBotonesJugador.addView(carta)
            botonrobar.setText("Terminar")
            cambiarboton(lista)
        }
    }

    private fun cambiarboton(lista: ArrayList<String>) {
        var botonterminar = findViewById<Button>(R.id.botonjugar)
        botonterminar.setOnClickListener{
            pasarJugador(lista)
        }
    }

    private fun pasarJugador(lista:ArrayList<String> ) {
        var JugadorActual = findViewById<TextView>(R.id.JugadorActual)
        var listaactual: ArrayList<String>? = null
        if (jugador1 == lista) {
            listaactual = jugador2
            JugadorActual.setText("Tus cartas Jugador 2")
        } else if (jugador2 == lista) {
            listaactual = jugador3
            JugadorActual.setText("Tus cartas Jugador 3")
        } else {
            listaactual = jugador1
            JugadorActual.setText("Tus cartas Jugador 1")
        }

        if(jugarJ!=0){
            jugarJ--
            pasarJugador(listaactual)
        }
        else if (jugarK != 0) {
            while (jugarK!=0){
                for (i in 0..2) {
                    //MIENTRAS ESTADO SEA TRUE, NO DEBO SALIR HASTA QUE SE LE ASIGNE UNA CARTA AL JUGADOR
                        //BUSCANDO CARTAS QUE NO HAYAN SIDO USADAS.
                    var estado = true
                    while (estado) {
                        var random = (0..51).random()
                        if (cartasusadas[random] != "0") {
                            listaactual.add(cartasusadas[random])
                            cartasusadas[random] = "0"
                            estado = false
                        }
                    }
                }
                jugarK--
            }

        }
        mostrarCartas(listaactual)
    }
    //BOOLEAN PARA VERIFICAR SI LA CARTA SE PUEDE JUGAR
    private fun jugar(i: String): Boolean {
        var retorno = false
        //TURNO = 1 CUANDO YA JUGUE A TIRAR UNA CARTA
        //CUANDO YA JUGUE UNA VEZ
        //VERIFICA QUE SEA MISMO NUMERO
        if(turno==1){
            if(cartamesa!!.dropLast(1) == i.dropLast(1)){
                retorno = true
                cartamesa = i
                jugarCarta()
                turno = 1
            }
        }else{
            //VERIFICA QUE SEA EL MISMO SIMBBOLO O EL MISMO NUMERO
                //LENGTH - 1 PARA VER EL ULTIMO DIGITO QUE ES EL SIMBOLO
            if(cartamesa!![cartamesa!!.length-1] == i[i.length-1]  || cartamesa!!.dropLast(1) == i.dropLast(1)){
                retorno = true
                cartamesa = i
                jugarCarta()
                turno = 1
            }
        }
        if(cartamesa!!.dropLast(1) == "K" ){
            //GUARDO EN VARIABLE PARA QUE EL SIGUIENTE JUGADOR AGARRE 3
            jugarK++
        }
        if(cartamesa!!.dropLast(1) == "J" ){
            //GUARDO PARA QUE EL SIGUIENTE JUGADOR YA NO JUEGE
            jugarJ++
        }

        return retorno
    }

    private fun jugarCarta() {
        //Aca se apilan las cartas
        var Relative = findViewById<RelativeLayout>(R.id.RelativeBaraja)
        var mazo = Mazo(this, cartamesa!!)
        //RL.LP es para agregar atributos, en vez de hacerlo x codigo en xml
        val params2 = RelativeLayout.LayoutParams(
            RelativeLayout.LayoutParams.WRAP_CONTENT,
            RelativeLayout.LayoutParams.WRAP_CONTENT
        )
        params2.setMargins(0, 0,500, 0)
        mazo.setLayoutParams(params2)
        Relative.addView(mazo)
    }
}