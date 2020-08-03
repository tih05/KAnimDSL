package com.tikhomirov.kanimdsl.defaultAnimations

import android.animation.Animator
import android.animation.ObjectAnimator
import android.view.View
import com.tikhomirov.annotations.KAnimation
import com.tikhomirov.kanimdsl.Animation
import com.tikhomirov.kanimdsl.AnimationBuilder

@KAnimation("alpha")
class AlphaAnimation: Animation<Float>() {
    override fun createAnimation(): Animator {
        return ObjectAnimator.ofFloat(target, View.ALPHA,  *this@AlphaAnimation.values.toFloatArray())
            .apply {
                duration = this@AlphaAnimation.duration
                startDelay = this@AlphaAnimation.startDelay

            }
    }
}
