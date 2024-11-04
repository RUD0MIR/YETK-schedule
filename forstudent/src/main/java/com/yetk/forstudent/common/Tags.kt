package com.yetk.forstudent.common

sealed class Tags(val tag: String) {
    data class RevealSwipe(val itemId: Int) : Tags("revealSwipe$itemId")

    data class DeleteAction(val itemId: Int) : Tags("deleteAction$itemId")

    data class CheckBox(val itemId: Int) : Tags("checkbox$itemId")

    data class ExpandToggle(val itemId: Int) : Tags("expandToggle$itemId")
}
