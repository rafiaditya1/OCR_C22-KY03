package com.bangkit.ocr_c22_ky03.module.customView

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.AttributeSet
import android.util.Patterns
import android.view.View
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import com.bangkit.ocr_c22_ky03.R

class EmailEditText : AppCompatEditText {
    private lateinit var backgroundEt: Drawable
    private var txtColor: Int = 0

    constructor(context: Context) : super(context) {
        init()
    }
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    private fun init() {
        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                // Do nothing.
            }
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (!isValidEmail(s)) {
                    error = resources.getString(R.string.valid_email)
                }
            }
            override fun afterTextChanged(s: Editable) {
            }
        })
        backgroundEt = ContextCompat.getDrawable(context, R.drawable.bg_edit_text) as Drawable
        txtColor = ContextCompat.getColor(context, android.R.color.background_light)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        textSize = 12f
        textAlignment = View.TEXT_ALIGNMENT_VIEW_START
    }

    private fun isValidEmail(s: CharSequence): Boolean {
        return !TextUtils.isEmpty(s) && Patterns.EMAIL_ADDRESS.matcher(s).matches()
    }
}