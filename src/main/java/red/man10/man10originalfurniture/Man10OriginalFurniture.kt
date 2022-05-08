package red.man10.man10originalfurniture

import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin
import red.man10.man10originalfurniture.menu.Event

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
        if (sender !is Player)return true

        if (args.isEmpty()){

            return true
        }

        when(args[0]){

            "exchangeItem" ->{

            }

            "add" ->{//mf add <mcid>
                if (!sender.hasPermission(OP))return true

                if (args.size!=2){
                    sender.sendMessage("/mf add <mcid> 手に持っているアイテムを登録します")
                    return true
                }

                val item = sender.inventory.itemInMainHand

                if (!item.hasItemMeta()){
                    sender.sendMessage("アイテムのCustomModelDataが設定されていない可能性があります")
                    return true
                }

                Thread{
                    UserData.addItem(args[1],item)
                    sender.sendMessage("登録完了 mcid:${args[1]} CMD:${item.itemMeta.customModelData}")
                }.start()
            }

            "remove" ->{
                if (!sender.hasPermission(OP))return true

                if (args.size!=1){
                    sender.sendMessage("/mf remove 手に持ってるアイテムをDBから削除します")
                    return true
                }

                val item = sender.inventory.itemInMainHand

                if (!item.hasItemMeta()){
                    sender.sendMessage("アイテムのCustomModelDataが設定されていない可能性があります")
                    return true
                }

                Thread{

                    UserData.removeItem(item.type,item.itemMeta.customModelData)
                    sender.sendMessage("削除完了 CMD:${item.itemMeta.customModelData}")
                }.start()

            }

            "rename" ->{

                val data = UserData.userData[sender] ?: return true
                data.rename(args.joinToString().replace(" rename",""),sender.inventory.itemInMainHand)

            }

            "relore" ->{

                val data = UserData.userData[sender] ?: return true
                val str = args.joinToString().replace(" rename","").split(";").toMutableList()

                data.relore(str,sender.inventory.itemInMainHand)
            }

            "set" ->{

                val data = UserData.userData[sender] ?: return true

                Thread{
                    data.setNewItem(sender.inventory.itemInMainHand)
                }.start()
            }

        }

        return false
    }
}