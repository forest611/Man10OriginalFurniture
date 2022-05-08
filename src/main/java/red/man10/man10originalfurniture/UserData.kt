package red.man10.man10originalfurniture

import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import red.man10.man10originalfurniture.Man10OriginalFurniture.Companion.plugin
import java.util.concurrent.ConcurrentHashMap

class UserData {

    var itemDictionary = ConcurrentHashMap<Pair<Material,Int>,ItemStack>()
    lateinit var player : Player

    fun setNewItem(item: ItemStack){

        if (!checkItem(item)){
            player.sendMessage("§c§lこのアイテムはあなたの作ったアイテムとして登録されていません！")
            return
        }

        val mysql = MySQLManager(plugin,"UserData")

        mysql.execute("UPDATE user_data SET created_item='${Utility.itemToBase64(item)}' " +
                "WHERE uuid='${player.uniqueId}' and material='${item.type}' and custom_model_data=${item.itemMeta.customModelData}")

        itemDictionary[Pair(item.type,item.itemMeta.customModelData)] = item

        player.sendMessage("§a§l登録完了")
    }

    private fun checkItem(item:ItemStack):Boolean{

        if (!item.hasItemMeta())return false

        val material = item.type
        val cmd = item.itemMeta.customModelData

        if (itemDictionary[Pair(material,cmd)] != null)return true

        return false

    }

    fun rename(name:String,item:ItemStack){
        if (!checkItem(item)){
            player.sendMessage("§c§lこのアイテムはあなたの作ったアイテムとして登録されていません！")
            return
        }

        val meta = item.itemMeta
        name.replace("&","§")
        meta.displayName(Component.text(name))
        item.itemMeta = meta

        player.sendMessage("§a§l設定完了")
    }

    fun relore(lore: MutableList<String>,item: ItemStack){
        if (!checkItem(item)){
            player.sendMessage("§c§lこのアイテムはあなたの作ったアイテムとして登録されていません！")
            return
        }

        val meta = item.itemMeta
        for (i in 0 until lore.size){
            lore[i] = lore[i].replace("&","§")
        }
        meta.lore = lore
        item.itemMeta = meta

        player.sendMessage("§a§l設定完了")

    }

    companion object{

        var userData = ConcurrentHashMap<Player,UserData>()

        fun loadData(p: Player):UserData?{
            val data = UserData()

            val mysql = MySQLManager(plugin,"UserData")

            val rs = mysql.query("select created_item from user_data where uuid='${p.uniqueId}'")?:return null

            val dic = ConcurrentHashMap<Pair<Material,Int>,ItemStack>()

            while (rs.next()){
                val item = Utility.itemFromBase64(rs.getString("created_item"))!!

                dic[Pair(item.type,item.itemMeta.customModelData)] = item
            }

            rs.close()
            mysql.close()

            if (dic.isEmpty())return null

            data.itemDictionary = dic
            data.player = p


            userData[p] = data

            return data
        }

        fun addItem(mcid:String,item:ItemStack){

            val p = Bukkit.getOfflinePlayer(mcid)

            val mysql = MySQLManager(plugin,"UserData")

            mysql.execute("INSERT INTO user_data (player, uuid, created_item) " +
                    "VALUES ('${mcid}', '${p.uniqueId}', '${Utility.itemToBase64(item)}')")

            if (p.isOnline){
                loadData(p.player!!)
            }
        }

        fun removeItem(material: Material,cmd:Int){

            val mysql = MySQLManager(plugin,"UserData")

            mysql.execute("DELETE FROM user_data WHERE material='${material.name}' AND custom_model_data=${cmd}")

        }
    }



}