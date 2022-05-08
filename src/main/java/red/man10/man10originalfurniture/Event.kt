package red.man10.man10originalfurniture

import org.bukkit.Bukkit
import org.bukkit.Sound
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import red.man10.man10originalfurniture.Man10OriginalFurniture.Companion.plugin
import red.man10.man10originalfurniture.Menu.Companion.getID
import red.man10.man10originalfurniture.Menu.Companion.popStack

object Event : Listener{

    @EventHandler
    fun clickEvent(e: InventoryClickEvent){

        val p = e.whoClicked as Player

        val menu = Menu.peekStack(p) ?: return

        e.isCancelled = true
        p.playSound(p.location, Sound.UI_BUTTON_CLICK,0.1F,1.0F)

        val item = e.currentItem?:return
        val id = getID(item)

        if (menu is ListMenu){
            when(id){
                "prev" ->{
                    menu.previous()
                    return
                }
                "next" ->{
                    menu.next()
                    return
                }
                "reload"->{
                    menu.open()
                    return
                }

            }
        }

        menu.click(e,menu,id,item)

    }

    @EventHandler
    fun closeInventory(e: InventoryCloseEvent){

        val p = e.player as Player

        if (e.reason != InventoryCloseEvent.Reason.PLAYER)return

        popStack(p)
        val menu = popStack(p) ?:return

        Bukkit.getScheduler().runTask(plugin, Runnable { menu.open() })
    }


}