package com.tikhomirov.kotanimdsl.DefaultAnimations

import android.animation.Animator
import android.animation.ObjectAnimator
import android.view.View
import com.tikhomirov.kotanimdsl.Animation
import com.tikhomirov.kotanimdsl.AnimationBuilder

class AlphaAnimation: Animation<Float>() {
    override fun createAnimation(): Animator {
        return ObjectAnimator.ofFloat(target, View.ALPHA,  *this@AlphaAnimation.values.toFloatArray())
            .apply {
                duration = this@AlphaAnimation.duration
                startDelay = this@AlphaAnimation.startDelay

            }
    }
}

fun AnimationBuilder.alpha(lambda: AlphaAnimation.()->Unit) = addAnimation(AlphaAnimation(), lambda)

fun View.animateAlpha(lambda: AlphaAnimation.()->Unit)  = AlphaAnimation().launchOnView(this, lambda)

fun View.alpha(lambda: AlphaAnimation.()->Unit): Animator  = AlphaAnimation().createOnView(this, lambda)