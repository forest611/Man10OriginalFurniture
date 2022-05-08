package red.man10.man10originalfurniture.menu

import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack
import red.man10.man10originalfurniture.UserData

class MainMenu(p:Player) : ListMenu("登録済みアイテム",p){

    override fun open() {

        val data = UserData.userData[p]

        if (data==null){
            p.openInventory(menu)
            pushStack()
            return
        }

        listInventory(data.itemDictionary.values.toMutableList())
        p.openInventory(menu)
        pushStack()
    }

    override fun click(e: InventoryClickEvent, menu: Menu, id: String, item: ItemStack) {

        if (item.type==Material.AIR)return

        val data = UserData.userData[p]?:return

        data.buyItem(item)

    }
}