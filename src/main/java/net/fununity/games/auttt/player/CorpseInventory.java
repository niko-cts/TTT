package net.fununity.games.auttt.player;

import net.fununity.games.auttt.GameLogic;
import net.fununity.games.auttt.TTT;
import net.fununity.games.auttt.language.TranslationKeys;
import net.fununity.main.api.common.util.RandomUtil;
import net.fununity.main.api.inventory.ClickAction;
import net.fununity.main.api.inventory.CustomInventory;
import net.fununity.main.api.item.ItemBuilder;
import net.fununity.main.api.item.UsefulItems;
import net.fununity.main.api.player.APIPlayer;
import net.fununity.mgs.gamestates.GameManager;
import net.fununity.mgs.gamestates.GameState;
import net.fununity.misc.translationhandler.translations.Language;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitTask;

import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CorpseInventory {

    private final PlayerCorpse playerCorpse;
    private final Object[] items;
    private final Map<UUID, Integer> analyzed;
    private final Map<UUID, BukkitTask> analyzeTask;

    public CorpseInventory(PlayerCorpse playerCorpse, PlayerDeathEvent event) {
        this.playerCorpse = playerCorpse;
        this.analyzed = new HashMap<>();
        this.analyzeTask = new HashMap<>();
        this.items = new ItemBuilder[7 * 3];
        this.items[10] = UsefulItems.getPlayerHead(playerCorpse.tttPlayer.getApiPlayer());

        if (RandomUtil.getRandom().nextBoolean()) { // death
            ItemBuilder item;
            if (ChronoUnit.MINUTES.between(this.playerCorpse.death, OffsetDateTime.now()) >= 3)
                item = new ItemBuilder(Material.WEB).setName(TranslationKeys.TTT_GUI_CORPSE_TIME_LONG);
            else
                item = new ItemBuilder(Material.REDSTONE).setName(TranslationKeys.TTT_GUI_CORPSE_TIME_CLOSE);
            insertItem(item, RandomUtil.getRandomInt(5));
        }

        Entity killer = event.getEntity().getLastDamageCause().getEntity();
        Material material = null;
        if (killer instanceof Player) {
            ItemStack weapon = ((Player) killer).getInventory().getItemInMainHand();
            switch (weapon.getType()) {
                case WOOD_SWORD -> material = Material.STICK;
                case STONE_SWORD -> material = Material.COBBLESTONE;
                case IRON_SWORD -> material = Material.IRON_INGOT;
                case DIAMOND_SWORD -> material = Material.DIAMOND;
            }
        } else if (killer instanceof Arrow)
            material = Material.BOW;
        if (material != null)
            insertItem(new ItemBuilder(material).setName(TranslationKeys.TTT_GUI_CORPSE_KILLWEAPON), 4);


        if (RandomUtil.getRandom().nextBoolean()) { // hand item
            ItemStack item = event.getEntity().getInventory().getItemInMainHand().clone();
            if (item != null && item.getType() != Material.AIR)
                insertItem(item, 1);
        }
    }

    private void insertItem(Object item, int amount) {
        for (int i = 0; i < amount; i++) {
            int j;
            do {
                j = RandomUtil.getRandomInt(items.length);
            } while (items[j] != null);
            items[j] = item;
        }
    }

    private void openInventory(TTTPlayer tttPlayer) {
        Language lang = tttPlayer.getApiPlayer().getLanguage();
        CustomInventory menu = new CustomInventory(playerCorpse.tttPlayer.getColoredName(), 9 * 5);
        menu.setSpecialHolder("ttt-corpse-" + playerCorpse.tttPlayer.getApiPlayer().getUniqueId());

        for (int i = 1; i < 9; i++) {
            menu.setItem(i, tttPlayer.getRole().getGlass());
            menu.setItem(menu.getInventory().getSize() - i, tttPlayer.getRole().getGlass());
        }
        for (int i = 0; i < menu.getInventory().getSize(); i += 9) {
            menu.setItem(i, tttPlayer.getRole().getGlass());
            if (i + 8 < menu.getInventory().getSize())
                menu.setItem(i + 8, tttPlayer.getRole().getGlass());
        }

        for (int i = 0, j = 10; i < items.length; i++, j += ((j+1) % 8 == 0 ? 3 : 1)) {

            if (items[i] instanceof ItemBuilder)
                menu.setItem(j, ((ItemBuilder) items[i]).translate(lang), new ClickAction() {
                    @Override
                    public void onClick(APIPlayer apiPlayer, ItemStack itemStack, int i) {
                        // todo traitor remove
                    }
                });
            else if(items[i] instanceof ItemStack) {
                int finalI = i;
                menu.setItem(j, (ItemStack) items[finalI], new ClickAction() {
                    @Override
                    public void onClick(APIPlayer apiPlayer, ItemStack itemStack, int slot) {
                        apiPlayer.getPlayer().closeInventory();
                        if (GameManager.getInstance().getCurrentGameState() != GameState.INGAME) return;
                        apiPlayer.getPlayer().getInventory().addItem(itemStack);
                        items[finalI] = new ItemStack(Material.AIR);
                        for (TTTPlayer onlinePlayer : GameLogic.getInstance().getTTTPlayers()) {
                            if (onlinePlayer.getApiPlayer().hasCustomData("openInv")) {
                                CustomInventory inv = (CustomInventory) onlinePlayer.getApiPlayer().getCustomData("openInv");
                                if (inv.getSpecialHolder() != null && inv.getSpecialHolder().equals(menu.getSpecialHolder())) {
                                    onlinePlayer.getApiPlayer().getPlayer().closeInventory();
                                    openInventory(onlinePlayer);
                                }
                            }
                        }
                        openInventory(tttPlayer);
                    }
                });
            }
        }

        menu.fill(UsefulItems.BACKGROUND_GRAY);
        menu.open(tttPlayer.getApiPlayer());
    }


    public void openGUI(APIPlayer apiPlayer) {
        if (analyzed.getOrDefault(apiPlayer.getUniqueId(), 1) == 0) {
            TTTPlayer tttPlayer = GameLogic.getInstance().getTTTPlayer(apiPlayer.getUniqueId());
            if (tttPlayer != null)
                openInventory(tttPlayer);
            return;
        }

        CustomInventory menu = new CustomInventory(playerCorpse.tttPlayer.getColoredName(), 9 * 5);
        for (int i = 1; i < 9; i++) {
            menu.setItem(i, playerCorpse.tttPlayer.getRole().getGlass());
            menu.setItem(menu.getInventory().getSize() - i, playerCorpse.tttPlayer.getRole().getGlass());
        }
        for (int i = 0; i < menu.getInventory().getSize(); i += 9) {
            menu.setItem(i, playerCorpse.tttPlayer.getRole().getGlass());
            if (i + 8 < menu.getInventory().getSize())
                menu.setItem(i + 8, playerCorpse.tttPlayer.getRole().getGlass());
        }

        Language lang = apiPlayer.getLanguage();

        menu.setItem(22, UsefulItems.getPlayerHead(playerCorpse.tttPlayer.getApiPlayer()).setName(playerCorpse.tttPlayer.getColoredName())
                .setLore(lang.getTranslation(analyzed.containsKey(apiPlayer.getUniqueId()) ? TranslationKeys.TTT_GUI_CORPSE_ANALYZING : TranslationKeys.TTT_GUI_CORPSE_ANALYZABLE).split(";")).craft(), new ClickAction() {
            @Override
            public void onClick(APIPlayer apiPlayer, ItemStack itemStack, int i) {
                if (analyzed.containsKey(apiPlayer.getUniqueId())) return;
                startTimer(apiPlayer.getUniqueId());
                apiPlayer.getPlayer().closeInventory();
                analyzed.put(apiPlayer.getUniqueId(), 31);
                openGUI(apiPlayer);
            }
        });

        menu.open(apiPlayer);
    }

    private void startTimer(UUID uuid) {
        BukkitTask bukkitTask = Bukkit.getScheduler().runTaskTimer(TTT.getInstance(), () -> {
            int i = this.analyzed.get(uuid) - 1;
            this.analyzed.put(uuid, i);
            if (i == 0) {
                this.analyzeTask.get(uuid).cancel();
                this.analyzeTask.remove(uuid);
            }
        }, 0, 20);
        this.analyzeTask.put(uuid, bukkitTask);
    }
}
