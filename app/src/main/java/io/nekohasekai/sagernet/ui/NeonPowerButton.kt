package io.nekohasekai.sagernet.ui

import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Color
import android.graphics.Outline
import android.util.AttributeSet
import android.view.View
import android.view.ViewOutlineProvider
import android.view.animation.LinearInterpolator
import android.widget.ImageButton
import androidx.core.content.ContextCompat
import io.nekohasekai.sagernet.R

class NeonPowerButton @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : ImageButton(context, attrs) {

    private var isOn = false
    private var pulseAnimator: ObjectAnimator? = null

    init {
        setBackgroundColor(Color.TRANSPARENT)
        setImageResource(R.drawable.ic_power) // آیکون پاور
        setColorFilter(ContextCompat.getColor(context, R.color.erfing_red))
        setupGlow()
        setupPulse()
        setOnClickListener {
            toggle()
        }
    }

    private fun setupGlow() {
        // Glow دور دکمه
        outlineProvider = object : ViewOutlineProvider() {
            override fun getOutline(view: View, outline: Outline) {
                outline.setOval(0, 0, view.width, view.height)
            }
        }
        clipToOutline = false
        elevation = 20f // شدت نور
        translationZ = 10f
    }

    private fun setupPulse() {
        pulseAnimator = ObjectAnimator.ofFloat(this, "alpha", 1f, 0.5f).apply {
            duration = 800
            repeatMode = ObjectAnimator.REVERSE
            repeatCount = ObjectAnimator.INFINITE
            interpolator = LinearInterpolator()
        }
    }

    fun toggle() {
        isOn = !isOn
        updateUI()
    }

    private fun updateUI() {
        if (isOn) {
            // 🔴 قرمز نئونی + Glow + Pulse
            setColorFilter(ContextCompat.getColor(context, R.color.erfing_red))
            pulseAnimator?.start()
        } else {
            // OFF → خاکستری تیره
            setColorFilter(ContextCompat.getColor(context, R.color.background_dark))
            pulseAnimator?.cancel()
            alpha = 1f
        }
    }

    fun setState(on: Boolean) {
        isOn = on
        updateUI()
    }
}
