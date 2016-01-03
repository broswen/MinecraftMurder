package com.broswen.minecraftmurder;

import com.avaje.ebeaninternal.server.cluster.mcast.Message;
import com.broswen.minecraftmurder.core.Arena;
import com.broswen.minecraftmurder.core.ArenaManager;
import com.broswen.minecraftmurder.core.constants.ArenaState;
import com.broswen.minecraftmurder.core.constants.Messages;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by broswen on 12/1/2015.
 */
public class MurderCommand implements CommandExecutor{
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        if(!(commandSender instanceof Player)){
           commandSender.sendMessage("You must be a player.");
            return true;
        }
        Player p = (Player) commandSender;

        if(strings.length == 0){
            p.sendMessage(Messages.PREFIX + "Usage: '/murder {args}' type '/murder help' for help.");
            return true;
        }

        if(strings[0].equalsIgnoreCase("join")){

            if(strings.length < 2){
                p.sendMessage(Messages.PREFIX + "Usage: '/murder join '#'");
                return true;
            }

            if(ArenaManager.getArena(p) != null){
                p.sendMessage(Messages.PREFIX + "You are already in an arena.");
                return true;
            }

            if(ArenaManager.getArena(Integer.valueOf(strings[1])) == null){
                p.sendMessage(Messages.PREFIX + "That arena does not exist.");
                return true;
            }
            Arena a = ArenaManager.getArena(Integer.valueOf(strings[1]));

            if(a.getState().equals(ArenaState.INGAME) || a.getState().equals(ArenaState.STOPPED)){
                p.sendMessage(Messages.PREFIX + "That arena/game is running.");
                return true;
            }

            if(a.isFull()){
                p.sendMessage(Messages.PREFIX + "That arena is full.");
                return true;
            }

            a.addPlayer(p);
            p.sendMessage(Messages.PREFIX + "You joined arena " + strings[1] + ".");
            p.teleport(a.getLobby());

            return true;
        }

        if(strings[0].equalsIgnoreCase("leave")){

            if(ArenaManager.getArena(p) == null){
                p.sendMessage(Messages.PREFIX + "You are not in an arena.");
                return true;
            }
            Arena a = ArenaManager.getArena(p);

            p.teleport(a.getLobby());
            a.removePlayer(p);
            a.broadcast(Messages.PREFIX + p.getName() + " has left.");
            p.sendMessage(Messages.PREFIX + "You left arena " + a.getID() + ".");

            return true;
        }

        if(strings[0].equalsIgnoreCase("start")){
            if(ArenaManager.getArena(p) == null){
                p.sendMessage(Messages.PREFIX + "You are not in an arena.");
                return true;
            }
            Arena a = ArenaManager.getArena(p);

            if(!a.getState().equals(ArenaState.WAITING)){
                p.sendMessage(Messages.PREFIX + "The game must be in lobby mode.");
                return true;
            }

            a.setForceStart(true);
            p.sendMessage(Messages.PREFIX + "You started the countdown.");

            return true;
        }

        if(strings[0].equalsIgnoreCase("stop")){

            return true;
        }

        if(strings[0].equalsIgnoreCase("arenainfo")){

            return true;
        }

        if(strings[0].equalsIgnoreCase("help")){
            p.sendMessage(Messages.PREFIX + "==== HELP ====");
            p.sendMessage("/murder join");
            p.sendMessage("/murder leave");
            p.sendMessage("/murder start");
            p.sendMessage("/murder stop");
            p.sendMessage("/murder arenainfo");
        }



        return false;
    }
}
