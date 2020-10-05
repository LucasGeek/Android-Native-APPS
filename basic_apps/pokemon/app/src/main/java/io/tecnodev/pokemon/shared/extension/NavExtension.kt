package io.tecnodev.pokemon.shared.extension

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.NavOptions
import io.tecnodev.pokemon.R

private val slideLeftOptions = NavOptions.Builder()
    .setEnterAnim(R.anim.from_right)
    .setExitAnim(R.anim.to_left)
    .setPopEnterAnim(R.anim.from_left)
    .setPopExitAnim(R.anim.to_right)
    .build()

fun NavController.navigateWithAnimations(
    destinationId: Int,
    animation: NavOptions = slideLeftOptions
) {
    this.navigate(destinationId, null, animation)
}

fun NavController.navigateWithAnimations(
    destinationId: Int,
    args: Bundle,
    animation: NavOptions = slideLeftOptions
) {
    this.navigate(destinationId, args, animation)
}

fun NavController.navigateWithAnimations(
    directions: NavDirections,
    animation: NavOptions = slideLeftOptions
) {
    this.navigate(directions, animation)
}