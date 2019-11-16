//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package github.saukiya.tccardstats.inventory;

import github.saukiya.tccardstats.data.HolderData;
import github.saukiya.tccardstats.data.PlayerData;
import github.saukiya.tccardstats.data.StatsDataRead;
import github.saukiya.tccardstats.util.Config;
import github.saukiya.tccardstats.util.Message;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class CardInventory {
    public CardInventory() {
    }

    public static void openCardInventory(Player openInvPlayer, int page) {
        String playerName = openInvPlayer.getName();
        int buySlot = PlayerData.getBuySlot(openInvPlayer);
        int slot = Integer.valueOf(Config.getString("PlayerDefaultSlot")) + buySlot;
        int maxPage = slot / 36 + 1;
        Inventory inv = Bukkit.createInventory(new HolderData(page, playerName), 54, Message.getMsg("Inventory.Open.Name", new Object[0]));
        ItemStack item = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short)15);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("§r");
        item.setItemMeta(meta);

        int i;
        for(i = 0; i < 9; ++i) {
            inv.setItem(i, item);
        }

        for(i = 45; i < 54; ++i) {
            inv.setItem(i, item);
        }

//        item.setType(Material.SKELETON_SKULL);
//        item.setDurability((short)3);
//        ItemMeta skullMeta = item.getItemMeta();
//        ((SkullMeta)skullMeta).setOwner(playerName);
//        skullMeta.setDisplayName("§b" + playerName);
//        item.setItemMeta(skullMeta);
//        inv.setItem(4, item);
        item.setDurability((short)0);
        item.setType(Material.ARROW);
        if (page < maxPage) {
            meta.setDisplayName(Message.getMsg("Inventory.Open.PageDown", new Object[0]));
            item.setItemMeta(meta);
            item.setAmount(page + 1);
            inv.setItem(53, item);
        }

        if (page > 1) {
            meta.setDisplayName(Message.getMsg("Inventory.Open.PageUp", new Object[0]));
            item.setItemMeta(meta);
            item.setAmount(page - 1);
            inv.setItem(45, item);
        }

        item.setAmount(1);
        if (openInvPlayer.getName().equals(playerName)) {
            if (Config.getString("PlayerSellCard.Enabled").equals("false")) {
                item.setType(Material.BLAZE_POWDER);
                meta.setDisplayName(Message.getMsg("Inventory.Open.SellCard", new Object[0]));
                item.setItemMeta(meta);
                inv.setItem(47, item);
            }

            if (Config.getString("PlayerBuySlot.Enabled").equals("true")) {
                item.setType(Material.GOLD_INGOT);
                meta.setDisplayName(Message.getMsg("Inventory.Open.BuySlot.Name", new Object[0]));
                meta.setLore(Message.getList("Inventory.Open.BuySlot.Lore", new String[]{String.valueOf(slot), String.valueOf(Config.getInt("PlayerBuySlot.Points") + Config.getInt("PlayerBuySlot.ValuePoints") * buySlot)}));
                item.setItemMeta(meta);
                inv.setItem(51, item);
                meta.setLore(null);
            }

            item.setType(Material.ENCHANTED_BOOK);
            meta.setDisplayName(Message.getMsg("Inventory.Open.Suit", new Object[0]));
            meta.setLore(StatsDataRead.getPlayerSuitLore(playerName));
            item.setItemMeta(meta);
            inv.setItem(4, item);
        }

        YamlConfiguration playerData = PlayerData.getPlayerData(openInvPlayer);


        for(i = 9; i < 45; ++i) {
            ItemStack cardItem = playerData.getItemStack("CardItem." + (page * 36 + i - 36));
            if (cardItem != null) {
                inv.setItem(i, cardItem);
            }
        }

        slot -= (page - 1) * 36;
        item.setType(Material.BARRIER);
        meta.setDisplayName(Message.getMsg("Inventory.Open.LockSlot", new Object[0]));
        item.setItemMeta(meta);

        for(i = 9; i < 45; ++i) {
            if (i - slot - 9 >= 0) {
                inv.setItem(i, item);
            }
        }

        openInvPlayer.openInventory(inv);
    }
}
