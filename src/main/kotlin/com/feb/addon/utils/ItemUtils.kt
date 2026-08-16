package com.feb.addon.utils

import net.minecraft.client.gui.screens.inventory.InventoryScreen
import net.minecraft.core.component.DataComponents
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.world.inventory.ContainerInput
import net.minecraft.world.item.ItemStack

object ItemUtils {

    private val FORMAT_REGEX = Regex("§[0-9a-fk-orx]")

    fun stripFormatting(text: String): String = text.replace(FORMAT_REGEX, "")

    private fun normalize(text: String): String = stripFormatting(text).lowercase().trim()

    fun displayName(stack: ItemStack): String = stack.hoverName.string

    fun lore(stack: ItemStack): List<String> =
        stack.get(DataComponents.LORE)?.lines()?.map { it.string } ?: emptyList()

    fun minecraftId(stack: ItemStack): String =
        BuiltInRegistries.ITEM.getKey(stack.item).path

    class ItemFilter(
        val name: String? = null,
        val lore: String? = null,
        val minecraftId: String? = null,
    ) {
        fun matches(stack: ItemStack): Boolean {
            minecraftId?.let { filter ->
                val id = minecraftId(stack)
                val normalizedFilter = normalize(filter).removePrefix("minecraft:")
                if (id != normalizedFilter) return false
            }
            name?.let { filter ->
                if (!normalize(displayName(stack)).contains(normalize(filter))) return false
            }
            lore?.let { filter ->
                val normalized = normalize(filter)
                if (lore(stack).none { normalize(it).contains(normalized) }) return false
            }
            return true
        }
    }

    data class FoundItem(val stack: ItemStack, val slot: Int)

    fun isInventoryOpen(): Boolean = mc.screen is InventoryScreen

    fun openInventory() {
        val player = mc.player ?: return
        if (mc.screen == null) {
            mc.setScreen(InventoryScreen(player))
        }
    }

    fun closeInventory() {
        if (isInventoryOpen()) {
            mc.player?.closeContainer()
            mc.setScreen(null)
        }
    }

    fun findInHotbar(filter: ItemFilter): FoundItem? = findAll(filter, 0..8).firstOrNull()

    fun findInInventory(filter: ItemFilter): FoundItem? = findAll(filter, 0..35).firstOrNull()

    fun findAll(filter: ItemFilter, range: IntRange = 0..35): List<FoundItem> {
        val player = mc.player ?: return emptyList()
        val inventory = player.inventory
        return range.mapNotNull { slot ->
            if (slot < 0 || slot >= 36) return@mapNotNull null
            val stack = inventory.getItem(slot)
            if (!stack.isEmpty && filter.matches(stack)) FoundItem(stack, slot) else null
        }
    }

    fun findByName(name: String, range: IntRange = 0..35): List<FoundItem> =
        findAll(ItemFilter(name = name), range)

    fun findByLore(text: String, range: IntRange = 0..35): List<FoundItem> =
        findAll(ItemFilter(lore = text), range)

    fun findByMinecraftId(id: String, range: IntRange = 0..35): List<FoundItem> =
        findAll(ItemFilter(minecraftId = id), range)

    fun moveItem(fromSlot: Int, toSlot: Int): Boolean {
        if (fromSlot == toSlot) return true
        return click(fromSlot, 0, ContainerInput.PICKUP) && click(toSlot, 0, ContainerInput.PICKUP)
    }

    fun swapToHotbar(slot: Int, hotbarIndex: Int): Boolean {
        if (hotbarIndex !in 0..8) return false
        return click(slot, hotbarIndex, ContainerInput.SWAP)
    }

    fun quickMove(slot: Int): Boolean = click(slot, 0, ContainerInput.QUICK_MOVE)

    private fun click(inventorySlot: Int, mouseButton: Int, input: ContainerInput): Boolean {
        if (!isInventoryOpen()) return false
        val player = mc.player ?: return false
        val menuSlot = menuSlotId(inventorySlot) ?: return false
        mc.gameMode?.handleContainerInput(player.containerMenu.containerId, menuSlot, mouseButton, input, player)
        return true
    }

    private fun menuSlotId(inventoryIndex: Int): Int? {
        val player = mc.player ?: return null
        val inventory = player.inventory
        return player.containerMenu.slots
            .firstOrNull { it.container === inventory && it.getContainerSlot() == inventoryIndex }
            ?.index
    }

}
