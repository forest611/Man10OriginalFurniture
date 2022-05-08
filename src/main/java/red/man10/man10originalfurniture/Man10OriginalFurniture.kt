package red.man10.man10originalfurniture

import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.plugin.java.JavaPlugin

class Man10OriginalFurniture : JavaPlugin() {

    companion object{
        const val OP = "myfurniture.op"
        const val USER = "myfurniture.user"
        const val prefix = "§a§l[MyFurniture]"
        lateinit var plugin : JavaPlugin
    }

    override fun onEnable() {
        // Plugin startup logic

        plugin = this

        server.pluginManager.registerEvents(Event,this)
    }

    override fun onDisable() {
        // Plugin shutdown logic
    }

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {

        if (!sender.hasPermission(USER))return true

        if (args.isEmpty()){

            return true
        }

        when(args[0]){

            "exchangeItem" ->{

            }

        }

        return false
    }
}