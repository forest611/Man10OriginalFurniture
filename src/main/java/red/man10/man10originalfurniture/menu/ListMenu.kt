package red.man10.man10originalfurniture.menu

import net.kyori.adventure.text.Component
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack
import red.man10.man10originalfurniture.Man10OriginalFurniture.Companion.exchangeItem


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
    fun listInventory(values: MutableList<ItemStack>): Inventory {

        var inc = 0

        while (menu.getItem(44) == null){
            if (values.size <= inc+page*45)break

            val item = values[inc+page*45]

            inc ++

            menu.addItem(addItemInformation(item))

        }

        setPageButton()

        return menu
    }

    /**
     * リロードとページ切り替えボタンを追加
     */
    private fun setPageButton(){
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

//        val reloadItem = ItemStack(Material.COMPASS)
//        val reloadMeta = reloadItem.itemMeta
//        reloadMeta.displayName(Component.text("§6§lリロード"))
//        setID(reloadMeta, "reload")
//        reloadItem.itemMeta = reloadMeta
//        menu.setItem(49,reloadItem)
    }

    /**
     * アイテムに情報をつける
     */
    private fun addItemInformation(item: ItemStack): ItemStack {

        val clone = item.clone()

        val meta = clone.itemMeta

        val lore = meta.lore()?.toMutableList()?: mutableListOf()

        lore.add(Component.text("§a左クリックで投票パール ${exchangeItem.amount}個と交換"))
        meta.lore(lore)

        clone.itemMeta = meta

        return clone
    }
}