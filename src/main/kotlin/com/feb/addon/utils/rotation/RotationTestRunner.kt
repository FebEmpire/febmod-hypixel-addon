package com.feb.addon.utils.rotation

import com.feb.mod.addon.AddonContext
import com.feb.mod.api.event.events.RenderFrameEvent
import kotlin.random.Random

object RotationTestRunner {

    private var remaining = 0
    private var total = 0
    private var running = false
    private var wasActiveLastTick = false

    fun init(context: AddonContext) {
        context.events.on<RenderFrameEvent> {
            onRenderFrame(it)
        }
    }

    fun start(count: Int) {
        if (running) {
            println("Rotation test already running (${remaining}/${total} left), ignoring")
            return
        }

        remaining = count
        total = count
        running = true
        wasActiveLastTick = false

        println("Starting rotation test: $count rotations")
        fireNext()
    }

    fun stop() {
        running = false
        remaining = 0
    }

    private fun fireNext() {
        if (remaining <= 0) {
            running = false
            println("Rotation test complete ($total/$total)")
            return
        }

        val index = total - remaining + 1
        remaining--

        val yaw = Random.nextFloat() * 360f - 180f
        val pitch = Random.nextFloat() * 180f - 90f

        println("[$index/$total] rotating to yaw=$yaw pitch=$pitch")

        RotationUtils.setRotationTarget(yaw, pitch)
        wasActiveLastTick = true
    }

    private fun onRenderFrame(event: RenderFrameEvent) {
        if (!running) return

        val active = RotationUtils.isActive

        if (wasActiveLastTick && !active) {
            fireNext()
        }

        wasActiveLastTick = active
    }
}