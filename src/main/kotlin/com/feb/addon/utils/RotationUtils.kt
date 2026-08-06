package com.feb.addon.utils

import com.feb.mod.event.EventBus
import com.feb.mod.event.SubscribeEvent
import com.feb.mod.event.events.RenderFrameEvent
import net.minecraft.network.protocol.game.ServerboundMovePlayerPacket
import kotlin.math.abs
import kotlin.math.roundToInt
import kotlin.math.sqrt
import kotlin.random.Random

object RotationUtils {
    var isActive = false
    private var startYaw = 0f
    private var startPitch = 0f
    private var targetYaw = 0f
    private var targetPitch = 0f
    private var progress = 0f
    private var totalTicks = 0f
    private var yawNoise = 0f
    private var pitchNoise = 0f
    private var noisePhase = 0f
    private var noiseFreq = 0f
    private var overshootYaw = 0f
    private var overshootPitch = 0f
    private const val MIN_TICKS = 32f
    private const val MAX_TICKS = 75f
    private const val SCALE_MAX = 180f

    private var easeSwitch = 0.5f

    private var sendPackets = false
    private var lastSentYaw = 0f
    private var lastSentPitch = 0f
    private var lastPacketSendTime = 0L
    private const val MIN_PACKET_INTERVAL_MS = 150L
    private const val NEAR_TARGET_PACKET_INTERVAL_MS = 300L
    private const val PACKET_ROTATION_THRESHOLD = 2.0f
    private const val NEAR_TARGET_TOLERANCE = 2.0f

    private var delayEnabled = false
    private var hasPendingRotation = false
    private var pendingYaw = 0f
    private var pendingPitch = 0f
    private var rotationRequestTime = 0L
    private var rotationDelay = 0f
    private const val DELAY_MIN_TIME = 0.0435f
    private const val DELAY_MAX_TIME = 0.174f
    private const val DELAY_YAW_THRESHOLD = 12f
    private const val DELAY_PITCH_THRESHOLD = 8f

    fun init() {
        EventBus.register("febmod-rotationutils", this)
    }

    fun setPacketSending(enabled: Boolean) {
        sendPackets = enabled
    }

    fun setRotationDelay(enabled: Boolean) {
        delayEnabled = enabled
        if (!enabled) hasPendingRotation = false
    }

    fun setRotationTarget(yaw: Float, pitch: Float) {
        val player = mc.player ?: return

        if (delayEnabled && isActive) {
            val referenceYaw = if (hasPendingRotation) pendingYaw else targetYaw
            val referencePitch = if (hasPendingRotation) pendingPitch else targetPitch
            val yawChange = abs(getRotationDelta(referenceYaw, yaw))
            val pitchChange = abs(referencePitch - pitch)
            if (yawChange > DELAY_YAW_THRESHOLD || pitchChange > DELAY_PITCH_THRESHOLD) {
                pendingYaw = yaw
                pendingPitch = pitch
                hasPendingRotation = true
                rotationRequestTime = System.currentTimeMillis()
                rotationDelay = Random.nextFloat() * (DELAY_MAX_TIME - DELAY_MIN_TIME) + DELAY_MIN_TIME
                return
            }
        }

        beginRotation(yaw, pitch)
    }

    private fun beginRotation(yaw: Float, pitch: Float) {
        val player = mc.player ?: return

        startYaw = player.yRot
        startPitch = player.xRot
        targetYaw = normalizeAngle(yaw)
        targetPitch = pitch.coerceIn(-90f, 90f)
        progress = 0f

        val yawDist = abs(getRotationDelta(startYaw, targetYaw))
        val pitchDist = abs(targetPitch - startPitch)
        val angularDist = sqrt(yawDist * yawDist + pitchDist * pitchDist)
        val distFactor = (angularDist / SCALE_MAX).coerceIn(0f, 1f)
        val baseTicks = MIN_TICKS + distFactor * (MAX_TICKS - MIN_TICKS)
        totalTicks = baseTicks * (0.95f + Random.nextFloat() * 0.15f)

        easeSwitch = 0.45f + Random.nextFloat() * 0.15f

        val mag = if (angularDist > 30f) Random.nextFloat() * 0.4f else 0f
        overshootYaw = (Random.nextFloat() - 0.5f) * 2f * mag
        overshootPitch = (Random.nextFloat() - 0.5f) * mag * 0.5f
        noisePhase = Random.nextFloat() * 100f
        noiseFreq = 0.05f + Random.nextFloat() * 0.04f

        yawNoise = 0f
        pitchNoise = 0f
        lastSentYaw = player.yRot
        lastSentPitch = player.xRot
        isActive = true
    }

    fun stop() {
        isActive = false
        progress = 0f
        yawNoise = 0f
        pitchNoise = 0f
        overshootYaw = 0f
        overshootPitch = 0f
        hasPendingRotation = false
    }

    @SubscribeEvent
    fun onRenderFrame(event: RenderFrameEvent) {
        val player = mc.player ?: return

        if (delayEnabled && hasPendingRotation) {
            val elapsed = (System.currentTimeMillis() - rotationRequestTime) / 1000f
            if (elapsed >= rotationDelay) {
                beginRotation(pendingYaw, pendingPitch)
                hasPendingRotation = false
            }
        }

        if (!isActive) return

        progress = (progress + 1f / totalTicks).coerceAtMost(1f)

        val easedProgress = blendedEase(progress, easeSwitch)

        val yawDelta = getRotationDelta(startYaw, targetYaw)
        val pitchDelta = targetPitch - startPitch

        val overshootFade = if (progress > 0.7f) (1f - progress) / 0.3f else 1f
        val overshootOffsetYaw = overshootYaw * overshootFade
        val overshootOffsetPitch = overshootPitch * overshootFade

        val amp = (1f - easedProgress).coerceIn(0f, 1f)
        noisePhase += noiseFreq
        val sineNoise = smoothNoise(noisePhase)
        yawNoise = yawNoise * 0.6f + sineNoise * 0.03f * amp
        pitchNoise = pitchNoise * 0.6f + sineNoise * 0.015f * amp

        val rawYaw = startYaw + yawDelta * easedProgress + overshootOffsetYaw + yawNoise
        val rawPitch = (startPitch + pitchDelta * easedProgress + overshootOffsetPitch + pitchNoise)
            .coerceIn(-90f, 90f)

        val newYaw = applyGCD(current = player.yRot, next = rawYaw)
        val newPitch = applyGCD(current = player.xRot, next = rawPitch).coerceIn(-90f, 90f)

        player.setYRot(newYaw)
        player.setXRot(newPitch)

        maybeSendPacket(newYaw, newPitch)

        if (progress >= 1f) stop()
    }

    private fun maybeSendPacket(currentYaw: Float, currentPitch: Float) {
        if (!sendPackets) return

        val player = mc.player ?: return
        val connection = mc.connection ?: return
        val now = System.currentTimeMillis()

        val distanceToTarget = maxOf(
            abs(getRotationDelta(currentYaw, targetYaw)),
            abs(currentPitch - targetPitch)
        )
        val requiredInterval = if (distanceToTarget < NEAR_TARGET_TOLERANCE) {
            NEAR_TARGET_PACKET_INTERVAL_MS
        } else {
            MIN_PACKET_INTERVAL_MS
        }
        if (now - lastPacketSendTime < requiredInterval) return

        val yawDelta = abs(getRotationDelta(lastSentYaw, currentYaw))
        val pitchDelta = abs(lastSentPitch - currentPitch)
        if (yawDelta < PACKET_ROTATION_THRESHOLD && pitchDelta < PACKET_ROTATION_THRESHOLD) return

        val gcd = getGCD()
        val yawMultiple = yawDelta / gcd
        val pitchMultiple = pitchDelta / gcd
        val yawValid = abs(yawMultiple - yawMultiple.roundToInt()) < 0.001f || yawDelta < 0.001f
        val pitchValid = abs(pitchMultiple - pitchMultiple.roundToInt()) < 0.001f || pitchDelta < 0.001f
        if (!yawValid || !pitchValid) return

        connection.send(ServerboundMovePlayerPacket.Rot(currentYaw, currentPitch, player.onGround(), player.horizontalCollision))
        lastSentYaw = currentYaw
        lastSentPitch = currentPitch
        lastPacketSendTime = now
    }

    private fun blendedEase(t: Float, threshold: Float): Float = easeInOutCubic(t)

    private fun easeInOutCubic(t: Float): Float =
        if (t < 0.5f) 4f * t * t * t
        else 1f - (-2f * t + 2f).let { it * it * it } / 2f

    private fun smoothNoise(phase: Float): Float {
        return (kotlin.math.sin(phase) * 0.6f + kotlin.math.sin(phase * 2.3f + 1.7f) * 0.3f + kotlin.math.sin(phase * 5.1f + 0.4f) * 0.1f)
    }

    private fun easeInOutQuad(t: Float): Float = if (t < 0.5f) 2f * t * t else 1f - (-2f * t + 2f).let { it * it } / 2f

    private fun easeOutQuad(t: Float): Float = 1f - (1f - t) * (1f - t)

    private fun applyGCD(current: Float, next: Float): Float {
        val gcd = getGCD()
        val delta = getRotationDelta(current, next)
        val rounded = (delta / gcd).roundToInt() * gcd
        return current + rounded
    }

    private fun getGCD(): Float {
        val sensitivity = mc.options.sensitivity().get()
        val f = sensitivity * 0.6 + 0.2
        return (f * f * f * 1.2).toFloat()
    }

    private fun normalizeAngle(angle: Float): Float {
        var a = angle % 360f
        if (a >= 180f) a -= 360f
        if (a < -180f) a += 360f
        return a
    }

    private fun getRotationDelta(from: Float, to: Float): Float {
        var delta = normalizeAngle(to) - normalizeAngle(from)
        if (delta > 180f) delta -= 360f
        if (delta < -180f) delta += 360f
        return delta
    }
}