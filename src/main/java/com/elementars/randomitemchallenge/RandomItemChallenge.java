package com.elementars.randomitemchallenge;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public final class RandomItemChallenge extends JavaPlugin implements Listener {

    public boolean enabled;
    public Timer timer = new Timer();

    private Random random = new Random();
    private RICTask task = new RICTask(this);
    private List<Player> players = Collections.synchronizedList(new ArrayList<>());

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
        task.start();
    }

    @Override
    public void onDisable() {
        task.stop();
    }

    public void startRIC() {
        for (Player player : players) {
            player.sendMessage(ChatColor.GRAY + "Starting Random Item Challenge!");
        }
        enabled = true;
        giveRandomItems();
    }

    public void stopRIC() {
        for (Player player : players) {
            player.sendMessage(ChatColor.GRAY + "Stopping Random Item Challenge!");
        }
        players.clear();
        enabled = false;
    }

    public void giveRandomItems() {
        for (Player player : players) {
            player.sendMessage(ChatColor.GOLD + "Dropping Item!");
            Material randomMat = items[random.nextInt(items.length)];
            ItemStack toGive = new ItemStack(randomMat, randomMat.getMaxStackSize());
            for (int i = 0; i < 100; i++) {
                Item dropped = (Item) player.getWorld().spawnEntity(player.getLocation(), EntityType.DROPPED_ITEM);
                dropped.setOwner(player.getUniqueId());
                dropped.setItemStack(toGive);
            }
        }
        timer.reset();
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (command.getName().equalsIgnoreCase("ric")) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                if (args.length == 0) {
                    player.sendMessage("Not enough arguments!");
                    return false;
                }
                if (args[0].equalsIgnoreCase("start")) {
                    players.addAll(getServer().getOnlinePlayers());
                    startRIC();
                } else if (args[0].equalsIgnoreCase("stop")) {
                    stopRIC();
                }
            } else {
                sender.sendMessage("This command is player specific only.");
            }
            return true;
        }
        return false;
    }

    @EventHandler
    public void onFoodLevelChange(FoodLevelChangeEvent event) {
        if (enabled) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if (enabled) {
            event.setDropItems(false);
        }
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        if (enabled && players.contains(event.getEntity())) {
            players.remove(event.getEntity());
        }
    }

    public Material[] items = {
            Material.HOPPER,
            Material.SNOWBALL,
            Material.COAL,
            Material.ANVIL,
            Material.OAK_TRAPDOOR,
            Material.CRAFTING_TABLE,
            Material.BARREL,
            Material.ICE,
            Material.BASALT,
            Material.BIRCH_FENCE,
            Material.BOW,
            Material.ARROW,
            Material.BRICK_STAIRS,
            Material.CARTOGRAPHY_TABLE,
            Material.FISHING_ROD,
            Material.DIAMOND_AXE,
            Material.DIAMOND_SWORD,
            Material.DIAMOND_SHOVEL,
            Material.DIAMOND_HOE,
            Material.DIAMOND_PICKAXE,
            Material.DIAMOND_HELMET,
            Material.DIAMOND_CHESTPLATE,
            Material.DIAMOND_LEGGINGS,
            Material.DIAMOND_BOOTS,
            Material.NETHERITE_AXE,
            Material.NETHERITE_SWORD,
            Material.NETHERITE_SHOVEL,
            Material.NETHERITE_HOE,
            Material.NETHERITE_PICKAXE,
            Material.NETHERITE_HELMET,
            Material.NETHERITE_CHESTPLATE,
            Material.NETHERITE_LEGGINGS,
            Material.NETHERITE_BOOTS,
            Material.ELYTRA,
            Material.FIREWORK_ROCKET,
            Material.IRON_INGOT,
            Material.SPONGE,
            Material.OAK_BOAT,
            Material.MINECART,
            Material.RAIL,
            Material.ACTIVATOR_RAIL,
            Material.DETECTOR_RAIL,
            Material.TNT,
            Material.TURTLE_HELMET,
            Material.MILK_BUCKET,
            Material.WATER_BUCKET,
            Material.ENDER_CHEST,
            Material.ENDER_PEARL,
            Material.PUFFERFISH_BUCKET,
            Material.CROSSBOW,
            Material.TRIDENT,
            Material.GOLDEN_AXE,
            Material.FLINT_AND_STEEL,
            Material.HONEY_BLOCK,
            Material.SLIME_BLOCK,
            Material.DISPENSER,
            Material.COBWEB,
            Material.GLOWSTONE,
            Material.TWISTING_VINES
    };
}
