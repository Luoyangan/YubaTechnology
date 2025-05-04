package me.luoyangan.yubatechnology;

import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;
import io.github.thebusybiscuit.slimefun4.utils.SlimefunUtils;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.Plugin;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;
import io.github.thebusybiscuit.slimefun4.libraries.dough.config.Config;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;

import javax.annotation.Nonnull;
import java.util.logging.Level;

public class YubaTechnology extends JavaPlugin implements SlimefunAddon {

    @Override
    public void onEnable() {

        // 从 config.yml 中读取插件配置
        Config cfg = new Config(this);

        if (cfg.getBoolean("options.auto-update")) {
            // 你可以在这里添加自动更新功能
        }

        getLogger().info("########################################");
        getLogger().info("        YubaTechnology - 腐竹科技         ");
        getLogger().info("            作者: Luoyangan              ");
        getLogger().info("            QQ群: 812500721              ");
        getLogger().info("########################################");

        // 检查 SoulJars 插件是否加载
        if (!getServer().getPluginManager().isPluginEnabled("SoulJars")) {
            getLogger().log(Level.SEVERE, "灵魂罐(SoulJars) 插件未加载，部分功能将无法使用!");
            getLogger().log(Level.SEVERE, "从此处下载: https://builds.guizhanss.com/SlimefunGuguProject/SoulJars/master");
            getServer().getPluginManager().disablePlugin(this);
            return;
        } else {
            getLogger().severe("灵魂罐(SoulJars) 插件已加载");
        }

        /*
         * 1. 创建分类
         * 分类的显示物品将使用以下物品
         */
        ItemStack itemGroupItem = new CustomItemStack(
                SlimefunUtils.getCustomHead("818cfff452b0ed6f2f9c2b7a91aa9f1640703d62a774b7fc52c6500b533edbfa"),
                "&x&d&9&a&f&d&9腐&x&c&3&b&d&d&c竹&x&a&d&c&b&d&e科&x&9&7&d&9&e&1技");

        // 给你的分类提供一个独一无二的ID
        NamespacedKey itemGroupId = new NamespacedKey(this, "yubatechnology");
        ItemGroup itemGroup = new ItemGroup(itemGroupId, itemGroupItem);

        Plugin soulJars = getServer().getPluginManager().getPlugin("SoulJars");
        if (soulJars != null && soulJars.isEnabled()) {
            ItemStack itemGroupItem1 = new CustomItemStack(
                    Material.GHAST_SPAWN_EGG,
                    "&x&d&9&a&f&d&9腐&x&c&3&b&d&d&c竹&x&a&d&c&b&d&e科&x&9&7&d&9&e&1技 &f- &x&0&0&e&0&f&f刷&x&0&0&e&0&f&f怪&x&0&0&e&0&f&f蛋"
            );
            NamespacedKey itemGroupId1 = new NamespacedKey(this, "yubatechnology_spawn");
            ItemGroup itemGroup1 = new ItemGroup(itemGroupId1, itemGroupItem1);


            SlimefunItemStack slimefunItem1 = new SlimefunItemStack(
                    "FZKJ_SPAWN_A",
                    Material.GHAST_SPAWN_EGG,
                    "§f空白刷怪蛋",
                    "&7制作刷怪蛋的材料之一"
            );
            ItemStack[] recipe1= {
                    SlimefunItems.BLANK_RUNE, SlimefunItems.MAGIC_LUMP_3, SlimefunItems.BLANK_RUNE,
                    SlimefunItems.MAGICAL_GLASS, SlimefunItem.getById("SOUL_JAR").getItem(), SlimefunItems.MAGICAL_GLASS,
                    SlimefunItems.BLANK_RUNE, SlimefunItems.MAGIC_LUMP_3, SlimefunItems.BLANK_RUNE
            };
            SlimefunItem item1 = new SlimefunItem(itemGroup1, slimefunItem1, RecipeType.ANCIENT_ALTAR, recipe1);
            item1.register(this);
        }

        /*
         * 2. 创建一个 SlimefunItemStack
         * 这个类是 ItemStack 的扩展，拥有多个构造函数
         * 重要：每个物品都得有一个独一无二的ID
         */
        SlimefunItemStack slimefunItem = new SlimefunItemStack("FZKJ_A", Material.DIAMOND, "&4炫酷的钻石", "&c+20% 炫酷");

        /*
         * 3. 创建配方
         * 这个配方是一个拥有9个ItemStack的数组。
         * 它代表了一个3x3的有序合成配方。
         * 该配方所需的机器将在后面通过RecipeType指定。
         */
        ItemStack[] recipe = {
                new ItemStack(Material.EMERALD), null, new ItemStack(Material.EMERALD),
                null, new ItemStack(Material.DIAMOND), null,
                new ItemStack(Material.EMERALD), null, new ItemStack(Material.EMERALD)
        };

        /*
         * 4. 注册物品
         * 现在，你只需要注册物品
         * RecipeType.ENHANCED_CRAFTING_TABLE 代表
         * 该物品将在增强型工作台中合成。
         * 来自粘液科技本体的配方类型将会自动将配方添加到对应的机器中。
         */
        SlimefunItem item = new SlimefunItem(itemGroup, slimefunItem, RecipeType.ENHANCED_CRAFTING_TABLE, recipe);
        item.register(this);
    }

    @Override
    public void onDisable() {
        // 禁用插件的逻辑...
    }

    @Override
    public String getBugTrackerURL() {
        // 你可以在这里返回你的问题追踪器的网址，而不是 null
        return null;
    }

    @Override
    public JavaPlugin getJavaPlugin() {
        /*
         * 你需要返回对你插件的引用。
         * 如果这是你插件的主类，只需要返回 "this" 即可。
         */
        return this;
    }

    /*
     * 监听玩家交互事件，当玩家使用空白刷怪蛋时取消该事件
     * @param event 玩家交互事件对象
     */
    /*@EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {

        Player player = event.getPlayer();
        ItemStack item = event.getItem();

        // 检查玩家手中的物品是否为空白刷怪蛋
        if (item != null && item.isSimilar(FZKJ_SPAWN_A)) {
            ItemMeta meta = item.getItemMeta();
            if (meta != null && meta.getDisplayName().equals("§f空白刷怪蛋")) {
                // 取消事件，阻止玩家使用物品
                event.setCancelled(true);
                // 给玩家发送提示信息
                player.sendMessage("该刷怪蛋是空白的，需封装灵魂后才能使用");
            }
        }
    }*/
}
