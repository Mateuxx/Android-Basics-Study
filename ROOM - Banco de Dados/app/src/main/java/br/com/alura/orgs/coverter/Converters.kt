package br.com.alura.orgs.coverter

import androidx.room.TypeConverter
import java.math.BigDecimal

class Converters {

    /*
    Converter double vindo do ROOM BigDecimal para usar no app
     */
    @TypeConverter
    fun doubleToBigDecimal (valor: Double?) : BigDecimal {
        return valor?.let { BigDecimal(valor.toString()) } ?: BigDecimal.ZERO
    }

    /*
    Conveter do Banco dos dados do App para o ROOM, la no SQLITE eles podem receber NULL nas QUERRYS
     */
    @TypeConverter
    fun bigDecimalToDouble (valor: BigDecimal?) : Double? {
        return valor?.let { valor.toDouble() }
    }
}