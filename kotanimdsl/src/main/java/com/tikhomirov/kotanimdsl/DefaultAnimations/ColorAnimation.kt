package com.tikhomirov.kotanimdsl.DefaultAnimations

import android.animation.Animator
import android.animation.ObjectAnimator
import android.view.View
import com.tikhomirov.kotanimdsl.Animation
import com.tikhomirov.kotanimdsl.AnimationBuilder

class ColorAnimation: Animation<Int>() {
    lateinit var property: String
    override fun createAnimation(): Animator {

        return ObjectAnimator.ofArgb(target, property, *this@ColorAnimation.values.toIntArray())
            .apply {
                duration = this@ColorAnimation.duration
                startDelay = this@ColorAnimation.startDelay

            }
    }
}

fun AnimationBuilder.color(lambda: ColorAnimation.()->Unit) = addAnimation(ColorAnimation(), lambda)

fun View.animateColor(lambda: ColorAnimation.()->Unit)  = ColorAnimation().launchOnView(this, lambda)

fun View.color(lambda: ColorAnimation.()->Unit): Animator  = ColorAnimation().createOnView(this, lambda)