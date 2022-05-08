package red.man10.man10originalfurniture.menu

import net.kyori.adventure.text.Component
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack


/**
 * ページ切り替えをするメニュー用の抽象クラス
 */
abstract class ListMenu(title:String,p:Player) : Menu(title,54,p) {

    var page = 0

    fun next(){
        page++
        open()
    }

    fun previous(){
        page--
        open()
    }

    /**
     * 一覧形式で表示させる時の雛型
     */
    fun listInventory(): Inventory {


        setPageButton()

        return menu
    }

    /**
     * リロードとページ切り替えボタンを追加
     */
    protected fun setPageButton(){
        if (page!=0){

            val prevItem = ItemStack(Material.PAPER)
            val prevMeta = prevItem.itemMeta
            prevMeta.displayName(Component.text("§6§l前ページへ"))
            setID(prevMeta, "prev")

            prevItem.itemMeta = prevMeta

            menu.setItem(45,prevItem)

        }

        if (menu.getItem(44)!=null ){
            val nextItem = ItemStack(Material.PAPER)
            val nextMeta = nextItem.itemMeta
            nextMeta.displayName(Component.text("§6§l次ページへ"))

            setID(nextMeta, "next")

            nextItem.itemMeta = nextMeta

            menu.setItem(53,nextItem)

        }

        val reloadItem = ItemStack(Material.COMPASS)
        val reloadMeta = reloadItem.itemMeta
        reloadMeta.displayName(Component.text("§6§lリロード"))
        setID(reloadMeta, "reload")
        reloadItem.itemMeta = reloadMeta
        menu.setItem(49,reloadItem)
    }

    /**
     * アイテムに情報をつける
     */
    fun addItemInformation(): ItemStack {

        return ItemStack(Material.AIR)
    }
}