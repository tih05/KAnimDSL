package com.tikhomirov.kotanimdsl

import android.animation.Animator

class AnimationCallbackListener(
    val onRepeat: (() -> Unit)? = null,
    val onEnd: (() -> Unit)? = null,
    val onCancel: (() -> Unit)? = null,
    val onStart: (() -> Unit)? = null
) : Animator.AnimatorListener {

    override fun onAnimationRepeat(p0: Animator?) {
        onRepeat?.invoke()
    }

    override fun onAnimationEnd(p0: Animator?) {
        onEnd?.invoke()
    }

    override fun onAnimationCancel(p0: Animator?) {
        onCancel?.invoke()
    }

    override fun onAnimationStart(p0: Animator?) {
        onStart?.invoke()
    }

}