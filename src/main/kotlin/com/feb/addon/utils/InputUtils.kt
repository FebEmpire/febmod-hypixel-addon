package com.feb.addon.utils

import com.mojang.blaze3d.platform.InputConstants
import net.minecraft.client.KeyMapping

object InputUtils {

    private fun currentKey(mapping: KeyMapping): InputConstants.Key? = try {
        val field = KeyMapping::class.java.getDeclaredField("key")
        field.trySetAccessible()
        field.get(mapping) as InputConstants.Key
    } catch (e: Exception) {
        null
    }

    fun press(mapping: KeyMapping) {
        val key = currentKey(mapping) ?: return
        KeyMapping.set(key, true)
        KeyMapping.click(key)
    }

    fun release(mapping: KeyMapping) {
        val key = currentKey(mapping) ?: return
        KeyMapping.set(key, false)
    }

    fun click(mapping: KeyMapping) {
        press(mapping)
        release(mapping)
    }

    fun releaseAll() {
        KeyMapping.releaseAll()
    }

    fun selectHotbarSlot(slot: Int) {
        if (slot !in 0..8) return
        click(mc.options.keyHotbarSlots[slot])
    }

    fun useItem() {
        click(mc.options.keyUse)
    }

    fun attack() {
        click(mc.options.keyAttack)
    }

    fun dropItem() {
        click(mc.options.keyDrop)
    }

    fun swapOffhand() {
        click(mc.options.keySwapOffhand)
    }

    fun openInventory() {
        click(mc.options.keyInventory)
    }

}
