package cn.daqinjia.android.scaffold.ext

import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.*
import android.view.View


/**
 * # TextSpan
 *
 * 自定义文字样式
 *
 * Created on 2019/12/19
 * @author Vove
 */
class TextSpan(
        val text: String, selectionText: String = text,
        color: Int? = null, fontSize: Int? = null,
        underLine: Boolean = false,
        /**
         * @[android.graphics.Typeface]
         */
        typeface: Int? = null,
        onClick: ((String) -> Unit)? = null
) {
    var spanStr = SpannableStringBuilder(text)
    val start = text.indexOf(selectionText)
    val end = start + selectionText.length

    init {

        onClick?.also {
            val cs = object : ClickableSpan() {
                override fun onClick(widget: View) {
                    it.invoke(text)
                }
            }
            spanStr.setSpan(cs, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        }


        fontSize?.also {
            val sizeSpan = AbsoluteSizeSpan(it, true)
            spanStr.setSpan(sizeSpan, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        }

        color?.also {
            val cs = ForegroundColorSpan(it)
            spanStr.setSpan(cs, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        }
        if (underLine) {
            spanStr.setSpan(UnderlineSpan(), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        }

        typeface?.also {
            spanStr.setSpan(StyleSpan(it), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        }
    }

    fun build(): SpannableStringBuilder {
        return spanStr
    }


}

/**
 *
 * @receiver String
 * @param selectedString String
 * @param fontSize Int dip
 * @param color Int? @ColorInt
 * @param underLine Boolean
 * @param typeface Int?
 * @return SpannableStringBuilder
 */
fun String.span(selectedString: String = this, fontSize: Int, color: Int? = null,
                underLine: Boolean = false, typeface: Int? = null): SpannableStringBuilder {
    return TextSpan(this, selectedString, color = color, fontSize = fontSize,
        underLine = underLine, typeface = typeface).build()
}

fun String.spanColor(colorString: String): SpannableStringBuilder {
    return spanColor(colorString.asColor)
}

fun String.spanColor(color: Int): SpannableStringBuilder {
    return TextSpan(this, color = color).build()
}