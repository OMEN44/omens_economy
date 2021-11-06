package omen44.omens_economy.commands;

import omen44.omens_economy.Main;
import omen44.omens_economy.datamanager.ConfigTools;
import omen44.omens_economy.utils.EconomyUtils;
import omen44.omens_economy.utils.ShortcutsUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

/*
    This class implements:
        - /setmoney <bank/wallet> <target> <amount>
*/

public class CommandSetMoney implements TabExecutor {
    public Main main;

    ShortcutsUtils s = new ShortcutsUtils();
    FileConfiguration config = ConfigTools.getFileConfig("config.yml");
    String symbol = config.getString("money.moneySymbol");

    public CommandSetMoney(Main main) {this.main = main;}

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;

        int wallet = main.economyUtils.getMoney(player, "wallet");
        int bank = main.economyUtils.getMoney(player, "bank");
        String type;

        if (label.equalsIgnoreCase("setmoney") && args.length == 3){
            switch (args[0]) {
                case "wallet" -> type = "wallet";
                case "bank" -> type = "bank";
                default -> type = "error";
            }
            Player target = Bukkit.getPlayer(args[1]);
            int amount = Integer.parseInt(args[2]);

            if (type.equals("wallet")) {
                main.economyUtils.setWallet(target, amount);
                player.sendMessage(s.prefix + ChatColor.YELLOW + "Set " + args[1] + "'s wallet to " + symbol + wallet);
            } else if (type.equals("bank")) {
                main.economyUtils.setBank(target, amount);
                player.sendMessage(s.prefix + ChatColor.YELLOW + "Set " + args[1] + "'s bank to " + symbol + bank);
            } else {
                player.sendMessage(s.prefix + ChatColor.RED + "Error: Invalid Syntax");
            }
        } else {
            player.sendMessage(s.prefix + ChatColor.RED + "Error: Invalid Syntax");
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        Player player = (Player) sender;
        if (args.length == 1) {
            List<String> args1 = new ArrayList<>();
            args1.add("bank");
            args1.add("wallet");
            return args1;
        }
        if (args.length == 2) {
            List<String> playerNames = new ArrayList<>();
            Player[] players = new Player[Bukkit.getServer().getOnlinePlayers().size()];
            Bukkit.getServer().getOnlinePlayers().toArray(players);
            for (Player value : players) {
                playerNames.add(value.getName());
            }
            return playerNames;
        }
        if (args.length == 3) {
            List<String> args3 = new ArrayList<>();
            args3.add("<amount>");
            return args3;
        }
        return null;
    }
}
